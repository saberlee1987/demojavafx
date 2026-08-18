package com.saber.demojavafx.controllers;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.services.PersonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonNewController {
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
    private Button buttonSave;
    @FXML
    private Button buttonCancel;
    private final PersonService personService;
    private Runnable onPersonSaved;

    public PersonNewController(PersonService personService) {
        this.personService = personService;

    }
    @FXML
    public void initialize() {
        buttonSave.setOnAction(this::savePerson);
        buttonCancel.setOnAction(this::cancelPage);
    }
    public void setOnPersonSaved(Runnable onPersonSaved) {
        this.onPersonSaved = onPersonSaved;
    }

    private void cancelPage(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }

    private void savePerson(ActionEvent event) {
        System.out.println("Saving person...");
        PersonDto personDto = validateAndGetPersonDto();
        personService.savePerson(personDto);
        System.out.println("end saving person...");
        // اعلام موفقیت ذخیره به صفحه لیست
        if (onPersonSaved != null) {
            onPersonSaved.run();
        }
        cancelPage(event);
    }

    private PersonDto validateAndGetPersonDto() {
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String age = ageField.getText();
        String mobile = mobileField.getText();
        String nationalCode = nationalCodeField.getText();
        String email = emailField.getText();
        PersonDto personDto = new PersonDto();
        personDto.setFirstname(firstname);
        personDto.setLastname(lastname);
        personDto.setAge(Integer.parseInt(age));
        personDto.setMobile(mobile);
        personDto.setNationalCode(nationalCode);
        personDto.setEmail(email);
        return personDto;
    }
}
