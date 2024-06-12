package com.ohgiraffers.jpql.section06.projection;

import com.ohgiraffers.jpql.section05.join.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "biDirectionMenu")
@Table(name = "tbl_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class BiDirectionMenu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private BidirectionCategory category;

    @Column(name = "orderable_status")
    private String orderableStatus;
}
