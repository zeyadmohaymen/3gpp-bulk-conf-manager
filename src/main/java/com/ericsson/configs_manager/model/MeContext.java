package com.ericsson.configs_manager.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.*;

/**
 * Represents a MeContext object.
 * 
 * This class is used to store information about a MeContext, which is a context for managing configurations.
 * It contains an ID and a map of key-value pairs representing the MeContext data.
 * 
 * The MeContext class provides getters and setters for accessing and modifying the MeContext data.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Document(collection = "mecontexts")
public class MeContext {

    @Id
    @XmlAttribute(name = "id")
    private String id;
    
    private Map<String, Object> meContextData;

    public MeContext() {
    }

    public MeContext(Map<String, Object> meContextData) {
        this.meContextData = meContextData;
    }

    public MeContext(String id, Map<String, Object> meContextData) {
        this.id = id;
        this.meContextData = meContextData;
    }
    

    public String getId() {
        return id;
    }


    public Map<String, Object> getMeContextData() {
        return meContextData;
    }

    public void setMeContextData(Map<String, Object> meContextData) {
        this.meContextData = meContextData;
    }


    
}

