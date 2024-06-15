package com.ohgiraffers.toyproject.random.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "section01RandomBox")
@Table(name = "tbl_random")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RandomBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_no")
    private int gameNo;


}
