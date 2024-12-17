package com.hackhud.xml_reader_lab2_oop.strategies;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IStrategy {
    List<String> search(File xmlFile, Map<String, String> searchCriteria);

}

