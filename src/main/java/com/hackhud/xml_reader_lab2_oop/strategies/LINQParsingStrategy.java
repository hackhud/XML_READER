package com.hackhud.xml_reader_lab2_oop.strategies;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LINQParsingStrategy implements IStrategy {
    @Override
    public List<String> search(File xmlFile, Map<String, String> searchCriteria) {
        List<String> results = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList publications = (NodeList) xPath.evaluate("//Publication", document, XPathConstants.NODESET);

            for (int i = 0; i < publications.getLength(); i++) {
                Element publication = (Element) publications.item(i);

                boolean matches = true;
                for (Map.Entry<String, String> criterion : searchCriteria.entrySet()) {
                    String tagName = criterion.getKey();
                    String searchValue = criterion.getValue();
                    if (searchValue == null || searchValue.isEmpty()) continue;

                    NodeList tagNodes = publication.getElementsByTagName(tagName);
                    if (tagNodes.getLength() == 0 ||
                            !tagNodes.item(0).getTextContent().trim().equalsIgnoreCase(searchValue)) {
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
        }        return result.toString();
    }

}

