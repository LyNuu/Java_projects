package org.example.service;

import org.example.entity.Pet;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class PetСontroller implements Service {


    @Override
    public Object save(Object entity) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public void deleteById(long id) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            var pet = session.get(Pet.class, id);
            if (pet != null) {
                session.remove(pet);
            }
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteByEntity(Object entity) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.remove(session.merge((Pet) entity));
            session.getTransaction().commit();
        }

    }

    @Override
    public void deleteAll() {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.createQuery("DELETE FROM pets").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public Object update(Object entity) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.merge((Pet) entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public Object getById(long id) {
        Configuration configuration = new Configuration();
        configuration.configure();
        Pet pet;
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            pet = session.find(Pet.class, id);
            session.getTransaction().commit();
        }
        return pet;
    }

    @Override
    public List<Pet> getAll() {
        Configuration configuration = new Configuration();
        configuration.configure();
        List<Pet> pets = new ArrayList<>();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            pets = session.createQuery("SELECT p FROM Pet p", Pet.class).getResultList();
            session.getTransaction().commit();
        }
        return pets;
    }
}
