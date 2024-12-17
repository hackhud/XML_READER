package com.hackhud.xml_reader_lab2_oop.services;

import com.hackhud.xml_reader_lab2_oop.strategies.IStrategy;

import java.io.File;
import java.util.List;
import java.util.Map;

public class SearchService {
    public String search(IStrategy strategy, File file, Map<String, String> criteria) {
        List<String> results = strategy.search(file, criteria);
        return results.isEmpty() ? "Нічого не знайдено." : String.join("\n", results);
    }
}
