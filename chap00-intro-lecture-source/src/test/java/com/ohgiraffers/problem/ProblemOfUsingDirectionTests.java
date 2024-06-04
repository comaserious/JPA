package com.ohgiraffers.problem;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemOfUsingDirectionTests {

    /*수업목표. 테스트 코드 기반으로 JPA 를 사용하지 않았을때 문제를 알아보자*/

    /*필기.
    *  test 클래스란?
    *  @Test 라는 annotation 이 1개 이상 가지고 있는 클래스를 의마한다
    *  테스트 메소드는 반환값을 기대하지 않으며(return 이 없다),void 형으로 작성해야 한다
    *  또한 접근제한자는 사용하지 않아도 되지만(default) , private 은 안된다 */

    private Connection con;

    /* 우리가 작성한 테스트 메소드가 실행하기 이전에 1번씩 동작을 할 수 있는 annotation*/
    @BeforeEach
    void setConnection() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/menudb";
        String user = "ohgiraffers";
        String pass= "ohgiraffers";

        Class.forName(driver);

        con = DriverManager.getConnection(url,user,pass);
        con.setAutoCommit(false);

        System.out.println("beforeEach 사용");
    }

    @AfterEach
    void closeConnection() throws SQLException {

        System.out.println("afterEach 사용");
        con.rollback();
        con.close();
    }

    /*필기.
    *  JDBC 를 이용해 직접 SQL 을 다룰 때 발생할 수 있는 문제점 확인
    *  1. 데이터의 변환, sql 작성, JDBC 코드 중복 작성 => 개발시간의 증가 => 유지보수성 악화
    *  2. SQl 의존적인 개발
    *  3. 패러다임 불일치
    *  4. 동일성 보장 문제
    *  */

    /*1. 데이터 변환, sql 작성, JDBC 코드 중복 작성*/

    @Test
    @DisplayName("직접 sql 작성해서 메뉴를 조회할때 발생하는 문제 확인")   // 테스트의 이름은 기본적으로 메소드명이 나오게 된다. 더 잘 구별해보기 위해 displayName 을 사용
    void testDirectionSQL() throws SQLException {
        String query = "select * from tbl_menu";
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<Menu> menus = new ArrayList<>();
        while(rset.next()){
            Menu menu = new Menu();

            menu.setMenuCode(rset.getInt("menu_code"));
            menu.setMenuName(rset.getString("menu_name"));
            menu.setMenuPrice(rset.getInt("menu_price"));
            menu.setCategoryCode(rset.getInt("category_code"));
            menu.setOrderableStatus(rset.getString("orderable_status"));

            menus.add(menu);
        }

        /*필기.
        *  Assertion 클래스는 우리가 작성한 테스트 코드의 검증을 할 수 있는 기능(메소드)를
        *  제공 해주는 클래스 이다*/
        Assertions.assertNotNull(menus);

        menus.forEach(menu-> System.out.println("menu = " + menu));

        rset.close();
        stmt.close();
    }

    @Test
    @DisplayName("직접 SQL 을 작성해서 신규메뉴 추가시 발생하는 문제 확인")
    void testInsertSQL() throws SQLException {

        // given
        Menu newMenu = new Menu();
        newMenu.setMenuName("과도한 홍시");
        newMenu.setMenuPrice(15000);
        newMenu.setCategoryCode(1);
        newMenu.setOrderableStatus("Y");

        String query = "insert into tbl_menu(menu_name,menu_price,category_code,orderable_status) values(?,?,?,?)";


        // when

        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1,newMenu.getMenuName());
        pstmt.setInt(2,newMenu.getMenuPrice());
        pstmt.setInt(3,newMenu.getCategoryCode());
        pstmt.setString(4,newMenu.getOrderableStatus());

        int result = pstmt.executeUpdate();

        // then

        /*equals : 메소드는 실제 값과 기대한 값의 일치 여부를 동일성으로 판단한다*/
        Assertions.assertEquals(1,result);

        pstmt.close();
    }

    /*2. SQL 에 의존적인 개발*/

    /*2-1. 조회 항목, 요구사항 변화에 따른 의존성 문제*/

    /*필기.
    *  고객 즉 클라이언트 측에 요구 사항 변화로 인한
    *  DB 수정, SQL문 수정, Application 수정등의 문제 발생*/

    /*2-2. 연관된 객체에 대한 문제*/

    @Test
    @DisplayName("연관된 객체 문제 확인")
    void testAssociatedObject() throws SQLException {

        //given

        String query = "select a.*, b.* from tbl_menu a join tbl_category b on a.category_code = b.category_code";

        
        //when
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<MenuAndCategory> menuAndCategoryList = new ArrayList<>();



        while(rset.next()) {
            Menu menu = new Menu();
            Category category = new Category();
            MenuAndCategory menuAndCategory = new MenuAndCategory();
            menu.setMenuName(rset.getString("menu_name"));
            menu.setMenuPrice(rset.getInt("menu_price"));
            menu.setMenuCode(rset.getInt("menu_code"));
            menu.setCategoryCode(rset.getInt("category_code"));
            menu.setOrderableStatus(rset.getString("orderable_status"));

            category.setCategoryCode(rset.getInt("category_code"));
            category.setCategoryName(rset.getString("category_name"));
            category.setRefCategoryCode(rset.getInt("ref_category_code"));

            menuAndCategory.setMenu(menu);
            menuAndCategory.setCategory(category);

            menuAndCategoryList.add(menuAndCategory);

        }
        
        //then

        Assertions.assertNotNull(menuAndCategoryList);
        
        menuAndCategoryList.forEach(menuAndCategory -> System.out.println("menuAndCategory = " + menuAndCategory));

        stmt.close();
        rset.close();
    }

    /*3. 패러다임 불일치 문제 (상속, 다형성, 캡슐화, 추상화) */

    /*필기.
    *  객체 지향 언어의 상속과 유사한 것이 데이터베이스의 서브타입 엔티티 이다
    *  유사한 것 같지만 다른 부분은 DB 의 상속은 상속 개념을 데이터로 추상화 해서
    *  슈퍼타입과 서브타입으로 구분하고, 물리적으로는 다른 테이블로 분리가 된 형태이다*/

    /*3-1. 연관관계 문제*/

    /*필기.
    *  DB 의 컬럼과 클래스의 필드의 내용물이 불일치하는 일이 발생한다
    *  서로 다른 테이블이 연관관계를 나타낼때 FK 를 통해서 연관관계를 형성하지만
    *  클래스에서는 전체 테이블을 전부 다 들고 있다(클래스에서는 연관관계를 표현할 방법이 없기 때문)*/

    /*4. 동일성 보장 문제*/

    @Test
    @DisplayName("조회한 두 개의 행을 담은 객체의 동일성 비교")
    void testEquals() throws SQLException {

        //given

        String query = "select * from tbl_menu where menu_code =1 ";
        //when
        Statement stmt1 = con.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query);

        Menu menu1 = null;

        while (rset1.next()){
            menu1=new Menu();
            menu1.setMenuCode(rset1.getInt("menu_code"));
            menu1.setMenuName(rset1.getString("menu_name"));
            menu1.setMenuPrice(rset1.getInt("menu_price"));
            menu1.setOrderableStatus(rset1.getString("orderable_status"));
        }


        Statement stmt2 = con.createStatement();
        ResultSet rset2 = stmt2.executeQuery(query);

        Menu menu2 = null;

        while (rset2.next()){
            menu2=new Menu();
            menu2.setMenuCode(rset2.getInt("menu_code"));
            menu2.setMenuName(rset2.getString("menu_name"));
            menu2.setMenuPrice(rset2.getInt("menu_price"));
            menu2.setOrderableStatus(rset2.getString("orderable_status"));
        }
        //then

        Assertions.assertFalse(menu1 == menu2);
        Assertions.assertEquals(menu1.getMenuName(),menu2.getMenuName());
        rset1.close();
        rset2.close();
        stmt1.close();
        stmt2.close();


        // 같은 값을 조회하는데 다른 인스턴스를 생성하는 비효율성이 있음을 확인할 수 있다

    }
}