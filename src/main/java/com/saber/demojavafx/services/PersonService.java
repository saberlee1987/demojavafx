package com.saber.demojavafx.services;

import com.saber.demojavafx.dto.PersonDto;

import java.util.List;

public interface PersonService {

    List<PersonDto> getAllPersons(String search,int page,int pageSize);
    Long countPersons();
    PersonDto getPersonById(Integer id);

    void savePerson(PersonDto personDto);
    void savePersons(List<PersonDto> persons);
    void updatePerson(PersonDto personDto);
    void checkRulesForPerson(PersonDto personDto);
    void checkRulesForPerson(List<PersonDto> persons);
    void checkRulesForPerson(PersonDto personDto,Integer personId);

    void deletePersonById(Integer id);
}
