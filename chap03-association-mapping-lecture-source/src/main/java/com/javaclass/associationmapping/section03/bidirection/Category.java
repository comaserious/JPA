package com.javaclass.associationmapping.section03.bidirection;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.List;

@Entity(name="bidirection_category")
@Table(name="tbl_category")
/*양방향 연관관계가 설정되어있는 엔티티를 toString 메소드에서 참조할때,
순환 참조가 발생하여 스택오버플로우가 발생할 수 있다
* */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "menuList")
public class Category {

    @Id
    @Column(name="category_code")
    private int categoryCode;

    @Column(name="category_name")
    private String categoryName;

    @Column(name="ref_category_code")
    private Integer refCategoryCode;

    /*필기.
    *  mappedBy
    *  객체의 참조는 둘인데, 외래키는 하나인 상황을 해결 하기 위해서
    *  두 객체의 연관관계 중 하나를 정해서 테이블의 외래키를 관리하는데
    *  이를 연관관계의 주인(Owner) 라고 한다.
    *  속성은 연관관계의 주인(외래키가 있는 쪽)이 아닌 쪽에서 사용되며,
    *  주인 엔티티의 연관된 필드명을 가리킨다
    *  - 원래라면 연관관계의 주인은 테이블에서 외래키가 있는 곳으로 설정해야하지만 , 반대로 설정하는 것도 가능하다
    *    하지만 성능상에 문제가 있기 때문에 권장하지 않는다 */


    // 주인의 필드값을 적어 넣어 연결을 시켜준다
    @OneToMany(mappedBy = "category")
    private List<Menu> menuList;


    public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
    }



//    @Override
//    public String toString() {
//        return "Category{" +
//                "categoryCode=" + categoryCode +
//                ", categoryName='" + categoryName + '\'' +
//                ", refCategoryCode=" + refCategoryCode +
////                ", menuList=" + menuList +
//                '}';
//    }

    // menuList 는 category를 가지고 있고 다시 category는 menuList 를 가지고 있다
    // 즉 서로 서로 참조하고 있으면서 toString 이 무한히 반복 된다
}
