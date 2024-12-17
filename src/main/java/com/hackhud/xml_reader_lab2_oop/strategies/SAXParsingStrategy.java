package com.hackhud.xml_reader_lab2_oop.strategies;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAXParsingStrategy implements IStrategy {
    @Override
    public List<String> search(File xmlFile, Map<String, String> searchCriteria) {
        List<String> results = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                boolean isPublication = false;
                Map<String, String> currentPublication = new HashMap<>();
                StringBuilder currentTagContent = new StringBuilder();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if ("Publication".equals(qName)) {
                        isPublication = true;
                        currentPublication.clear();
                    }
                    currentTagContent.setLength(0);
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (isPublication) {
                        if (!"Publication".equals(qName)) {
                            currentPublication.put(qName, currentTagContent.toString().trim());
                        } else {
                            if (matchesCriteria(currentPublication, searchCriteria)) {
                                results.add(mapToString(currentPublication));
                            }
                            isPublication = false;
                        }
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    currentTagContent.append(ch, start, length);
                }
            };

            saxParser.parse(xmlFile, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private boolean matchesCriteria(Map<String, String> publication, Map<String, String> searchCriteria) {
        for (Map.Entry<String, String> criterion : searchCriteria.entrySet()) {
            String tagName = criterion.getKey();
            String searchValue = criterion.getValue();
            if (!publication.getOrDefault(tagName, "").equals(searchValue)) {
                return false;
            }
        }
        return true;
    }

    private String mapToString(Map<String, String> publication) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : publication.entrySet()) {
            result.append("<").append(entry.getKey()).append(">")
                    .append(entry.getValue())
                    .append("</").append(entry.getKey()).append(">\n");
        }
        return result.toString();
    }
}

