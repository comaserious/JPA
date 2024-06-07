package com.ohgiraffers.section03.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class EntityLifeCycleTests {

    private EntityLifeCycle lifeCycle;


    @BeforeEach
    void setUp(){
        this.lifeCycle = new EntityLifeCycle();
    }

    // persistence context 에 동록하지 않은 상태
    @DisplayName("비영속 테스트")
    @ParameterizedTest
    @ValueSource(ints={1,2})
    void testTransient(int menuCode){

        Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);

        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
                );


        Assertions.assertNotEquals(foundMenu,newMenu);

        // manager 안에는 newMenu가 들어가 있지 않다
        Assertions.assertFalse(lifeCycle.getMangerInstance().contains(newMenu));

    }

    @DisplayName("다른 엔티티 메니저가 관리하는 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints={1,2,3})
    void testOtherManager(int menuCode){
        Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
        Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);


        // 관리하는 매니저가 다르기 때문에 내용물이 같더라도 다르게 인식한다
        Assertions.assertNotEquals(menu1,menu2);
    }

    @DisplayName("같은 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints={1,2,3})
    void testSameManager(int menuCode){
        EntityManager manager = EntityManagerGenerator.getInstance();

        Menu menu1 = manager.find(Menu.class,menuCode);
        Menu menu2 = manager.find(Menu.class,menuCode);

        Assertions.assertEquals(menu1,menu2);
    }

    @DisplayName("준영속화 detach 테스트")
    @ParameterizedTest
    @CsvSource({"11,1000","12,1000"})
    void testDetachEntity(int menuCode, int menuPrice){

        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class,menuCode);

        transaction.begin();

        /*detach()
        * 특정 entity 만 준영속 상태(영속성 컨텍스트가 관리하던 엔티티객체를 관리하지 않는 상태)로 만든다*/
        manager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);
        manager.flush();




        Assertions.assertNotEquals(menuPrice,manager.find(Menu.class,menuCode).getMenuPrice());

        // 전혀 업데이트 되지 않았음을 확인할 수있다
        // 그이유는 foundMenu 를 준영속상태로 만들었기 때문에 아무리 setter로 값을 바꾼후 flush를 하여도
        // DB 에 update 되지 않은 것이다
        transaction.rollback();


    }

    @DisplayName("준영속화 detach 후 다시 영속화 테스트")
    @ParameterizedTest(name="[{index}] 준영속화된 {0} 번 메뉴를 {1}원으로 변경하고 다시 영속화되는지 확인")
    @CsvSource({"11,1000","12,1000"})
    void testDetachAndPersist(int menuCode, int menuPrice){

        EntityManager manager = EntityManagerGenerator.getInstance();
        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class,menuCode);

        transaction.begin();
        manager.detach(foundMenu);  // 준영속상태

        foundMenu.setMenuPrice(menuPrice);


        /*필기.
        *  파라미터로 넘어온 준영속 엔티티 객체의 식별자 값으로 1 차 캐시에서 엔티티 객체를
        *  조회한다
        *  만약 1차 캐시에 엔티티가 없으면 DB에서 엔티티를 조회하고 1차 캐시에
        *  조회한 영속 엔티티 객체에 준 영속 상태의 엔티티 객체의 값을 병합한 뒤
        *  영속 엔티티 객체를 반환한다.
        *  혹은 조회할 수 없는 테이터의 경우 새로 생성해서 병합한다.(save or merge)*/
        manager.merge(foundMenu);
        //manager.persist(foundMenu);

        manager.flush();

        Assertions.assertEquals(menuPrice,manager.find(Menu.class,menuCode).getMenuPrice());

        transaction.rollback();
    }

    @DisplayName("detach 준영속 후 merge 한 데이터 save 테스트")
    @Test
    void testDetachAndMergeSave(){
        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();
        Menu foundMenu = manager.find(Menu.class,20);

        manager.detach(foundMenu);

        transaction.begin();

//        foundMenu.setMenuCode(999);
        foundMenu.setMenuName("알아리알탕");

        manager.merge(foundMenu);

        manager.flush();



        Assertions.assertNotNull(manager.find(Menu.class,20));

        Assertions.assertEquals("알아리알탕",manager.find(Menu.class,20).getMenuName());

        transaction.rollback();
    }

    @DisplayName("준영속성화 clear 테스트")
    @ParameterizedTest
    @ValueSource(ints={1,2,3})
    void testClearPersistenceContext(int menuCode){

        EntityManager manger = EntityManagerGenerator.getInstance();
        EntityTransaction transaction = manger.getTransaction();

        Menu foundMenu = manger.find(Menu.class,menuCode);

        // clear() : 영속성 컨텍스트를 초기화 => 영속성 컨텍스트 내의 모든 엔티티를 준영속화 시킨다
        manger.clear();

        Menu expectedMenu = manger.find(Menu.class,menuCode);

        Assertions.assertNotEquals(foundMenu,expectedMenu);


    }

    @DisplayName("준영속화 close 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    void testClose(int menuCode){

        EntityManager manager = EntityManagerGenerator.getInstance();

        Menu foundMenu = manager.find(Menu.class,menuCode);

        // close() : 영속성 컨텍스트를 종료한다 => 영속성 컨텍스트 내 모든 객체를 준영속화 시킨다
        // close() 이후 manager 관련기능 모두 사용 할 수 없게 된다
        manager.close();

//        Menu expectedMenu = manager.find(Menu.class,menuCode);

//        Assertions.assertNotEquals(foundMenu,expectedMenu);


        Assertions.assertThrows(IllegalStateException.class,() -> manager.find(Menu.class,menuCode));

    }

    @DisplayName("영속성 엔티티 삭제 remove 테스트")
    @ParameterizedTest
    @ValueSource(ints = {1})
    void testRemoveEntity(int menuCode){
        EntityManager manager = EntityManagerGenerator.getInstance();
        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class,menuCode);

        transaction.begin();

        /*필기.
        *  remove() : 엔티티를 영속성 컨텍스트 및 DB에서 삭제한다
        *  단, 트랜젝션을 제어하지 않으면 DB 에 영구반영되지 않는다.
        *  transaction 을 커밋 or flush  하는 순간 영속성 컨텍스트에서
        *  관리하는 엔티티 객체가 DB 에 반영된다*/

        manager.remove(foundMenu);
        manager.flush();

//        Assertions.assertFalse(manager.contains(foundMenu));
        Menu refoundMenu = manager.find(Menu.class,menuCode);

        Assertions.assertEquals(null,refoundMenu);



    }


}
