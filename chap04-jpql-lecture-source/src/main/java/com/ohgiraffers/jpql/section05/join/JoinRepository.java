package com.ohgiraffers.jpql.section05.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JoinRepository {

    @PersistenceContext
    private EntityManager manager;


    public List<Menu> selectInnerJoin() {

        String jpql = "select m from section05Menu m join m.category c";

        List<Menu> menuList = manager.createQuery(jpql, Menu.class).getResultList();
        return menuList;
    }

    public List<Object[]> selectOuterJoin() {

        String jpql = "select m.menuName, c.categoryName from section05Menu m right join m.category c order by m.category.categoryCode";

        List<Object[]> menuList = manager.createQuery(jpql).getResultList();

        return menuList;
    }

    public List<Object[]> selectCollectionJoin() {

        String jpql = "select c.menuName, m.categoryName from section05Category m left join m.menuList c ";

        List<Object[]> categoryList = manager.createQuery(jpql).getResultList();

        return categoryList;
    }

    // 엔티티와 관련된 연관 엔티티를 한번의 쿼리로 함께 조회하는 방법
    public List<Menu> selectFetchInnerJoin() {

        String jpql = "select m from section05Menu m join fetch m.category c";
        List<Menu> menuList= manager.createQuery(jpql, Menu.class).getResultList();

        return menuList;
    }

    public List<Menu> rightjoinTest() {

        String jpql = "select m from section05Menu m right join m.category c";

        List<Menu> menuList = manager.createQuery(jpql, Menu.class).getResultList();

        return  menuList;
    }
}
