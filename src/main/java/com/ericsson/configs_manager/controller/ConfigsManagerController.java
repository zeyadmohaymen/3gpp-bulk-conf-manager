package com.ericsson.configs_manager.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ericsson.configs_manager.service.ConfigsManagerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api")
public class ConfigsManagerController {

    @Autowired
    private ConfigsManagerService cmService;

    // TODO: Utilize the Boda bulk CM parser to parse file into CSVs. Save CSVs to MongoDB.
    // @PostMapping("/boda-bulkcmparser")
    // public void bodaBulkCmParser() {
    //     /**
    //      * https://github.com/bodastage/boda-bulkcmparser/tree/master
    //      * 
    //      * XML to csv parser for 3GPP Bulk CM data files as defined by ETSI TS 132 615.
    //      */
        
    // }

    /**
     * Parses the bulk CM data from the given file.
     * Saves parsed configuration data to mongoDB.
     *
     * @param file The multipart file containing the CM data.
     * @return The result of saving the bulk CM data.
     */
    @PostMapping(path="/parse", consumes = "multipart/form-data")
    public Object parseBulkCmData(@RequestParam("file") MultipartFile file) {
        
        return cmService.saveBulkCm(file);
    }

    // TODO: Implement file upload with session cache
    // @PostMapping("/upload")
    // public void uploadFile(@RequestParam("file") MultipartFile file) {
    // }

    @GetMapping("/configs/all")
    public Object getConfigs() {
        return cmService.getAllConfigurations();
    }

    @GetMapping("/mecontexts/all")
    public Object getMeContexts() {
        return cmService.getAllMeContexts();
    }

    @GetMapping("/configs/{id}")
    public Object getConfig(@PathVariable("id") String id) {
        return cmService.getConfigurationById(id);
    }

    @GetMapping("/mecontexts/{id}")
    public Object getMeContext(@PathVariable("id") String id) {
        return cmService.getMeContextById(id);
    }

}
