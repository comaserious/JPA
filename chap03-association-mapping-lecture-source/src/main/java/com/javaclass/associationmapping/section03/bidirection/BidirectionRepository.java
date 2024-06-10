package com.javaclass.associationmapping.section03.bidirection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BidirectionRepository {

    @PersistenceContext
    private EntityManager manager;


    public Menu findMenu(int menuCode) {
        return manager.find(Menu.class,menuCode);
    }

    public Category findCategoryCode(int categoryCode) {

        return manager.find(Category.class,categoryCode);
    }

    public void saveMenu(Menu menu) {
        manager.persist(menu);
    }

    public void saveCategory(Category category) {

        manager.persist(category);
    }
}
