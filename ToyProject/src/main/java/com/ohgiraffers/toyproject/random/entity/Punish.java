package com.ohgiraffers.toyproject.random.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "section01Punish")
@Table(name = "tbl_punish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Punish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private int no;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "game_no")
    private RandomBox randomBox;

    public void setRandomBox(RandomBox randomBox) {
        this.randomBox = randomBox;
    }
}
