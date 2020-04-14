package com.fetch.persist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class PersistenceService {

    @Autowired
    PersistenceRepository repo;

    @Transactional
    public <T> Collection<T> findAll(String type) throws JsonProcessingException {
        return repo.findAll(type);
    }

    @Transactional
    public <T> T find(String id, String type) throws JsonProcessingException {
        Class<T> c = (Class<T>) TypeLookup.findClass(type);
        return repo.find(c, Long.parseLong(id));
    }

    @Transactional
    public <T> long save(String type, String json) throws JsonProcessingException {
        Class<T> c = (Class<T>) TypeLookup.findClass(type);
        T ob = TypeLookup.getObject(json, c);
        repo.save(c, ob);
        return TypeLookup.getId(ob, type);
    }
    @Transactional
    public <T> void update(String id, String type, String json) throws JsonProcessingException {
        Class<T> c = (Class<T>) TypeLookup.findClass(type);
        T ob = TypeLookup.getObject(json, c);
        repo.update(c, ob);
    }


}
