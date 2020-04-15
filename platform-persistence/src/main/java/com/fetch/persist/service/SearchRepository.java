package com.fetch.persist.service;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SearchRepository {

    @PersistenceContext
    EntityManager em;

    public <T> List<T> search(Class<T> clazz, String attribute, String value) {
        FullTextEntityManager fem
                = Search.getFullTextEntityManager(em);

        QueryBuilder queryBuilder = fem.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(clazz)
                .get();
        Query query = queryBuilder
                .keyword()
                .onField(attribute)
                .matching(value)
                .createQuery();
        FullTextQuery jpaQuery
                = fem.createFullTextQuery(query, clazz);
        List<T> results = jpaQuery.getResultList();
        return results;
    }

    public <T> List<T> searchWildcard(Class<T> clazz, String attribute, String wildCardValue) {
        FullTextEntityManager fem
                = Search.getFullTextEntityManager(em);
        QueryBuilder queryBuilder = fem.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(clazz)
                .get();
        Query query = queryBuilder
                .keyword()
                .wildcard()
                .onField(attribute)
                .matching(wildCardValue.toLowerCase())
                .createQuery();
        FullTextQuery jpaQuery
                = fem.createFullTextQuery(query, clazz);
        List<T> results = jpaQuery.getResultList();
        return results;
    }


    public void buildIndex() throws InterruptedException {
        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(em);
        fullTextEntityManager.createIndexer().startAndWait();
    }


}
