package com.fetch.persist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PersistenceService {

    @Autowired
    PersistenceRepository repo;

    @Transactional
    public <T> Collection<T> findAll(String type) throws JsonProcessingException {
        return repo.findAll(type);
    }

    @Transactional
    public <T> Optional<T> findOne(String type, String attr, String value) throws JsonProcessingException {
        return repo.findOne(type, attr, value);
    }

    @Transactional
    public <T> Collection<T> findAll(String type,String attr, Long value) throws JsonProcessingException {
        return repo.findAll(type, attr, value);
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
        return TypeLookup.getId(ob);
    }
    @Transactional
    public <T> void saveList(String type, String jsonList) throws JsonProcessingException {
        Class<T> c = (Class<T>) TypeLookup.findClass(type);
        List<T> obs = TypeLookup.readJsonList(jsonList, c);
        obs.forEach(o->repo.save(c, o));
    }
    @Transactional
    public <T> void update(String id, String type, String json) throws JsonProcessingException {
        Class<T> c = (Class<T>) TypeLookup.findClass(type);
        T ob = TypeLookup.getObject(json, c);
        repo.update(c, ob);
    }

    @Transactional
    public <T> void delete(Long id, String type) throws JsonProcessingException {
        Class<T> c = (Class<T>) TypeLookup.findClass(type);
        T t = repo.find(c, id);
        repo.delete(t);
    }

    @Transactional
    public <T> void deleteAll(List<Long> ids, String type) throws JsonProcessingException {
        Class<T> c = (Class<T>) TypeLookup.findClass(type);
        ids.forEach(id-> {
            T t = repo.find(c, id);
            repo.delete(t);
        });
    }
}
