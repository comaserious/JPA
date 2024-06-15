package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.common.Pagination;
import com.ohgiraffers.springdatajpa.common.PagingButton;
import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.servie.CategoryService;
import com.ohgiraffers.springdatajpa.menu.model.servie.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    private final CategoryService categoryService;

    @Autowired
    public MenuController(MenuService menuService,CategoryService categoryService){
        this.menuService=menuService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{menuCode}")
    public String findMenuByMenuCode(@PathVariable int menuCode, Model model){

        MenuDTO menuDTO = menuService.findMenuByCode(menuCode);

        model.addAttribute("menu",menuDTO);

        return "menu/detail";
    }

    @GetMapping("/list")
    public String findAllMenu(Model model, @PageableDefault Pageable pageable){

        /*no paging 버젼*/
//        List<MenuDTO> menuDTOList = menuService.findAllMenu();
//
//        model.addAttribute("list",menuDTOList);

        /*paging 버전*/
        log.info("pageable : {}",pageable);

        Page<MenuDTO> menuDTOS = menuService.findAllMenu(pageable);

        log.info("조회한 내용 목록 : {} ",menuDTOS.getContent());
        log.info("총페이지수 : {} ",menuDTOS.getTotalPages());
        log.info("총 메뉴의 수 : {} ", menuDTOS.getTotalElements());
        log.info("해당 페이지에 표시될 요소의 수 : ",menuDTOS.getSize());
        log.info("해당 페이지의 실제 요소 갯수 : {} ", menuDTOS.getNumberOfElements());
        log.info("첫페이지 여부 :{} ",menuDTOS.isFirst());
        log.info("마지막페이지 여부 : {} ",menuDTOS.isLast());
        log.info("정렬방식 : {} ",menuDTOS.getSort());
        log.info("여러페이지중 현재 인덱스 : {} ",menuDTOS.getNumber());

        PagingButton paging = Pagination.getPagingButtonInfo(menuDTOS);
        model.addAttribute("paging",paging);
        model.addAttribute("list",menuDTOS);

        return "menu/all";
    }

    @GetMapping("/querymethod")
    public void queryMethodPage(){}


    @GetMapping("search")
    public String findByMenuPrice(@RequestParam int menuPrice,Model model){

        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);

        model.addAttribute("list",menuList);
        model.addAttribute("price",menuPrice);

        return "menu/searchResult";
    }

    @GetMapping("/regist")
    public void registPage(){}

    @GetMapping(value = "/categoryCode",produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> getAllCategory(){

        return categoryService.findAll();
    }

    @PostMapping("/regist")
    public String registMenu(MenuDTO menuDTO){

        System.out.println("menuDTO = " + menuDTO);
        menuService.registNewMenu(menuDTO);

        return "redirect:/menu/list";
    }

    @GetMapping("/modify")
    public void modifyPage(){}

    @PostMapping("/modify")
    public String modifyMenu(MenuDTO menuDTO){

        menuService.modifyMenu(menuDTO);

        return "redirect:/menu/"+menuDTO.getMenuCode();

    }

    @GetMapping("/delete")
    public void deletePage(){}

    @PostMapping("/delete")
    public String deleteMenu(@RequestParam int menuCode){

        menuService.deleteMenu(menuCode);

        return "redirect:/menu/list";
    }

}
