package com.ericsson.configs_manager.model;

import javax.xml.bind.annotation.*;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * Represents a sub-network.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SubNetwork {

    @XmlAttribute(name = "id")
    private String id;

    @DBRef
    @XmlElement(name = "MeContext", namespace = "genericNrm.xsd")
    private List<MeContext> meContexts;

    @XmlElement(name = "SubNetwork", namespace = "genericNrm.xsd")
    private List<SubNetwork> subNetworks;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MeContext> getMeContexts() {
        return meContexts;
    }

    public void setMeContexts(List<MeContext> meContexts) {
        this.meContexts = meContexts;
    }

    public List<SubNetwork> getSubNetworks() {
        return subNetworks;
    }

    public void setSubNetworks(List<SubNetwork> subNetworks) {
        this.subNetworks = subNetworks;
    }
}

