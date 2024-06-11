package com.ohgiraffers.jpql.section02.parameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ParameterBindingTest {

    /*필기.
    *  [Parameter Binding(가지고 온 변수를 query 문에 사용하기)]
    *  1. 이름 기준 파리미터(named parameters)
    *  - ':' 다음에 이름기준으로 파라미터를 작성한다.
    *  ex> :menuName
    *  2. 위치 기준 파라미터(positional parameters)
    *  - '?' 다음에 위치 값을 주고 위치의 값은 1 부터 시작한다.
    *  ex> ?1*/

    @Autowired
    private ParameterBindingRepository repository;

    @DisplayName("이름을 기준으로 파라미터 바인딩 메뉴 목록 조회 테스트")
    @Test
    void testParameterByName(){

        //given
        String menuName = "한우딸기국밥";

        //when

        List<Menu> menu = repository.findMenuByName(menuName);


        // then
        menu.forEach(System.out::println);

        Assertions.assertEquals(menuName,menu.get(0).getMenuName());

    }

    @DisplayName("위치를 기준으로 파라미터 바인딩 메뉴 목록 조회 테스트")
    @Test
    void testParameterByPosition(){

        //given
        String menuName = "한우딸기국밥";

        //when

        List<Menu> menu = repository.selectMenuBindingByPostion(menuName);


        // then
        menu.forEach(System.out::println);

        Assertions.assertEquals(menuName,menu.get(0).getMenuName());

    }

}
