package org.example.repository;

import org.example.entity.Owner;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class OwnerRepository implements Repository<Owner> {
    @Override
    public Owner save(Owner entity) {
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
            var owner = session.get(Owner.class, id);
            if (owner != null) {
                session.remove(owner);
            }
            session.getTransaction().commit();
        }

    }

    @Override
    public void deleteByEntity(Owner entity) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.remove(session.merge((Owner) entity));
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
            session.createQuery("DELETE FROM owners").executeUpdate();
            session.getTransaction().commit();
        }

    }

    @Override
    public Owner update(Owner entity) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.merge((Owner) entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public Owner getById(long id) {
        Configuration configuration = new Configuration();
        configuration.configure();
        Owner owner;
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            owner = session.find(Owner.class, id);
            session.getTransaction().commit();
        }
        return owner;
    }

    @Override
    public List getAll() {
        Configuration configuration = new Configuration();
        configuration.configure();
        List<Owner> owners = new ArrayList<>();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession();) {
            session.beginTransaction();
            owners = session.createQuery("SELECT o FROM owners o", Owner.class).getResultList();
            session.getTransaction().commit();
        }
        return owners;
    }
}
