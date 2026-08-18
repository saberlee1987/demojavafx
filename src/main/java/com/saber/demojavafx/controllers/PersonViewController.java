package com.saber.demojavafx.controllers;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.services.PersonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonViewController {
    @FXML
    private TextField idField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField mobileField;
    @FXML
    private TextField nationalCodeField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField createdAtField;
    @FXML
    private TextField updatedAtField;
    @FXML
    private Button buttonClose;
    private final PersonService personService;

    public PersonViewController(PersonService personService) {
        this.personService = personService;
    }

    public void loadData(Integer personId) {
        PersonDto person = personService.getPersonById(personId);
        idField.setText(String.valueOf(personId));
        firstnameField.setText(person.getFirstname());
        lastnameField.setText(person.getLastname());
        nationalCodeField.setText(person.getNationalCode());
        emailField.setText(person.getEmail());
        ageField.setText(String.valueOf(person.getAge()));
        mobileField.setText(person.getMobile());
        createdAtField.setText(person.getCreatedAt());
        updatedAtField.setText(person.getUpdatedAt());
        buttonClose.setOnAction(this::close);
    }
    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
}
