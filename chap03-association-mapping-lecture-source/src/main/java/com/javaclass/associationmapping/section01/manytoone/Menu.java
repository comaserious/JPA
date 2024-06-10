package com.javaclass.associationmapping.section01.manytoone;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="menu_and_category")
@Table(name="tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Menu {
    @Id
    @Column(name="menu_code")
    private int menuCode;


    @Column(name="menu_name")
    private String menuName;

    @Column(name="menu_price")
    private int menuPrice;

    /*영속성 전이
    * 특정 엔티티를 영속화(등록) 할때, 연관된 엔티티도 함께
    * 영속화 한다는 의미이다.
    *  즉, -> Menu 엔티티를 영속화 할때 category 또한 영속화 해야한다*/

    // semi project 를 생각해보자 그때도 참조당하는 테이블을 먼저 생성해야 참조하는 테이블을 생성할 수있었다
    // 즉 category 가 있어야 Menu 를 만들 수 있는 것이다. 엄연히 순서의 문제이다
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="category_code")
    private Category category;

    @Column(name="orderable_status")
    private String orderableStatus;

}
