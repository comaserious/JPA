package com.ohgiraffers.jpql.section06.projection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProjectionTest {


    /*필기.
    *  [프로젝션(Projection)]
    *  select 절 조회할 대상 지정하는 것을 projection 이라고 한다.
    *  select 절의 조회 대상을 projection 대상이라고 한다.
    *  */

    /*필기.
    *  projection 대상의 4가지 방식
    *  1. Entity projection (ex> select m from section01Menu m)
    *  - 원하는 객체를 바로 조회 할 수 있다.
    *  - 조회된 Entity 는 영속성 켄텍스트가 관리한다.
    *  2. Embedded 타입 projection
    *  - @Embeddable => 엔티티와 거의 비슷하게 사용되며 조회의 시작점이 될 수 없다(from 절에 사용불가)
    *  - @Embedded 타입은 영속성 컨텍스트에서 관리하지 않는다
    *  3. 스칼라 타입 프로젝션
    *  - 숫자, 문자, 날짜 등등의 기본 데이터 타입을 의미한다.
    *  - 스칼라 타입도 영속성 컨텍스트에서 관리되지 않는다.
    *  4. new 명령어를 활용한 프로젝션
    *  - 다양한 종류의 단순 값들을 DTO 로 바로 조회하는 방식으로 'new 패키지명.DTO명' 을 쓰게 되면
    *    해당하는 DTO 로 바로 값을 반환 받을수 있다.
    *    이 역시도 클래스 객체는 엔티티가 아니기 때문에 영속성 컨텍스트에서 관리 하지 않는다.
    * */

    @Autowired
    private ProjectionService service;

    /*1. Entity Projection*/
    @DisplayName("단일 엔티티 프로젝션 테스트")
    @Test
    void testSingleEntityProjection(){
        List<Menu> menuList = service.singleEntityTest();

        Assertions.assertNotNull(menuList);
    }

    @DisplayName("양방향 연관관계 entity projection test")
    @Test
    void testBiDirectionEntityTest(){
        int menuCode = 7;

        BidirectionCategory categoryOfMenu = service.biDirectionProjection(menuCode);

        Assertions.assertNotNull(categoryOfMenu);
    }

    @DisplayName("임베디드 타입 프로젝션 테스트")
    @Test
    void testEmbeddedProjection(){

        List<MenuInfo> menuInfos = service.findMenuNameAndPrice();

        menuInfos.forEach(System.out::println);
        Assertions.assertNotNull(menuInfos);
    }

    @DisplayName("TypedQuery 를 이용한 스칼라 타입 프로젝션 테스트")
    @Test
    void testScalarTypedQuery(){

        List<String> categoryNameList = service.scalarTypedQuery();

        categoryNameList.forEach(System.out::println);
        Assertions.assertNotNull(categoryNameList);
    }
    @DisplayName("Query 를 이용한 스칼라 타입 프로젝트 테스트")
    @Test
    void testScalarQuery(){
        List<Object[]> menuNameAndCodeList= service.scalarQuery();

        menuNameAndCodeList.forEach(row->{
            for(Object column : row){
                System.out.print(column+ " ");
            }
            System.out.println();
        });

        Assertions.assertNotNull(menuNameAndCodeList);
    }
    @DisplayName("new 명령어를 이용한 프로젝션 테스트")
    @Test
    void testNewProjection(){

        List<CategoryInfoDTO> categoryInfoDTOList = service.newProjection();
        Assertions.assertNotNull(categoryInfoDTOList);

        categoryInfoDTOList.forEach(System.out::println);

    }

}
