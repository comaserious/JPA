package com.ohgiraffers.toyproject.random.dao;

import com.ohgiraffers.toyproject.random.entity.RandomBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomRepository extends JpaRepository<RandomBox,Integer> {
}
