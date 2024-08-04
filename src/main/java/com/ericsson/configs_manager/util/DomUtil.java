package com.ericsson.configs_manager.util;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class for working with DOM elements.
 */
public class DomUtil {

    /**
     * Retrieves the context ID from the given MeContext element.
     *
     * @param meContextElement The MeContext element from which to retrieve the context ID.
     * @return The context ID as a String, or null if the element is not a MeContext.
     */
    public static String getMeContextId(Element meContextElement) {

        if (meContextElement.getNodeName().contains("MeContext")) {
            return meContextElement.getAttribute("id");
        }

        return null;
    }
    
    /**
     * Returns the key for the given XML element.
     * The key is built using the element name and its attributes.
     *
     * @param element the XML element
     * @return the key for the element
     */
    public static String getElementKey(Element element) {
        
        // Build key with element name and its attributes
        StringBuilder keyBuilder = new StringBuilder(element.getNodeName());

        if (element.hasAttributes()) {
            for (int i = 0; i < element.getAttributes().getLength(); i++) {
                String attrName = element.getAttributes().item(i).getNodeName();
                String attrValue = element.getAttributes().item(i).getNodeValue();
                keyBuilder.append(" ").append(attrName).append("='").append(attrValue).append("'");
            }
        }

        return keyBuilder.toString();
    }

    /**
     * Builds a hierarchy map from the given XML element.
     * The hierarchy map represents the nested structure of the XML element and its child elements.
     * Each element is represented as a key-value pair in the map, where the key is the element's tag name
     * and the value is either a nested hierarchy map or the text content of the element.
     * 
     * @param element the XML element to build the hierarchy from
     * @return a map representing the hierarchy of the XML element
     */
    public static Map<String, Object> buildHierarchy(Element element) {
        Map<String, Object> hierarchyMap = new HashMap<>();
        NodeList childNodes = element.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                String key = getElementKey(childElement);

                // Recursively build the hierarchy for this child
                Map<String, Object> childHierarchy = buildHierarchy(childElement);

                if (childHierarchy.isEmpty()) {
                    // If the child has no further children, use its text content as value
                    childHierarchy.put("value", childElement.getTextContent().trim());
                }

                // Check if the child has exactly one child and should be a key-value pair
                if (childHierarchy.size() == 1 && childHierarchy.containsKey("value")) {
                    hierarchyMap.put(key, childHierarchy.get("value"));
                } else {
                    hierarchyMap.put(key, childHierarchy);
                }
            }
        }

        return hierarchyMap;
    }

}
