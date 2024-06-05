package com.ohgiraffers.section02.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EntityMangerCRUD {

    private EntityManager manager;


    public EntityManager getManagerInstance() {

        return this.manager;
    }

    public Menu findMenuByMenuCode(int menuCode) {

        this.manager= EntityManagerGenerator.getInstance();

        return manager.find(Menu.class, menuCode);
    }


    public Long saveAndReturnAllCount(Menu newMenu) {

        this.manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        manager.persist(newMenu);

        manager.flush();

        return getCount(manager);
    }

    private Long getCount(EntityManager manager) {

        return manager.createQuery("select count(*) from section02Menu",Long.class).getSingleResult();
    }


    public Menu modifyMenuName(int menuCode, String menuName) {

        Menu foundMenu = findMenuByMenuCode(menuCode);

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        foundMenu.setMenuName(menuName);

        manager.flush();

        return foundMenu;
    }

    public Long removeAndReturnAllCount(int menuCode) {

        manager = EntityManagerGenerator.getInstance();

        Menu foundMenu = findMenuByMenuCode(menuCode);
        EntityTransaction transaction= manager.getTransaction();
        transaction.begin();

        manager.remove(foundMenu);
        manager.flush();

        return getCount(manager);
    }


}
