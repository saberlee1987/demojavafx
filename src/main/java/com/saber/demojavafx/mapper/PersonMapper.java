package com.saber.demojavafx.mapper;

import com.github.mfathi91.time.PersianDate;
import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.model.PersonEntity;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PersonMapper {

    public static PersonDto modelToDto(PersonEntity personEntity) {
        PersonDto personDto = new PersonDto();
        personDto.setId( personEntity.getId());
        personDto.setFirstname( personEntity.getFirstname());
        personDto.setLastname( personEntity.getLastname());
        personDto.setAge( personEntity.getAge());
        personDto.setMobile( personEntity.getMobile());
        personDto.setNationalCode( personEntity.getNationalCode());
        personDto.setEmail( personEntity.getEmail());
        if (personEntity.getCreatedAt() != null)
            personDto.setCreatedAt(PersianDate.fromGregorian(personEntity.getCreatedAt().toLocalDate())
                    .format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        if (personEntity.getUpdatedAt() != null)
            personDto.setUpdatedAt(PersianDate.fromGregorian(personEntity.getUpdatedAt().toLocalDate())
                    .format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        return personDto;
    }
    public static List<PersonDto> modelToDto(List<PersonEntity> personEntities) {
        List<PersonDto> persons = new ArrayList<>();
        for (PersonEntity personEntity : personEntities) {
            persons.add(modelToDto(personEntity));
        }
        return persons;
    }
}
