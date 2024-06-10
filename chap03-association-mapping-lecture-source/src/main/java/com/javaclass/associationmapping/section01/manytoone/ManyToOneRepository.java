package com.javaclass.associationmapping.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ManyToOneRepository {

    @PersistenceContext
    private EntityManager manager;


    public Menu findMenu(int menuCode) {

        return manager.find(Menu.class,menuCode);
    }

    /*필기.
    *  jpql(java Persistence Query Language)
    *  - 객체지향 쿼리 언어
    *  - 객체 지향 모델에 맞게 작성된 쿼리를 통해 엔티티를 대상으로 검색하고,
    *    검색결과를 토대로 객체를 조직할 수 있다.
    *  - join 문법이 sql과는 다소 차이가 있지만, 직접 쿼리르 작성 할 수있는
    *    문법을 제공한다.
    *  - 주의점 from 절에 기술할 테이블명에는 반드신 entity 명을 작성해야한다
    *  - 콜론(:): ex> :menuCode - 쿼리문에서 변수의 값을 바인딩하기 위한 jqpl 문법*/

    public String findCategoryName(int menuCode) {

        String Jpql = "select c.categoryName from menu_and_category m join m.category c where m.menuCode = :menuCode";

        return manager.createQuery(Jpql,String.class)
                .setParameter("menuCode",menuCode)
                // 첫번째 인자는 jpql 상에 바인딩한 이름 , 두번째 인자는 바인딩한 값이 무엇인지 알려준다
                .getSingleResult();
    }

    public void save(Menu menu) {

        manager.persist(menu);
    }
}
