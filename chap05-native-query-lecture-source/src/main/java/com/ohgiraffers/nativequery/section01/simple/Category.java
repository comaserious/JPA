package com.ohgiraffers.nativequery.section01.simple;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "section01Category")
@Table(name = "tbl_category")
@SqlResultSetMappings(value = {
        /*1. @Column 으로 설정이 되어 있는 경우(자동)*/
        @SqlResultSetMapping(
                name = "categoryAutoMapping",
                entities = {@EntityResult(entityClass = Category.class)},
                columns = {@ColumnResult(name = "menu_count")}
        ),
        /*2. @Column 으로 설정이 되어 있지 않는 경우(수동)*/
        @SqlResultSetMapping(
                name = "categoryManualMapping",
                entities = {
                        @EntityResult(entityClass = Category.class , fields = {
                                @FieldResult(name = "categoryCode", column = "category_code"),
                                @FieldResult(name = "categoryName", column = "category_name"),
                                @FieldResult(name = "refCategoryCode", column = "ref_category_code")

                        })
                },
                columns = {@ColumnResult(name = "menu_count")}

        )


})
@NamedNativeQuery(
        name = "namedNativeQuery",
        query = "select a.category_code,a.category_name,a.ref_category_code, coalesce(v.menu_count,0) menu_count from tbl_category a " +
                "left join (select count(menu_code) as menu_count, category_code from tbl_menu group by category_code) v on a.category_code = v.category_code " +
                "order by 1",
        resultSetMapping = "categoryAutoMapping"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

}
