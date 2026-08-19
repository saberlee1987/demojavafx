package com.saber.demojavafx.repositories;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.model.PersonEntity;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<PersonEntity> findAll(String search,int page,int pageSize);
    Long countPersons();
    Optional<PersonEntity> findById(Integer id);
    Optional<PersonEntity> findByNationalCode(String nationalCode);

    void save(PersonEntity person);
    void update(PersonDto person);

    void deleteById(Integer id);
}
