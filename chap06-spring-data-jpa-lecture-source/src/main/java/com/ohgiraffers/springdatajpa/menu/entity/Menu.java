package com.ohgiraffers.springdatajpa.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
/*2. @Builder lombok 라이브러리에서 제공해주는 빌더 사용*/
//@Builder(toBuilder = true)


public class Menu {


    @Id
    @Column(name = "menu_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

//    public void setMenuName(String menuName) {
//        this.menuName=menuName;
//    }

    /*3. Entity 클래스 내부에서 builder 패턴 구현하기 */
    public Menu menuName(String var){
        this.menuName = var;
        return this;
    }
    public Menu menuPrice(int var){
        this.menuPrice=var;
        return this;
    }
    public Menu categoryCode(int var){
        this.categoryCode =var;
        return this;
    }
    public Menu orderableStatus(String var){
        this.orderableStatus = var;
        return  this;
    }

    public Menu builder(){
        return new Menu(this.menuCode,this.menuName,this.menuPrice,this.categoryCode,this.orderableStatus);

    }

}
