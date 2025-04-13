package com.elnurkarimli.javafx_ibb_ecodation;

import com.elnurkarimli.javafx_ibb_ecodation.utils.ResourceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.Locale;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private Button exitButton;

    @FXML
    private Label greetingLabel;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.textProperty().bind(ResourceManager.getFactory().getStringBinding("welcome"));
    }

    @FXML
    private void onLanguageChange() {
        String selected = languageComboBox.getValue();
        if ("English".equals(selected)) {
            ResourceManager.changeLanguage(new Locale("en", "US"));
        } else {
            ResourceManager.changeLanguage(new Locale("tr", "TR"));
        }
    }

    @FXML
    public void initialize() {
        // Dil dosyasındaki anahtarlara etiketleri bağla
        greetingLabel.textProperty().bind(ResourceManager.getFactory().getStringBinding("greeting"));
        exitButton.textProperty().bind(ResourceManager.getFactory().getStringBinding("exit"));
        welcomeText.textProperty().bind(ResourceManager.getFactory().getStringBinding("welcome"));

        // ComboBox'a dil seçeneklerini ekle
        languageComboBox.getItems().addAll("Türkçe", "English");
        languageComboBox.setValue("Türkçe"); // Varsayılan olarak Türkçe göster
    }
}
