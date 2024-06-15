package com.ohgiraffers.toyproject.random.dao;

import com.ohgiraffers.toyproject.random.entity.Punish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PunishRepository extends JpaRepository<Punish,Integer> {



    @Query("select m from section01Punish m join m.randomBox c where c.gameNo =?1 and m.status =?2")
    List<Punish> findPunishCustom( int gameNo, String y);
}
