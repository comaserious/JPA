package com.ohgiraffers.nativequery.section02.simple2;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "section02Category")
@Table(name = "tbl_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SqlResultSetMappings(value = {
        /*1. @Column 으로 설정이 되어 있는 경우(자동)*/
        @SqlResultSetMapping(
                name = "categoryNamedMapping",
                entities = {@EntityResult(entityClass = Category.class)},
                columns = {@ColumnResult(name = "menu_count")}
        )

})
@NamedNativeQuery(
        name = "categoryNamedNativeQuery",
        query = "select a.category_code,a.category_name,a.ref_category_code, coalesce(v.menu_count,0) menu_count from tbl_category a " +
        "left join (select count(menu_code) as menu_count, category_code from tbl_menu group by category_code) v on a.category_code = v.category_code " +
        "order by 1",
        resultSetMapping = "categoryNamedMapping"
)
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
