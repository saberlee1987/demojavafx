package com.saber.demojavafx.repositories;

import com.saber.demojavafx.model.PersonEntity;

import java.util.List;

public interface PersonRepository {
    List<PersonEntity> findAll();
    PersonEntity findById(Integer id);
    PersonEntity findByNationalCode(String nationalCode);

    void save(PersonEntity person);
}
