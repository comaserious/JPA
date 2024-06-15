package com.ohgiraffers.toyproject.random.service;

import com.ohgiraffers.toyproject.random.DTO.PunishDTO;
import com.ohgiraffers.toyproject.random.dao.PunishRepository;
import com.ohgiraffers.toyproject.random.dao.RandomRepository;
import com.ohgiraffers.toyproject.random.entity.Punish;
import com.ohgiraffers.toyproject.random.entity.RandomBox;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PunishService {

    private final PunishRepository repository;
    private final RandomRepository randomRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public PunishService(PunishRepository repository,RandomRepository randomRepository,ModelMapper modelMapper){
        this. repository=repository;
        this.randomRepository = randomRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public int savePunish(List<PunishDTO> punishDTOList) {
        RandomBox randomBox = new RandomBox();
        randomRepository.save(randomBox);
        System.out.println("randomBox = " + randomBox);
        List<Punish> punishes = new ArrayList<>();
        for(PunishDTO p : punishDTOList){
            p.setGameNo(randomBox.getGameNo());
            p.setStatus("Y");
            System.out.println("p = " + p);
            Punish punish = new Punish();
            punish = punish.builder().name(p.getName()).status(p.getStatus()).build();
            punish.setRandomBox(randomBox);
            punishes.add(punish);
        }

        for(Punish p : punishes){
            System.out.println("p11 = " + p);
            repository.save(p);
        }

        return randomBox.getGameNo();
    }

    public List<PunishDTO> findAll(int gameNo) {

        List<Punish> punishes = repository.findPunishCustom(gameNo,"Y");

        return punishes.stream().map(p -> modelMapper.map(p,PunishDTO.class)).collect(Collectors.toList());
    }
}
