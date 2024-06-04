package com.ohgiraffers.section01.entityManager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class EntityManagerGeneratorTest {

    //All => 최초 한번 테스트코드가 실행하기전 또는 모든 테스트가 끝난후에 동작
    @BeforeAll
    static void beforeAllTest(){
        System.out.println("=================BeforeAll======================");
    }

    @BeforeEach
    void beforeEachTest(){
        System.out.println("============beforeEach=================");
    }
    @AfterEach
    void afterEachTest(){
        System.out.println("=============afterEach===================");
    }
    @AfterAll
    static void afterAllTest(){
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



}