package com.saber.demojavafx.repositories.impl;

import com.saber.demojavafx.dto.PersonDto;
import com.saber.demojavafx.mapper.PersonMapper;
import com.saber.demojavafx.model.PersonEntity;
import com.saber.demojavafx.repositories.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class PersonRepositoryImp implements PersonRepository {

    private final EntityManager entityManager;

    public PersonRepositoryImp(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<PersonEntity> findAll(String search,int page,int pageSize) {
        if (search !=null && !search.isEmpty()) {
            return entityManager.createNamedQuery("Person.findBySearch",PersonEntity.class)
                    .setParameter("search",search)
                    .setFirstResult(page  * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
        return entityManager.createNamedQuery("Person.findAll",PersonEntity.class)
                .setFirstResult(page  * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public Long countPersons() {
        return entityManager.createQuery("select count(p.id) from PersonEntity p",Long.class)
                .getSingleResult();
    }

    @Override
    public Optional<PersonEntity> findById(Integer id) {
        return  Optional.ofNullable(entityManager.createNamedQuery("Person.findById",PersonEntity.class)
                .setParameter("id",id)
                .getSingleResult());
    }

    @Override
    public Optional<PersonEntity> findByNationalCode(String nationalCode) {
        try {
            return Optional.of(entityManager.createNamedQuery("Person.findByNationalCode", PersonEntity.class)
                    .setParameter("nationalCode", nationalCode)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(PersonEntity person) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(person);
        transaction.commit();
    }

    @Override
    public void update(PersonDto person) {
         EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            PersonEntity personEntity = entityManager.find(PersonEntity.class, person.getId());
            PersonMapper.setPersonDtoToEntity(person,personEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        PersonEntity personEntity = entityManager.find(PersonEntity.class, id);
        entityManager.remove(personEntity);
        transaction.commit();
    }
}
