package com.javaclass.associationmapping.section01.manytoone;

import lombok.*;

@NoArgsConstructor@AllArgsConstructor@Getter@ToString@Setter
public class MenuDTO {

    private int menuCode;

    private String menuName;

    private int menuPrice;

    private CategoryDTO categoryDTO;

    private String orderableStatus;
}
