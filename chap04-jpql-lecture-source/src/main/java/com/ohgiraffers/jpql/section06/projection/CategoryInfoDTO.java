package com.ohgiraffers.jpql.section06.projection;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)@Getter@Setter@ToString
public class CategoryInfoDTO {

    private int categoryCode;
    private String categoryName;
}
