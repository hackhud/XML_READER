package com.hackhud.xml_reader_lab2_oop.strategies;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DOMParsingStrategy implements IStrategy {
    @Override
    public List<String> search(File xmlFile, Map<String, String> searchCriteria) {
        List<String> results = new ArrayList<>();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            NodeList publications = document.getElementsByTagName("Publication");

            for (int i = 0; i < publications.getLength(); i++) {
                Element publication = (Element) publications.item(i);

                boolean matches = true;
                for (Map.Entry<String, String> criterion : searchCriteria.entrySet()) {
                    String tagName = criterion.getKey();
                    String searchValue = criterion.getValue();

                    NodeList tagNodes = publication.getElementsByTagName(tagName);
                    if (tagNodes.getLength() == 0 || !tagNodes.item(0).getTextContent().trim().equals(searchValue)) {
                        matches = false;
                        break;
                    }
                }

                if (matches) {
                    results.add(nodeToString(publication));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private String nodeToString(Element node) {
        StringBuilder result = new StringBuilder();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                result.append("<").append(child.getNodeName()).append(">")
                        .append(child.getTextContent().trim())
                        .append("</").append(child.getNodeName()).append(">\n");
            }
        }
        return result.toString();
    }

}
