package com.fetch.persist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    SearchRepository repo;

    @Transactional
    public <T> List<T> search(String type, String attribute, String value) throws JsonProcessingException {
        Class<T> c = (Class<T>) TypeLookup.findClass(type);
        return repo.search(c, attribute, value);
    }

    @Transactional
    public <T> List<T> searchWildcard(String type, String attribute, String wildCardValue) throws JsonProcessingException {
        Class<T> c = (Class<T>) TypeLookup.findClass(type);
        return repo.searchWildcard(c, attribute, wildCardValue);
    }

    @Transactional
    public void buildIndex()throws InterruptedException{
        repo.buildIndex();
    }

}
