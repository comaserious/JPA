package com.ohgiraffers.nativequery.section01.simple;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class NativeQueryTest {

    /*필기.
    *  Native Query 란?
    *  MySQL 에서 작성했던 쿼리문을 그대로 사용하는 것을 의미한다.
    *  이를 사용하게 된다면 ORM 의 기능을 이용하면서, SQL 쿼리를 사용할 수 있어서
    *  더욱 강력하게 DB 에 접근할 수 있게 된다.
    *  따라서 복잡한 쿼리를 작성할 때나, 특정한 DB 에서만 사용이 가능한
    *  기능을 사용해야 할 때 등에 Native Query 를 사용한다.*/

    /*필기.
    *  1. 결과 타입 정의가 가능할 때
    *  2. 결과 타입을 정의할 수 없을 때
    *  3. 결과 매핑 사용*/

    @PersistenceContext
    private EntityManager manager;

    /*1. 결과 타입을 정의한 경우*/

    /*필기.
    *  모든 컬럼값을 매핑하는 경우에만 타입을 특정할 수 있다(이경우 앞서 봤듯이 영속성컨텍스트에서 관리한다)
    *  일부 컬럼만 조회를 하고 싶다 ? => Object[], scalar 값을 별도로 담을 클래스를 정의해서 사용해야한다.*/
    @DisplayName("결과 타입을 정의한 Native Query 사용해보기")
    @Test
    @Transactional
    void testNativeQueryByResultType(){

        //given

        int menuCode =15;

        //when
        String query = "select menu_code,menu_name, menu_price, category_code,orderable_status from tbl_menu where menu_code = ?";

        Query nativeQuery = manager.createNativeQuery(query,Menu.class).setParameter(1,menuCode);

        Menu foundMenu = (Menu) nativeQuery.getSingleResult();


        // then

        Assertions.assertNotNull(foundMenu);  // foundMenu 가 존재하는가
        Assertions.assertTrue(manager.contains(foundMenu)); // 영속성 컨텍스트에 보관을 하고 있는가( @Transactional 을 해야지 Persistence Context 에 담기게 된다)

        System.out.println(foundMenu);
    }

    @DisplayName("결과타입을 지정할 수 없는 Native Query 테스트")
    @Test
    void testNativeQueryNoResult(){
        String query = "select menu_name , menu_price from tbl_menu";

        List<Object[]> menuList = manager.createNativeQuery(query).getResultList();

        Assertions.assertNotNull(menuList);


        menuList.forEach(row->{
            for (Object column : row){
                System.out.print(column + " ");
            }
            System.out.println();
        });
    }

    /*3. 결과매핑을 사용하는 경우 - 자동, 수동*/
    @DisplayName("자동결과 매핑을 사용한 native query 조회테스트")
    @Test
    @Transactional
    void testAutoMapping(){

        /* 상황 : category 를 기준으로 카테고리 별 메뉴의 개수를 조회하고 싶다.*/
        // COALESCE 함수 : 여러개의 인수(컬럼) 을 전달할 수 있으며 LEFT JOIN 으로 인해 NULL 이 발생할 수 있기 때문에
        // (a,b) : a 가 null 일 경우 b로 대체해서 값을 집어 넣겠다.
        String query = "select a.category_code,a.category_name,a.ref_category_code, coalesce(v.menu_count,0) menu_count from tbl_category a " +
                "left join (select count(menu_code) as menu_count, category_code from tbl_menu group by category_code) v on a.category_code = v.category_code " +
                "order by 1";
        // order by 1 -> 첫번째 컬럼을 기준으로 오름차순 정렬

        //
        /* 필기.
        *   자동 매핑을 사용하는 이유 ? => Object 로 값을 받는다. 하지만 우리가 값을 조작하거나 다시 데이터와 맞출 때
        *   Object 타입에서 우리가 원하는 대로 형변환을 해줘야한다.
        *   이는 Entity 필드도 고려, DB 의 자료형도 고려해야하는 작업이기 때문에 번거롭다.*/
        Query nativeQuery = manager.createNativeQuery(query,"categoryAutoMapping");

        List<Object[]> categoryList = nativeQuery.getResultList();

        Assertions.assertNotNull(categoryList);

        Assertions.assertTrue(manager.contains(categoryList.get(0)[0]));

        categoryList.forEach(row->{
            for(Object c: row){
                System.out.print(c+ " ");
            }
            System.out.println();
        });

        Assertions.assertTrue(categoryList.get(3)[1] instanceof Long);
    }

    /*2. 매핑 설정을 수동으로 하는 경우 (@Column 어노테이션 생략 가능)*/
    @DisplayName("자동결과 매핑을 사용한 native query 조회테스트")
    @Test
    @Transactional
    void testManualMapping(){

        /* 상황 : category 를 기준으로 카테고리 별 메뉴의 개수를 조회하고 싶다.*/
        // COALESCE 함수 : 여러개의 인수(컬럼) 을 전달할 수 있으며 LEFT JOIN 으로 인해 NULL 이 발생할 수 있기 때문에
        // (a,b) : a 가 null 일 경우 b로 대체해서 값을 집어 넣겠다.
        String query = "select a.category_code,a.category_name,a.ref_category_code, coalesce(v.menu_count,0) menu_count from tbl_category a " +
                "left join (select count(menu_code) as menu_count, category_code from tbl_menu group by category_code) v on a.category_code = v.category_code " +
                "order by 1";
        // order by 1 -> 첫번째 컬럼을 기준으로 오름차순 정렬

        //
        /* 필기.
         *   자동 매핑을 사용하는 이유 ? => Object 로 값을 받는다. 하지만 우리가 값을 조작하거나 다시 데이터와 맞출 때
         *   Object 타입에서 우리가 원하는 대로 형변환을 해줘야한다.
         *   이는 Entity 필드도 고려, DB 의 자료형도 고려해야하는 작업이기 때문에 번거롭다.*/
        Query nativeQuery = manager.createNativeQuery(query,"categoryManualMapping");

        List<Object[]> categoryList = nativeQuery.getResultList();

        Assertions.assertNotNull(categoryList);

        Assertions.assertTrue(manager.contains(categoryList.get(0)[0]));

        categoryList.forEach(row->{
            for(Object c: row){
                System.out.print(c+ " ");
            }
            System.out.println();
        });

        Assertions.assertTrue(categoryList.get(3)[1] instanceof Long);
    }

    @DisplayName("test nativeNamedQuery ")
    @Test
    @Transactional
    void namedNativeTest(){
        List<Object[]> resultList = manager.createNamedQuery("namedNativeQuery").getResultList();

        resultList.forEach(row->{
            for(Object c : row){
                System.out.print(c+"/");
            }
            System.out.println();
        });
    }
}
