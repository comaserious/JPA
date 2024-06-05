package com.ohgiraffers.section03.entity;

import jakarta.persistence.EntityManager;

public class EntityLifeCycle {

   private EntityManager manager;

   public EntityManager getMangerInstance(){
       return this.manager;
   }


    public Menu findMenuByMenuCode(int menuCode) {

       manager = EntityManagerGenerator.getInstance();

       return manager.find(Menu.class,menuCode);
    }
}
