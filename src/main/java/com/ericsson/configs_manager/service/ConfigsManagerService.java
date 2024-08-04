package com.ericsson.configs_manager.service;

// import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
// import javax.xml.validation.Schema;
// import javax.xml.validation.SchemaFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ericsson.configs_manager.model.BulkCmConfigDataFile;
import com.ericsson.configs_manager.model.MeContext;
import com.ericsson.configs_manager.repository.ConfigsRepository;
import com.ericsson.configs_manager.repository.MeContextsRepository;
import com.ericsson.configs_manager.util.DomUtil;

/**
 * This class represents the service for managing configurations.
 */
@Service
public class ConfigsManagerService {
    
    @Autowired
    private ConfigsRepository configsManagerRepository;

    @Autowired
    private MeContextsRepository meContextsRepository;

    /**
     * Saves the bulk CM configuration data file to the database.
     * 
     * @param mpfile The multipart file containing the bulk CM configuration data.
     * @return A string indicating the status of the operation.
     */
    public String saveBulkCm(MultipartFile mpfile) {

        try {
            
            BulkCmConfigDataFile file = (BulkCmConfigDataFile) unmarshallBulkCmFile(mpfile);
            
            parseMeContexts(mpfile);

            BulkCmConfigDataFile confs = configsManagerRepository.save(file);

            return("File parsed successfully. Configuration saved to database with id " + confs.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return("Error parsing file.");
        }

    }

    // fetch all configurations
    public List<String> getAllConfigurations() {
        List<BulkCmConfigDataFile> configs = configsManagerRepository.findAll();
        List<String> ids = new ArrayList<>();
        for (BulkCmConfigDataFile config : configs) {
            ids.add(config.getId());
        }
        return ids;
    }

    // fetch all MeContexts
    public List<String> getAllMeContexts() {
        List<MeContext> meContexts = meContextsRepository.findAll();
        List<String> ids = new ArrayList<>();
        for (MeContext meContext : meContexts) {
            ids.add(meContext.getId());
        }
        return ids;
    }

    // fetch a configuration by id
    public BulkCmConfigDataFile getConfigurationById(String id) {
        return configsManagerRepository.findById(id).orElse(null);
    }

    // fetch a MeContext by id
    public MeContext getMeContextById(String id) {
        return meContextsRepository.findById(id).orElse(null);
    }


    /**
     * Unmarshalls a BulkCmConfigDataFile from the provided XML file.
     *
     * @param xmlFile the XML file to be unmarshalled
     * @return the unmarshalled BulkCmConfigDataFile object
     */
    private Object unmarshallBulkCmFile(MultipartFile xmlFile) {

        try (InputStream inputStream = xmlFile.getInputStream()) {

            JAXBContext jaxbContext = JAXBContext.newInstance(BulkCmConfigDataFile.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // set schema validation
            // SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            // Schema schema = sf.newSchema(new File("src/main/xml/schemas/EricssonSpecificAttributes.5.1.xsd"));
            // unmarshaller.setSchema(schema);

            return unmarshaller.unmarshal(inputStream);

        } catch (JAXBException | IOException e) {
            e.printStackTrace();
            return("Error parsing file.");
        }
    }


    /**
     * Parses the given MultipartFile and extracts MeContext elements from the XML document.
     * 
     * @param mpfile The MultipartFile to be parsed.
     * @return A string indicating the status of the parsing process. Returns "File parsed successfully." if the file is parsed successfully, 
     *         or "Error parsing file." if an error occurs during parsing.
     */
    private String parseMeContexts(MultipartFile mpfile) {

        try (InputStream inputStream = mpfile.getInputStream()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(inputStream);

            doc.getDocumentElement().normalize();

            // Find all MeContext elements
            NodeList meContextNodes = doc.getElementsByTagName("xn:MeContext");

            // List to hold all MeContext objects
            List<MeContext> meContexts = new ArrayList<>();


            for (int i = 0; i < meContextNodes.getLength(); i++) {
                Node meContextNode = meContextNodes.item(i);

                if (meContextNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element meContextElement = (Element) meContextNode;
                    String key = DomUtil.getMeContextId(meContextElement);

                    // Create a map for the current MeContext node
                    Map<String, Object> meContextHierarchy = DomUtil.buildHierarchy(meContextElement);

                    // Create a MeContext object
                    MeContext meContext = new MeContext(key, meContextHierarchy);

                    // Add the MeContext object to the list
                    meContexts.add(meContext);
                }
            }

            meContextsRepository.saveAll(meContexts);
            
            return("File parsed successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            return("Error parsing file.");
        }
        
    }

}
