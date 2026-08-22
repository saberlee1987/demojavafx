package com.saber.demojavafx.controllers;

import com.saber.demojavafx.utils.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SampleBorderPaneController {
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private Button sayHelloButton;

    public SampleBorderPaneController() {
        System.out.println("constructor sampleBorder Pane Controller");
    }

    @FXML
    public void initialize() {
        System.out.println("initialize sampleBorder Pane Controller");
        sayHelloButton.setOnAction(this::sayHello);
    }
    private void sayHello(ActionEvent event) {
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String messageSayHell = String.format("Hello %s %s",firstname,lastname);
        Utilities.showDialog("say hello",messageSayHell, Alert.AlertType.INFORMATION);
    }
}
