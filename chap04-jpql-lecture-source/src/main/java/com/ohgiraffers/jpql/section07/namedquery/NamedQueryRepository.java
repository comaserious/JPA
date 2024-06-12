package com.ohgiraffers.jpql.section07.namedquery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class NamedQueryRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Menu> selectByDynamicQuery(String searchName, int searchCode) {

        StringBuilder jpql = new StringBuilder("select m from section07Menu m");

        List<Menu> resultList = manager.createQuery(jpql.toString(),Menu.class).getResultList();
        if(searchName !=null && !searchName.isEmpty() && searchCode>0){
            jpql.append(" where m.menuName like '%'||:searchName||'%' and m.categoryCode = :searchCode");
            resultList = manager.createQuery(jpql.toString(),Menu.class).setParameter("searchName",searchName).setParameter("searchCode",searchCode).getResultList();
        }else{
            if(searchName !=null && !searchName.isEmpty()){
                jpql.append(" where m.menuName like '%'||:searchName||'%'");
                resultList = manager.createQuery(jpql.toString(),Menu.class).setParameter("searchName",searchName).getResultList();
            }else if(searchCode>0){
                jpql.append(" where m.categoryCode = :searchCode");
                resultList = manager.createQuery(jpql.toString(),Menu.class).setParameter("searchCode",searchCode).getResultList();
            }
        }



        return resultList;
    }

    public List<Menu> selectByNamedQuery() {

        List<Menu> menuList = manager.createNamedQuery("section07Menu.selectMenuList",Menu.class).getResultList();

        return menuList;
    }

    public List<String> selectByNamedQuery2() {

        List<String> menuNameList= manager.createNamedQuery("selectMenuName",String.class).getResultList();
        return menuNameList;

    }
}
