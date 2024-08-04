package com.ericsson.configs_manager.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Represents the configuration data.
 * This class is used to store and retrieve configuration data.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfigData {

    @XmlElement(name = "SubNetwork", namespace = "genericNrm.xsd")
    private List<SubNetwork> subNetworks;

    // Getters and setters
    public List<SubNetwork> getSubNetworks() {
        return subNetworks;
    }

    public void setSubNetworks(List<SubNetwork> subNetworks) {
        this.subNetworks = subNetworks;
    }
}

