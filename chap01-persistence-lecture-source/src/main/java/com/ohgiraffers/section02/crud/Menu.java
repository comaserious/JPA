package com.ohgiraffers.section02.crud;

import jakarta.persistence.*;

@Entity(name ="section02Menu")
@Table(name="tbl_menu")
public class Menu {

    // pk =>not null,unique=> auto_increment
    @Id  // not null, unique
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // Identity = > mysql Sequence => oracle
    /* strategy 속성
    *  - AUTO : 우리가 사용하는 DB에 따른다
    *  - IDENTITY / SEQUENCE : mysql auto_increment 사용 oracle sequence 를 사용*/
    @Column(name="menu_code")
    private int menuCode;

    @Column(name="menu_name")
    private String menuName;

    @Column(name="menu_price")
    private int menuPrice;

    @Column(name="category_code")
    private int categoryCode;

    @Column(name="orderable_status")
    private String orderableStatus;

    // 동일 패키지까지만 접근 가능
    protected Menu(){}

    protected Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {


        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }
}
