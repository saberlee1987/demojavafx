package com.saber.demojavafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SampleRegisterPersonController {
    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField ageField;

    @FXML
    private Button buttonSavePerson;

    @FXML
    public void initialize() {

        buttonSavePerson.setOnAction(this::registerPerson);
    }

    private void registerPerson(ActionEvent event) {
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String ageStr = ageField.getText();

        System.out.println("firstname ===> "+firstname);
        System.out.println("lastname ===> "+lastname);
        System.out.println("ageStr ===> "+ageStr);
    }
}
