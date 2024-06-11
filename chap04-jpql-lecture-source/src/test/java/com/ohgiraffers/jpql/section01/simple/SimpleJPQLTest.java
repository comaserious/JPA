package com.ohgiraffers.jpql.section01.simple;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SimpleJPQLTest {

    /*필기.
    *  JPQL 은 엔티티 객체를 중심으로 개발 할 수 있는
    *  객체 지향 쿼리이다 . SQL 보다 간결하며 특정 DBMS 에 '의존' 하지 않는다.
    *  find() 메소드를 통한 조회와 다르게, 항상 데이터베이스에 SQL 을 실행해서 결과를 조회한다.
    *  JPA 의 공식 지원 기능
    *  - JPQL            : 기본
    *  - Native SQL      : JPQL 대신 직접적으로 SQL 을 사용
    *  - Criteria Query  : JPQL 을 편하게 작성하게 도외주는 API
    * */

    /*필기.
    *  JPQL 의 기본 문법(CRUD)
    *  조회
    *  select 절
    *  from 절
    *  [where 절]
    *  [group by 절]
    *  [having 절]
    *  [order by 절]
    *  삽입
    *  EntityManger 가 제공하는 persist() 메소드를 사용하면 되므로 따로 없다
    *  수정
    *  update
    *  set
    *  [where]
    *  삭제
    *  delete
    *  from
    *  [where]
    *
    * */

    /*필기.
    *  JPQL 사용시 주의 사항
    *  - 엔티티와 속성은 대소문자를 구분한다 (userId 와 userid 는 다른 컬럼으로 인식한다)
    *  - 엔티티명은 클래스명이 아닌 엔티티명이다
    *  - JPQL 은 엔티티 별칭이 필수 . 별칭 없이 사용하면 에러가 발생 */

    /*필기.
    *  [JPQL 사용방법]
    *  1. 작성한 JPQL(문자열)을 EntityManger.createQuery() 메소드를 통해 쿼리 객체로 만들어 준다.
    *  2. 쿼리 객체는 Query , TypedQuery 두가지가 있다.
    *   2-1. TypedQuery : 반환할 타입을 명확하게 지정하는 방식일때 사용하며 쿼리 객체의 메소드 실행 결과로 지정한 타입이 반환된다
    *   2-2. Query : 반환할 타입을 명확하게 지정할 수 없을때 사용하며 쿼리 객체 메소드의 실행 결과로 Object 또는 Object[](Column 이 2개 이상일 때) 이 반환된다
    *  3. 쿼리 객체에서 제공하는 메소드 getSingleResult() 또는 getResultList() 를 호출해서 쿼리를 실행하고, DB를 조회한다
    *   3-1. getSingleResult() : 결과가 정확히 한 행인 경우 사용하며, 없거나 많으면 예외 발생
    *   3-2. getResultList() : 결과가 2개 행 이상인 경우 사용, 컬렉션을 반환한다. 없으면 빈 컬렉션 반환*/



    @Autowired
    private SimpleJPQLRepository repository;

    @DisplayName("TypedQuery 를 이용한 단일메뉴(단일행, 단일 컬럼) 조회테스트")
    @Test
    void testSingeMenuByTypedQuery(){
        //when

        String menuName = repository.selectSingleMenuByTypedQuery();

        //then

        Assertions.assertEquals("한우딸기국밥",menuName);

        Assertions.assertEquals(menuName,repository.findMenu(8).getMenuName());
    }
    @DisplayName("Query 를 이용한 단일 메뉴(단일행,단일 컬럼) 조회 테스트")
    @Test
    void testSingeMenuByQuery(){
        //when

        Object menuName = repository.selectSingleMenuByQuery();

        //then

        Assertions.assertEquals("한우딸기국밥",menuName);

        Assertions.assertTrue(menuName instanceof String);

        Assertions.assertEquals(menuName,repository.findMenu(8).getMenuName());
    }

    @DisplayName("TypedQuery 를 이용한 단일 행 조회테스트")
    @Test
    void testSingleRow(){

        Menu menu = repository.selectSingleRowByTypedQuery();

        Assertions.assertNotNull(menu);
        Assertions.assertEquals(8,menu.getMenuCode());
    }

    @DisplayName("TypedQuery를 이용한 다중행 조회 테스트")
    @Test
    void testMultiRowTest(){

        List<Menu> menuList = repository.selectMultiRowByTypedQuery();

        Assertions.assertNotNull(menuList);

//        for(Menu m : menuList){
//            System.out.println(m);
//        }

//        menuList.forEach(menu -> System.out.println(menu));

        menuList.forEach(System.out::println);
    }

    @DisplayName("Query 를 이용한 다중행 조회")
    @Test
    void testMultiRowByQuery(){
        List<Object> menuList = repository.selectMultiRowByQuery();

        Assertions.assertNotNull(menuList);

        menuList.forEach(System.out::println);
    }

    @DisplayName("Distinct 를 활요한 중복 제거 다중행 조회 테스트")
    @Test
    void testDistinct(){
        /*메뉴=> 카테고리 코드*/
        List<Integer> categoryCodeList = repository.selectUseDistinct();

        categoryCodeList.forEach(System.out::println);

        Assertions.assertNotNull(categoryCodeList);
    }


}
