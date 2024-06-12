package com.ohgiraffers.jpql.section06.projection;

import com.ohgiraffers.jpql.section05.join.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "biDirectionCategory")
@Table(name = "tbl_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "menuList")
public class BidirectionCategory {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private int refCategoryCode;

    @OneToMany(mappedBy = "category")
    private List<BiDirectionMenu> menuList;
}
