package com.ohgiraffers.jpql.section06.projection;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "section06Category")
@Table(name = "tbl_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = "menuList")
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private int refCategoryCode;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_code")
    private List<Menu> menuList;


}
