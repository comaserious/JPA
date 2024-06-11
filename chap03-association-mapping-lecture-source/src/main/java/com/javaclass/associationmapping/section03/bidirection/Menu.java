package com.javaclass.associationmapping.section03.bidirection;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="bidirection_menu")
@Table(name="tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString()
public class Menu {
    @Id
    @Column(name="menu_code")
    private int menuCode;


    @Column(name="menu_name")
    private String menuName;

    @Column(name="menu_price")
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name="category_code")
    // fk 를 가지고 있기 때문에 Menu 가 주인이다
    private Category category;

    @Column(name="orderable_status")
    private String orderableStatus;


}
