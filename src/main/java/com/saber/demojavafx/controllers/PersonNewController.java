package com.saber.demojavafx.controllers;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.services.PersonService;
import com.saber.demojavafx.utils.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PersonNewController extends BasePersonController {
    private final PersonService personService;
    public PersonNewController(PersonService personService) {
        super(personService);
        this.personService = personService;

    }
    @FXML
    public void initialize() {
        buttonSave.setOnAction(this::savePerson);
        buttonCancel.setOnAction(this::cancelPage);
    }
    private void savePerson(ActionEvent event) {
        clearErrorLabels();
        PersonDto personDto = validateAndGetPersonDto();
        if (showValidationError(personDto)) return;
        try {
            personService.checkRulesForPerson(personDto);
            personService.savePerson(personDto);
            Utilities.showDialog("عملیات درج موفق","عملیات درج شخص با موفقیت انجام شد", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            Utilities.showDialog("خطا در هنگام درج شخص جدید", e.getMessage(), Alert.AlertType.ERROR);
        }
         cancelPage(event);
    }
}
