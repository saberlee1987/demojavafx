package com.saber.demojavafx.services.impl;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.mapper.PersonMapper;
import com.saber.demojavafx.model.PersonEntity;
import com.saber.demojavafx.repositories.PersonRepository;
import com.saber.demojavafx.repositories.impl.PersonRepositoryImp;
import com.saber.demojavafx.services.PersonService;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(EntityManagerFactory entityManagerFactory) {
        personRepository = new PersonRepositoryImp(entityManagerFactory);
    }



    @Override
    public List<PersonDto> getAllPersons(String search, int page, int pageSize) {
        List<PersonEntity> persons = personRepository.findAll(search,page,pageSize);
        return PersonMapper.modelToDto(persons);
    }

    @Override
    public Long countPersons() {
        return personRepository.countPersons();
    }

    @Override
    public PersonDto getPersonById(Integer id) {
        Optional<PersonEntity> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isEmpty())
            throw new RuntimeException("شخص مورد نظر یافت نشد");
        return PersonMapper.modelToDto(optionalPerson.get());
    }

    @Override
    public void savePerson(PersonDto personDto) {
        PersonEntity personEntity = PersonMapper.dtoToModel(personDto);
        personRepository.save(personEntity);
    }

    @Override
    public void updatePerson(PersonDto personDto) {
        personRepository.update(personDto);
    }

    @Override
    public void checkRulesForPerson(PersonDto personDto) {
        checkRulesForPerson(personDto,null);
    }

    @Override
    public void checkRulesForPerson(PersonDto personDto,Integer personId) {
        String nationalCode = personDto.getNationalCode();
        Optional<PersonEntity> optionalPersonEntity = personRepository.findByNationalCode(nationalCode);
        if (optionalPersonEntity.isPresent() && (personId != null && !optionalPersonEntity.get().getId().equals(personId))) {
            throw new RuntimeException("کد ملی مورد نظر قبلا برای شخصی دیگر درج شده است");
        }
    }

    @Override
    public void deletePersonById(Integer id) {
        personRepository.deleteById(id);
    }
}
