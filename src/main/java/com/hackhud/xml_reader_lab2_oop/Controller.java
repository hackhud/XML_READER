package com.hackhud.xml_reader_lab2_oop;

import com.hackhud.xml_reader_lab2_oop.strategies.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    @FXML
    private MenuButton listData;
    @FXML
    private MenuButton listDepartment;
    @FXML
    private MenuButton listFaculty;
    @FXML
    private MenuButton listName;

    @FXML
    private RadioButton radioBtnDom;
    @FXML
    private RadioButton radioBtnLinq;
    @FXML
    private RadioButton radioBtnSax;

    @FXML
    private TextArea resultLabel;
    @FXML
    private Button searchButton;

    @FXML
    private ToggleButton toggleButtonName;
    @FXML
    private ToggleButton toggleButtonFaculty;
    @FXML
    private ToggleButton toggleButtonDepartment;
    @FXML
    private ToggleButton toggleButtonData;
    @FXML
    private Button exitButton;

    @FXML
    private Button clearButton;


    public void initialize() {
        searchButton.setOnAction(actionevent -> executeSearch());
        exitButton.setOnAction(actionEvent -> executeExit());
        clearButton.setOnAction(actionEvent -> clearFields());

        XMLParser.parseAndFillMenu(ApplicationMain.getFile(), listData, listDepartment, listFaculty, listName);

        addMenuButtonListener(listData);
        addMenuButtonListener(listDepartment);
        addMenuButtonListener(listFaculty);
        addMenuButtonListener(listName);

        resultLabel.setEditable(false);
        resultLabel.setWrapText(true);
    }

    private void clearFields() {
        resultLabel.setText("");
        listData.setText("");
        listDepartment.setText("");
        listFaculty.setText("");
        listName.setText("");
    }
    private void executeSearch() {
        Map<String, String> searchCriteria = new HashMap<>();

        if (!listName.getText().isEmpty()) {
            searchCriteria.put("Name", listName.getText());
        }
        if (!listFaculty.getText().isEmpty()) {
            searchCriteria.put("Faculty", listFaculty.getText());
        }
        if (!listDepartment.getText().isEmpty()) {
            searchCriteria.put("Department", listDepartment.getText());
        }
        if (!listData.getText().isEmpty()) {
            searchCriteria.put("Data", listData.getText());
        }

        if (searchCriteria.isEmpty()) {
            resultLabel.setText("Виберіть критерії для пошуку!");
            return;
        }

        IStrategy strategy = null;
        if (radioBtnDom.isSelected()) {
            strategy = new DOMParsingStrategy();
        } else if (radioBtnSax.isSelected()) {
            strategy = new SAXParsingStrategy();
        } else if (radioBtnLinq.isSelected()) {
            strategy = new LINQParsingStrategy();
        }

        if (strategy == null) {
            resultLabel.setText("Виберіть метод пошуку!");
            return;
        }

        List<String> results = strategy.search(ApplicationMain.getFile(), searchCriteria);
        if (results.isEmpty()) {
            resultLabel.setText("Нічого не знайдено.");
        } else {
            resultLabel.setText(String.join("\n", results));
        }
    }




    private void executeExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Покинути програму?");

        ButtonType yesButton = new ButtonType("Так");
        ButtonType noButton = new ButtonType("Ні");

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                Platform.exit();
            }
        });
    }

    private void addMenuButtonListener(MenuButton menuButton) {
        for (MenuItem menuItem : menuButton.getItems()) {
            menuItem.setOnAction(event -> menuButton.setText(menuItem.getText()));
        }
    }
}
