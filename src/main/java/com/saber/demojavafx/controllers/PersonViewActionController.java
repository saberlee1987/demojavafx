package com.saber.demojavafx.controllers;

import com.saber.demojavafx.services.PersonService;

public class PersonViewActionController extends BasePersonActionController {
    public PersonViewActionController(PersonService personService) {
        super(personService);
    }

    @Override
    public void loadData(Integer personId) {
        super.loadData(personId);
        buttonClose.setOnAction(this::cancelPage);
    }
}
