package com.saber.demojavafx.services;

import com.saber.demojavafx.dto.PersonDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> getAllPersons();
    PersonDto getPersonById(Integer id);

    void savePerson(PersonDto personDto);
}
