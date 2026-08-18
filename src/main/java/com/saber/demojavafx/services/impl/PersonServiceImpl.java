package com.saber.demojavafx.services.impl;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.mapper.PersonMapper;
import com.saber.demojavafx.model.PersonEntity;
import com.saber.demojavafx.repositories.PersonRepository;
import com.saber.demojavafx.repositories.impl.PersonRepositoryImp;
import com.saber.demojavafx.services.PersonService;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(EntityManagerFactory entityManagerFactory) {
        personRepository = new PersonRepositoryImp(entityManagerFactory);
    }

    @Override
    public List<PersonDto> getAllPersons() {
        List<PersonEntity> persons = personRepository.findAll();
        return PersonMapper.modelToDto(persons);
    }

    @Override
    public PersonDto getPersonById(Integer id) {
        PersonEntity personEntity = personRepository.findById(id);
        return PersonMapper.modelToDto(personEntity);
    }
}
