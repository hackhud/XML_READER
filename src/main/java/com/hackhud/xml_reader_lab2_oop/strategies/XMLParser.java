package com.hackhud.xml_reader_lab2_oop.strategies;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class XMLParser {

    public static void parseAndFillMenu(File xmlFile, MenuButton listData, MenuButton listDepartment, MenuButton listFaculty, MenuButton listName) {
        Set<String> dataValues = new HashSet<>();
        Set<String> departmentValues = new HashSet<>();
        Set<String> facultyValues = new HashSet<>();
        Set<String> nameValues = new HashSet<>();

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            dataValues = parseTag(document, "Data");
            departmentValues = parseTag(document, "Department");
            facultyValues = parseTag(document, "Faculty");
            nameValues = parseTag(document, "Name");

            populateMenuButton(listData, dataValues);
            populateMenuButton(listDepartment, departmentValues);
            populateMenuButton(listFaculty, facultyValues);
            populateMenuButton(listName, nameValues);

        } catch (Exception e) {
            System.out.println("XML Parsing error: " + e.getMessage());
        }
    }

    private static Set<String> parseTag(Document document, String tagName) {
        Set<String> values = new HashSet<>();
        NodeList nodeList = document.getElementsByTagName(tagName);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String nodeValue = node.getTextContent().trim();
            if (!nodeValue.isEmpty()) {
                values.add(nodeValue);
            }
        }
        return values;
    }

    private static void populateMenuButton(MenuButton menuButton, Set<String> values) {
        menuButton.getItems().clear();
        for (String value : values) {
            MenuItem menuItem = new MenuItem(value);
            menuButton.getItems().add(menuItem);
        }
    }
}
