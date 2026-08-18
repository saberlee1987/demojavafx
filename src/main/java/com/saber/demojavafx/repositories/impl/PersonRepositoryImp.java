package com.saber.demojavafx.repositories.impl;

import com.saber.demojavafx.model.PersonEntity;
import com.saber.demojavafx.repositories.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class PersonRepositoryImp implements PersonRepository {

    private final EntityManager entityManager;

    public PersonRepositoryImp(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<PersonEntity> findAll() {
        return entityManager.createNamedQuery("Person.findAll",PersonEntity.class)
                .getResultList();
    }

    @Override
    public PersonEntity findById(Integer id) {
        return entityManager.createNamedQuery("Person.findById",PersonEntity.class)
                .setParameter("id",id)
                .getSingleResult();
    }

    @Override
    public PersonEntity findByNationalCode(String nationalCode) {
        return entityManager.createNamedQuery("Person.findByNationalCode",PersonEntity.class)
                .setParameter("nationalCode",nationalCode)
                .getSingleResult();
    }

    @Override
    public void save(PersonEntity person) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(person);
        transaction.commit();
    }
}
