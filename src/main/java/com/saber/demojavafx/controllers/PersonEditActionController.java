package com.saber.demojavafx.controllers;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.services.PersonService;
import com.saber.demojavafx.utils.Utilities;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class PersonEditActionController extends BasePersonActionController {
    private final PersonService personService;

    public PersonEditActionController(PersonService personService) {
        super(personService);
        this.personService = personService;
    }
    @Override
    public void loadData(Integer personId) {
        super.loadData(personId);
        buttonEdit.setOnAction((event)->update(event,personId));
        buttonCancel.setOnAction(this::cancelPage);
    }

    private void update(ActionEvent event, Integer personId) {
        clearErrorLabels();
        PersonDto personDto = validateAndGetPersonDto();
        if (showValidationError(personDto)) return;
        try {
            personDto.setId(personId);
            personService.checkRulesForPerson(personDto, personId);
            personService.updatePerson(personDto);
            Utilities.showDialog("عملیات ویرایش موفق","عملیات ویرایش شخص با موفقیت انجام شد", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            Utilities.showDialog("خطا در هنگام ویرایش شخص جدید", e.getMessage(), Alert.AlertType.ERROR);
        }
        cancelPage(event);
    }
}