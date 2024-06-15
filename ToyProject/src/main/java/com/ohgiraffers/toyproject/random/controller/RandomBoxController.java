package com.ohgiraffers.toyproject.random.controller;

import com.ohgiraffers.toyproject.random.DTO.PunishDTO;
import com.ohgiraffers.toyproject.random.service.PunishService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/randombox")
public class RandomBoxController {

    private final PunishService service;

    public RandomBoxController(PunishService service){
        this.service = service;
    }

    @GetMapping("/regist")
    public void regist(){
    }

    @PostMapping("/regist")
    @Transactional
    public String randomPunish(@RequestParam Map<String,String> punish, HttpSession session){

        Set<Map.Entry<String,String>> entrySet = punish.entrySet();


        List<PunishDTO> punishDTOList = new ArrayList<>();

        for(Map.Entry<String, String> entry : entrySet){
            System.out.println(entry.getKey()+"="+entry.getValue());
            PunishDTO p = new PunishDTO();
            p.setName(entry.getValue());
            punishDTOList.add(p);
        }

        int gameNo = service.savePunish(punishDTOList);
        session.setAttribute("gameNo",gameNo);

        return "randombox/punish";
    }

    @GetMapping(value = "/repeat",produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<PunishDTO> repeat(HttpSession session){
        int gameNo = (int) session.getAttribute("gameNo");
        List<PunishDTO> punishDTOList = service.findAll(gameNo);

        punishDTOList.forEach(System.out::println);

        return punishDTOList;
    }

}
