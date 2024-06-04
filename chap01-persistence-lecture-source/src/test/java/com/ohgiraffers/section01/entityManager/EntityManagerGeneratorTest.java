package com.ohgiraffers.section01.entityManager;

import com.ohgiraffers.section01.entitymanager.EntityManagerFactoryGenerator;
import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

public class EntityManagerGeneratorTest {

    //All => 최초 한번 테스트코드가 실행하기전 또는 모든 테스트가 끝난후에 동작
    @BeforeAll
    static void beforeAllTest() {
        System.out.println("=================BeforeAll======================");
    }

    @BeforeEach
    void beforeEachTest() {
        System.out.println("============beforeEach=================");
    }

    @AfterEach
    void afterEachTest() {
        System.out.println("=============afterEach===================");
    }

    @AfterAll
    static void afterAllTest() {
        System.out.println("=================AfterAll=============================");
    }

    /*수업목표. PersistenceContext 를 이해하기 위한 EntityManager 와 factory*/
    /*필기.
     *  EntityManagerFactory
     *  EntityManager 를 생성할 수 있는 기능을 제공하는 팩토리 클래스이다
     *  EntityManagerFactory 는 thread-safe 하기 때문에 여러 쓰레드가 동시에
     *  접근해도 안전하기 때문에 공유해서 재사용한다.(데이터의 변형을 동시에 여러명이 다루지 않기때문에 싱글톤이어도 문제가 발생하지 않는다)
     *  thread-safe 한 기능들은 매번 생성하기에는 비용(시간,메모리) 부담이 크기 때문에
     *  application 스코프와 동일하게 singleton 으로 생성해서 관리하는것이 효율적이다
     *  따라서 DB를 사용하는 어플리케이션 당 한개의 EntityManagerFactory 를 생성한다*/


    @Test
    @DisplayName("EntityManagerFactory 생성 확인")
    void testCreateFactory() {
        EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();

        System.out.println("엔티티 매니저 팩토리 hashcode : " + factory.hashCode());
        Assertions.assertNotNull(factory);
    }

    @Test
    @DisplayName("EntityManagerFactory singleton instance 확인")
    void testFactoryIsSingle() {

        // given

        EntityManagerFactory factory1 = EntityManagerFactoryGenerator.getInstance();

        //when

        EntityManagerFactory factory2 = EntityManagerFactoryGenerator.getInstance();

        //then

        System.out.println("factor1 해시코드1 : " + factory1.hashCode());
        System.out.println("factor2 해시코드2 : " + factory2.hashCode());
        Assertions.assertEquals(factory1, factory2);
    }

    /*필기.
     *  EntityManager
     *  EntityManager 는 엔티티를 저장하는 메모리상의 DB를 관라하는 인스턴스이다.
     *  엔티티를 저장하고, 수정,삭제, 조회하는 등의 엔티티와 관련된 모든일을 한다.
     *  EntityManager 는 팩토리와 달리 thread-safe 하지 않기 때문에 동시성 문제가 발생할 수 있다.
     *  따라서 web 의 경우 일반적으로 request-scope(하나의 transaction 이라는 소리이다) 와 일치 시킨다.*/

    @Test
    @DisplayName("EntityManger 생성 확인")
    void testCreateManager(){
        EntityManager manager = EntityManagerGenerator.getInstance();

        System.out.println("manager 해시코드 : "+manager.hashCode());

        Assertions.assertNotNull(manager);
    }

    @Test
    @DisplayName("EntityManager 스코프 확인")
    void testManagerLifeCycle(){
        //given
        EntityManager manager1 = EntityManagerGenerator.getInstance();
        //when
        EntityManager manager2 = EntityManagerGenerator.getInstance();
         //then

        System.out.println("manager1 해시코드 : "+ manager1);
        System.out.println("manager2 해시코드 : "+ manager2);

        Assertions.assertNotEquals(manager1,manager2);
    }
}



