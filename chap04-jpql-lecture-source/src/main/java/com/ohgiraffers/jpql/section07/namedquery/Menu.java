package com.ohgiraffers.jpql.section07.namedquery;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "section07Menu")
@Table(name = "tbl_menu")
@NamedQueries({
        @NamedQuery(name = "section07Menu.selectMenuList", query = "select m from section07Menu m"),
        @NamedQuery(name = "selectMenuName",query = "select m.menuName from section07Menu m")

})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class Menu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;


}
