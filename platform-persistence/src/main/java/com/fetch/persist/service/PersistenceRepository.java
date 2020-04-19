package com.fetch.persist.service;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class PersistenceRepository {

    @PersistenceContext
    EntityManager em;

    public <T> Collection<T> findAll(String type) {
        Query query = em.createQuery("SELECT e FROM "+type+" e");
        return (Collection<T>) query.getResultList();
    }

    public <T> Optional<T> findOne(String type, String attribute, String value) {
        Query query = em.createQuery("SELECT e FROM "+type+" e WHERE "+attribute +" = ?1" );
        query.setParameter(1, value);
        List<T> c = (List<T>) query.getResultList();
        if(c.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(c.get(0));
    }
    public <T> Collection<T> findAll(String type, String attribute, Long value) {
        Query query = em.createQuery("SELECT e FROM "+type+" e WHERE "+attribute +" = ?1" );
        query.setParameter(1, value);
        return (Collection<T>) query.getResultList();
    }

    public <T> T find(Class<T> clazz, Long id) {
        return em.find(clazz, id);

    }

    public <T> T save(Class<T> clazz, T t) {
        em.persist(t);
        em.flush();
        return t;
    }

    public <T> void update(Class<T> clazz, T t) {
        em.merge(t);em.flush();
    }

}
