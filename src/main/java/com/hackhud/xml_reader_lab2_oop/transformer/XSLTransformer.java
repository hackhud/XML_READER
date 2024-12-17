package com.hackhud.xml_reader_lab2_oop.transformer;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLTransformer {
    public static void transform(File xmlFile, File xslFile, File outputFile) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
            transformer.transform(new StreamSource(xmlFile), new StreamResult(outputFile));
            System.out.println("Transformation complete.");
        } catch (Exception e) {
            System.out.println("Transformation error: " + e.getMessage());
        }
    }
}
