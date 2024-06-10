package com.javaclass.associationmapping.section02.onetomany;

import lombok.*;

@NoArgsConstructor@AllArgsConstructor@Getter@ToString@Setter
public class MenuDTO {

    private int menuCode;

    private String menuName;

    private int menuPrice;

    private int categoryCode;

    private String orderableStatus;
}
