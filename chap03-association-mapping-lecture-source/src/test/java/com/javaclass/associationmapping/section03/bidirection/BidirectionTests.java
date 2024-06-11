package com.javaclass.associationmapping.section03.bidirection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@SpringBootTest
public class BidirectionTests {

    @Autowired
    private BidirectionService bidirectionService;

    /*필기.
    *  DB 의 테이블은 외래키 하나로 양방향 조회가 가능하지만
    *  객체는 서로 다른 두 단방향 참조를 합쳐서 양방향이라고 한다.
    *  따러서 두 개의 연관관계 중 연관관계의 주인을 정하고, 주인이 아닌
    *  연관관계를 추가하는 방식으로 작성한다.
    *  - 양방향 연관관계는 항상 설정하는 것이 아닌, 반대방향으로도 접근하여
    *    객체 그래프(참조) 할 일이 많을 때만 사용한다(즉 설계한다)
    *  - 양방향 연관관계 매핑 시 연관관계의 주인은 비즈니스로직 상 중요하다고 해서 주인이 되는것이 아니다 (케바케가 아니다)
    *    그러면 누가 주인이 될 것인가?
    *    외래키(fk) 를 관리하는 엔티티가 주인이 되어야 한다
    *    즉, 연관관계의 주인은 외래키를 가지고 있는 엔티티가 되어야 한다.
    *    (우리가 쓰는 테이블로 따지면 Menu 가 주인이다.)*/


    @DisplayName("양방향 영관관계 매핑 조회 테스트")
    @Test
    @Transactional
    void ownerFindTest1(){
        // given
        int menuCode = 10;

        //when
        // 처음주터 조회시 조인한 결과를 가져온다
        Menu foundmenu = bidirectionService.findMenu(menuCode);
        System.out.println("foundmenu = " + foundmenu);


        //then
        Assertions.assertEquals(menuCode,foundmenu.getMenuCode());


    }

    @DisplayName("양방향 연관관계 매핑 조회 테스트 2 (연관관계의 주인이 아닌 객체)")
    @Test
    @Transactional
    void notOwnerFindTest2(){
        //given

        int categoryCode = 10;

        //when
        /*해당 엔티티 조회하고 필요시 연관관계 엔티티를 조회하는 쿼리를 실행한다*/
        Category foundCategory = bidirectionService.findCategory(categoryCode);

        System.out.println("[category menuList]"+ foundCategory.getMenuList());
        // @Transactional 범위가 service 단 까지 였는데 이를 test 의 영역까지 확장을 하게 되면
        // OneToMany 의 System.out 또한 정상작동하는 것을 확인할 수있다
        // 문제는 @Transactional 의 범위 였다!!!!!!!!!
        //then
        Assertions.assertEquals(categoryCode,foundCategory.getCategoryCode());


    }
    private static Stream<Arguments> getMenuInfo(){
        return Stream.of(
                Arguments.of(
                        111,"스테이크",9800,"Y"
                )
        );
    }

    @DisplayName("양방향 연관관계 주인 객체를 이용한 삽입 테스트")
    @ParameterizedTest
    @MethodSource("getMenuInfo")
    void ownerInsertTest1(int menuCode, String menuName,int menuPrice,String orderableStatus){

        Category category = bidirectionService.findCategory(4);
        Menu menu = new Menu(menuCode,menuName,menuPrice,category,orderableStatus);

        Assertions.assertDoesNotThrow(
                ()->bidirectionService.registMenu(menu)
        );
    }

    private static Stream<Arguments> getCategoryInfo(){
        return Stream.of(
                Arguments.of(
                        111,"양방향 카테고리",null
                )
        );
    }
    @DisplayName("양방향 연관관계 주인이 아닌 객체를 이용한 삽입테스트")
    @ParameterizedTest
    @MethodSource("getCategoryInfo")
    void notOwnerInsertTest2(int categoryCode, String categoryName, Integer refCategoryCode){

        Category category = new Category(categoryCode,categoryName,refCategoryCode);


        bidirectionService.insertCategory(category);

        Category foundCategory = bidirectionService.findCategory(categoryCode);

        // 등록후 조회 성공여부
        System.out.println(foundCategory);
        Assertions.assertNotNull(foundCategory);

    }
}
