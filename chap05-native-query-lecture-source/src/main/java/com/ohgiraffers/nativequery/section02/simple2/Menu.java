package com.ohgiraffers.nativequery.section02.simple2;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "section02Menu")
@Table(name = "tbl_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Menu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menuName")
    private String menuName;

    @Column(name = "menuPrice")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderableStatus")
    private String orderableStatus;
}
