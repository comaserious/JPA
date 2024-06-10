package com.javaclass.associationmapping.section02.onetomany;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class OneToManyTest {
    @Autowired
    private OneToManyService oneToManyService;

    @DisplayName("1:N 연관관계 객체 그래프 탐색을 이용한 조회 테스트")
    @Test
    void oneToManyFindTest(){
        //given
        int categoryCode = 10;

        //when
        Category category = oneToManyService.findCategory(categoryCode);

        //then


        Assertions.assertNotNull(category);
    }



    /*필기.
    *  @OneToMany 일 때 조회임에도 불구하고 @Transactional annotation 을 사용하는 이유
    *  - 1 개의 엔티티가 여러개의 엔티티를 로드해야 하는 상황(Category => menuList)
    *    일때는 여러개의 엔티티를 로드해야하기 때문에 성능상의 이슈가 발생할 수 있다.
    *    그래서 Category 엔티티를 조회할때 Menu 에 대한 엔티티를 바로 로드하는 것이 아닌
    *    일단 가지고 있다가 진짜 필요할 때 로드를 하게 된다 (lazy)
    *  - @OneToMany(fetch = FetchType.LAZY) 가 default 로 설정이 되어 있다
    *    @ManyToMany 또한 지연 로딩이 디폴트값으로 설정되어 있다
    *  - @ManyToOne(fetch = Fetch.EAGER) 가 default 값으로 설정 되어  있다
    *    @OneToOne 또한 이른 로딩이 디폴트값으로 설정되어 있다*/

    private static Stream<Arguments> getInfo(){
        return Stream.of(
                Arguments.of(321,"돈가스스파게티",30000,321,"퓨전분식","Y")

        );
    }
    @DisplayName("1:N 연관관계 객체 삽입 테스트")
    @ParameterizedTest
    @MethodSource("getInfo")
    void oneToManyInsertTest(int menuCode, String menuName, int menuPrice, int categoryCode , String categoryName, String orderableStatus){

        CategoryDTO categoryDTO = new CategoryDTO(categoryCode,categoryName,null,null);
        List<MenuDTO> menuDTOS = new ArrayList<>();

        MenuDTO newMenu = new MenuDTO(menuCode,menuName,menuPrice,categoryCode,orderableStatus);
        menuDTOS.add(newMenu);
        categoryDTO.setMenuDTOS(menuDTOS);

        Assertions.assertDoesNotThrow(
                ()->oneToManyService.registMenu(categoryDTO)
        );

    }
}
