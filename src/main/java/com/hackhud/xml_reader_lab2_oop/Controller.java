package com.hackhud.xml_reader_lab2_oop;

import com.hackhud.xml_reader_lab2_oop.factories.StrategyFactory;
import com.hackhud.xml_reader_lab2_oop.services.AlertService;
import com.hackhud.xml_reader_lab2_oop.services.SearchService;
import com.hackhud.xml_reader_lab2_oop.strategies.IStrategy;
import com.hackhud.xml_reader_lab2_oop.strategies.XMLParser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;

public class Controller {

    @FXML private MenuButton listData, listDepartment, listFaculty, listName;
    @FXML private RadioButton radioBtnDom, radioBtnLinq, radioBtnSax;
    @FXML private TextArea resultLabel;
    @FXML private Button searchButton, clearButton, exitButton;

    private final SearchService searchService = new SearchService();
    private final AlertService alertService = new AlertService();

    public void initialize() {
        setupUI();

        XMLParser.parseAndFillMenu(ApplicationMain.getFile(), listData, listDepartment, listFaculty, listName);

        addMenuButtonListener(listData);
        addMenuButtonListener(listDepartment);
        addMenuButtonListener(listFaculty);
        addMenuButtonListener(listName);
    }

    private void setupUI() {
        searchButton.setOnAction(event -> executeSearch());
        clearButton.setOnAction(event -> clearFields());
        exitButton.setOnAction(event -> executeExit());

        addMenuButtonListener(listData);
        addMenuButtonListener(listDepartment);
        addMenuButtonListener(listFaculty);
        addMenuButtonListener(listName);

        resultLabel.setEditable(false);
        resultLabel.setWrapText(true);
    }

    private void executeSearch() {
        Map<String, String> searchCriteria = collectSearchCriteria();

        if (searchCriteria.isEmpty()) {
            resultLabel.setText("Виберіть критерії для пошуку!");
            return;
        }

        IStrategy strategy = StrategyFactory.getStrategy(radioBtnDom, radioBtnSax, radioBtnLinq);

        if (strategy == null) {
            resultLabel.setText("Виберіть метод пошуку!");
            return;
        }

        String result = searchService.search(strategy, ApplicationMain.getFile(), searchCriteria);
        resultLabel.setText(result);
    }

    private Map<String, String> collectSearchCriteria() {
        Map<String, String> criteria = new HashMap<>();
        if (!listName.getText().isEmpty()) criteria.put("Name", listName.getText());
        if (!listFaculty.getText().isEmpty()) criteria.put("Faculty", listFaculty.getText());
        if (!listDepartment.getText().isEmpty()) criteria.put("Department", listDepartment.getText());
        if (!listData.getText().isEmpty()) criteria.put("Data", listData.getText());
        return criteria;
    }

    private void clearFields() {
        resultLabel.clear();
        listData.setText("");
        listDepartment.setText("");
        listFaculty.setText("");
        listName.setText("");
    }

    private void executeExit() {
        if (alertService.showExitConfirmation()) {
            Platform.exit();
        }
    }

    private void addMenuButtonListener(MenuButton menuButton) {
        menuButton.getItems().forEach(item ->
                item.setOnAction(event -> menuButton.setText(item.getText()))
        );
    }
}
