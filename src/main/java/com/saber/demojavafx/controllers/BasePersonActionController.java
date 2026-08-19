package com.saber.demojavafx.controllers;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.services.PersonService;
import com.saber.demojavafx.utils.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Map;

public class BasePersonActionController {
    @FXML
    protected TextField firstnameField;
    @FXML
    protected TextField lastnameField;
    @FXML
    protected TextField ageField;
    @FXML
    protected TextField mobileField;
    @FXML
    protected TextField nationalCodeField;
    @FXML
    protected TextField emailField;

    @FXML
    protected TextField createdAtField;
    @FXML
    protected TextField updatedAtField;
    @FXML
    protected Button buttonSave;

    @FXML
    protected Button buttonEdit;
    @FXML
    protected Button buttonCancel;

    @FXML
    protected Button buttonClose;

    @FXML
    protected Label firstnameLabel;
    @FXML
    protected Label lastnameLabel;
    @FXML
    protected Label nationalCodeLabel;
    @FXML
    protected Label ageLabel;
    @FXML
    protected Label mobileLabel;
    @FXML
    protected Label emailLabel;

    private final PersonService personService;

    public BasePersonActionController(PersonService personService) {
        this.personService = personService;
    }

    protected void cancelPage(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource())
                .getScene()
                .getWindow();
        stage.close();
    }
    protected boolean showValidationError(PersonDto personDto) {
        if (personDto.getErrors() != null && !personDto.getErrors().isEmpty()) {
            for (Map.Entry<String, String> entry : personDto.getErrors().entrySet()) {
                String field = entry.getKey();
                String errorMessage = entry.getValue();
                switch (field) {
                    case "firstname":
                        firstnameLabel.setText(errorMessage);
                        firstnameField.setTooltip(new Tooltip(errorMessage));
                        break;
                    case "lastname":
                        lastnameLabel.setText(errorMessage);
                        lastnameField.setTooltip(new Tooltip(errorMessage));
                        break;
                    case "nationalCode":
                        nationalCodeLabel.setText(errorMessage);
                        nationalCodeField.setTooltip(new Tooltip(errorMessage));
                        break;
                    case "age":
                        ageLabel.setText(errorMessage);
                        ageField.setTooltip(new Tooltip(errorMessage));
                        break;
                    case "email":
                        emailLabel.setText(errorMessage);
                        emailField.setTooltip(new Tooltip(errorMessage));
                        break;
                    case "mobile":
                        mobileLabel.setText(errorMessage);
                        mobileField.setTooltip(new Tooltip(errorMessage));
                        break;
                }
            }
            Utilities.showDialog("خطا در اعتبار سنجی اطلاعات شخص",
                    "اطلاعات شخص مورد نظر را بدرستی وارد کنید"
                    , Alert.AlertType.ERROR);
            return true;
        }
        return false;
    }

    protected void clearErrorLabels() {
        firstnameLabel.setText(null);
        firstnameField.setTooltip(null);
        lastnameLabel.setText(null);
        lastnameField.setTooltip(null);
        nationalCodeLabel.setText(null);
        nationalCodeField.setTooltip(null);
        ageLabel.setText(null);
        ageField.setTooltip(null);
        emailLabel.setText(null);
        emailField.setTooltip(null);
        mobileLabel.setText(null);
        mobileField.setTooltip(null);
    }

    protected PersonDto validateAndGetPersonDto() {
        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String age = ageField.getText();
        String mobile = mobileField.getText();
        String nationalCode = nationalCodeField.getText();
        String email = emailField.getText();
        PersonDto personDto = new PersonDto();
        personDto.setFirstname(firstname);
        personDto.setLastname(lastname);
        personDto.setAge(age !=null && age.matches("\\d+") ? Integer.parseInt(age) : null);
        personDto.setMobile(mobile);
        personDto.setNationalCode(nationalCode);
        personDto.setEmail(email);
        Map<String, String> errors = Utilities.validatePerson(personDto);
        personDto.setErrors(errors);
        return personDto;
    }

    public void loadData(Integer personId) {
        try {
            PersonDto person = personService.getPersonById(personId);
            firstnameField.setText(person.getFirstname());
            lastnameField.setText(person.getLastname());
            nationalCodeField.setText(person.getNationalCode());
            emailField.setText(person.getEmail());
            ageField.setText(String.valueOf(person.getAge()));
            mobileField.setText(person.getMobile());
            createdAtField.setText(person.getCreatedAt());
            updatedAtField.setText(person.getUpdatedAt());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            Utilities.showDialog("خطا در واکشی اطلاعات شخص",e.getMessage(), Alert.AlertType.ERROR);
        }

    }
}
