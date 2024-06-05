package com.ohgiraffers.section02.crud;

import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class EntityManagerCRUDTests {

    private EntityMangerCRUD crud;

    @BeforeEach
    void initManager(){
        this.crud = new EntityMangerCRUD();
    }

    @AfterEach
    void rollback(){
        EntityTransaction transaction = crud.getManagerInstance().getTransaction();

        transaction.rollback();
    }


    @DisplayName("메뉴 코드로 메뉴 조회 테스트")
    /*필기.
    *  테스트 시 여러값들을 이용해서 검증을 진행해야 하는 경우에 경우의 수 만큼 테스트 메소드를 작성해야한다
    *  @ParameterizedTest 어노테이션을 붙이게 되면 테스트 메소드에 매개변수를 선언할 수 있다
    *  일반적인 테스트 메소드는 매개변수 사용 불가
    *  파라미터로 전달할 값의 목록만큼 반복적으로 테스트 메소드를 실행하며 준비된 값 목록을 전달하여
    *  테스트를 실행 할 수 있다. ==> given 을 대체 할 수 있다*/
    @ParameterizedTest
    /*필기. 여러개의 테스트 전용 파라미터 전달. 쉼표(,) 로 값을 구분 할 수 있다
    *  "" 로 몇번을 할건지 구분, "" 안에 쉼표로 몇개의 매개변수를 한번에 넘길건지 확인할 수 있다
    *  Csv : comma seperate values*/
    @CsvSource({"1,1","2,2","3,3"})
    void testFindMethodByMenuCode(int menuCode, int expected){

        //when

        Menu foundMenu = crud.findMenuByMenuCode(menuCode);



        Assertions.assertEquals(foundMenu.getMenuCode(),expected);
    }

    private static Stream<Arguments> newMenu(){
        return Stream.of(
                Arguments.of(
                        "신메뉴",
                        20000,
                        4,
                        "Y"
                )
        );
    }

    @DisplayName("새로운 메뉴 추가 테스트")
    @ParameterizedTest
    @MethodSource("newMenu")
    void testRegist(String menuName,int menuPrice,int categoryCode, String orderableStatus){

        // when

        Menu newMenu = new Menu(menuName,menuPrice,categoryCode,orderableStatus);

        Long count= crud.saveAndReturnAllCount(newMenu);

        Assertions.assertEquals(22,count);
    }

    @DisplayName("메뉴 이름 수정 테스트")
    @ParameterizedTest
    @CsvSource("1,변경된 메뉴")
    void testModifyMenuName(int menuCode,String menuName){
        // when

        Menu modifyMenu = crud.modifyMenuName(menuCode,menuName);


        // then

        Assertions.assertEquals(menuName,modifyMenu.getMenuName());
    }


    @DisplayName("메뉴 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1})
    void testRemove(int menuCode){
        Long count = crud.removeAndReturnAllCount(menuCode);

        Assertions.assertEquals(20,count);
    }
}
