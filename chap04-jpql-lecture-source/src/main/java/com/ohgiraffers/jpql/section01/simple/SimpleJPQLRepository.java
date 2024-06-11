package com.ohgiraffers.jpql.section01.simple;

import com.ohgiraffers.jpql.Chap04JpqlLectureSourceApplication;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimpleJPQLRepository {


    @PersistenceContext
    private EntityManager manager;

    public SimpleJPQLRepository(Chap04JpqlLectureSourceApplication chap04JpqlLectureSourceApplication) {
    }


    public String selectSingleMenuByTypedQuery() {

        String jpql = "select a.menuName from section01Menu a where a.menuCode = 8";

        TypedQuery<String> tQuery = manager.createQuery(jpql,String.class);

        String resultMenuName = tQuery.getSingleResult();
        return resultMenuName;
    }

    public Menu findMenu(int i) {

        return manager.find(Menu.class,i);
    }

    public Object selectSingleMenuByQuery() {

        String jpql = "select a.menuName from section01Menu a where a.menuCode = 8";
        Query query = manager.createQuery(jpql);

        Object resultMenu = query.getSingleResult();



        return resultMenu;
    }

    public Menu selectSingleRowByTypedQuery() {

        // * 을 사용하여 모든 컬럼을 조회는 불가능하지만 Entity 의 별칭을 이용하여 * 을 대신할 수 있다
        String jpql = "select m from section01Menu m where m.menuCode =8";

        TypedQuery<Menu> query = manager.createQuery(jpql,Menu.class);
        Menu resultMenu = query.getSingleResult();
        return resultMenu;
    }

    public List<Menu> selectMultiRowByTypedQuery() {

        String jpql = "select m from section01Menu m";

        TypedQuery<Menu> query = manager.createQuery(jpql,Menu.class);

        List<Menu> menuList = query.getResultList();

        return menuList;
    }

    public List<Object> selectMultiRowByQuery() {

        String jpql = "select m from section01Menu m ";

        Query query = manager.createQuery(jpql);

        List<Object> resultMenuList = query.getResultList();

        return resultMenuList;
    }

    public List<Integer> selectUseDistinct() {

        String jpql= "select distinct m.categoryCode from section01Menu m";

        TypedQuery<Integer> query = manager.createQuery(jpql,Integer.class);

        List<Integer> categoryCodes = query.getResultList();

        return  categoryCodes;
    }
}
