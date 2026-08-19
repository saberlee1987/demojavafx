package com.saber.demojavafx.controllers;

import com.saber.demojavafx.services.PersonService;

public class PersonViewController extends BasePersonController{
    public PersonViewController(PersonService personService) {
        super(personService);
    }

    @Override
    public void loadData(Integer personId) {
        super.loadData(personId);
        buttonClose.setOnAction(this::cancelPage);
    }
}
