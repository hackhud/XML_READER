package com.hackhud.xml_reader_lab2_oop.factories;

import com.hackhud.xml_reader_lab2_oop.strategies.*;

import javafx.scene.control.RadioButton;

public class StrategyFactory {
    public static IStrategy getStrategy(RadioButton dom, RadioButton sax, RadioButton linq) {
        if (dom.isSelected()) return new DOMParsingStrategy();
        if (sax.isSelected()) return new SAXParsingStrategy();
        if (linq.isSelected()) return new LINQParsingStrategy();
        return null;
    }
}
