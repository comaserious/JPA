package com.javaclass.associationmapping.section02.onetomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OneToManyRepository {

    @PersistenceContext
    private EntityManager manager;

    public Category findCategory(int categoryCode) {

        return manager.find(Category.class,categoryCode);
    }

    public void registCategory(Category category) {

        manager.persist(category);
    }
}
