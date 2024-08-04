package com.ericsson.configs_manager.model;

import javax.xml.bind.annotation.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a bulk CM configuration data file.
 * This class is used to store and retrieve configuration data in XML format.
 * The XML root element is "bulkCmConfigDataFile" with the namespace "configData.xsd".
 * The class is annotated with @XmlRootElement and @XmlAccessorType to enable XML serialization and deserialization.
 * The class is also annotated with @Document(collection = "configs") to specify the MongoDB collection name.
 */
@XmlRootElement(name = "bulkCmConfigDataFile", namespace = "configData.xsd")
@XmlAccessorType(XmlAccessType.FIELD)
@Document(collection = "configs")
public class BulkCmConfigDataFile {

    @Id
    private String id;

    @XmlElement(name = "configData", namespace = "configData.xsd")
    private ConfigData configData;

    // Getters and setters

    public String getId() {
        return id;
    }
    
    public ConfigData getConfigData() {
        return configData;
    }

    public void setConfigData(ConfigData configData) {
        this.configData = configData;
    }
}
