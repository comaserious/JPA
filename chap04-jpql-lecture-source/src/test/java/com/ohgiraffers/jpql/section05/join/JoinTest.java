package com.ohgiraffers.jpql.section05.join;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class JoinTest {

    /*필기.
    *  1. 일반 조인 : 일반적인 SQL 조인 (inner join, outer(left,right) 조인, 컬렉션(1:N) 조인)
    *  2. 패치 조인 : JPQL 에서 성능 최적화를 위해 제공하는 기능
    *               연관된 엔티티나 컬렉션을 한번에 조회한다. 지연 로딩(Lazy)이 아닌 즉시 로딩(Eager)을 수행하며
    *               join fetch 명령어를 사용한다*/
    @Autowired
    private JoinRepository repository;

    @DisplayName("내부(inner) 조인을 이용한 조회테스트")
    @Test
    @Transactional
    void testInnerJoin(){
        List<Menu> menuList = repository.selectInnerJoin();

        Assertions.assertNotNull(menuList);

        menuList.forEach(System.out::println);
    }

    @DisplayName("외부 조인을 이용한 조회 테스트")
    @Test
    void testOuterJoin(){
        List<Object[]> menuList = repository.selectOuterJoin();

        Assertions.assertNotNull(menuList);

        menuList.forEach(
                row->{
                    for(Object column : row){
                        System.out.println(column+" ");
                    }
                    System.out.println();
                }
        );

        // right join 을 하게 되면 중심 entity 가 join 인 category 가 된다
        // 하지만 실제로 사용하는 entity 는 menu 이고 따라서 menu 의 @Id 인 menuCode 가 null 인 값도 존재 하게 되고
        // 따라서 Menu 자체가 생성 되지 않는다 즉, right join 의 의도인 menu table 의 값에 관계없이 category를 보려는 의도가
        // 퇴색된다. 따라서 Menu 로 받는 것이 아닌 Object[] 를 통해서 right join 의 의도를 살린것이다
    }

    @DisplayName("right join 진짜 문제인가")
    @Test
    void rightJoinTest(){
        List<Menu> menuList = repository.rightjoinTest();

        menuList.forEach(System.out::println);


    }

    @DisplayName("컬렉션 조인을 이용한 조회 테스트")
    @Test
    void testCollectionJoin(){
        List<Object[]> categoryList = repository.selectCollectionJoin();

        Assertions.assertNotNull(categoryList);

        categoryList.forEach(row->{
            for(Object column : row){
                System.out.println(column+" ");

            }
            System.out.println();
        });
    }

    @DisplayName("패치 조인을 이용한 조회 테스트")
    @Test
    void testFetchJoin(){

        List<Menu> menuList = repository.selectFetchInnerJoin();

        Assertions.assertNotNull(menuList);

        menuList.forEach(System.out::println);
    }

    // inner join 과 달리 join fetch 를 하게 되면 hibernate 를 확인 해보면 알수 있듯이
    // category entity를 조회하지 않고 바로 join 을 통해서 값을 가지고 온다
    // 상황에 따라서는 이게 더 효율적인 데이터 탐색이 될 수 있다

}
