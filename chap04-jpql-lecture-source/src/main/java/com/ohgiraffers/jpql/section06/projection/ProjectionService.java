package com.ohgiraffers.jpql.section06.projection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectionService {

    private ProjectionRepository repository;

    public ProjectionService(ProjectionRepository repository){
        this.repository = repository;

    }

    @Transactional
    public List<Menu> singleEntityTest() {
        List<Menu> menuList = repository.singleEntityTest();

        Menu menu = repository.findMenu(1);

        System.out.println(menu);

        menuList.get(7).setMenuName("새로운 이름");


        return menuList;
    }

    @Transactional(readOnly = true)
    public BidirectionCategory biDirectionProjection(int menuCode) {

        BidirectionCategory categoryOfMenu = repository.biDirectionProjection(menuCode);
        return categoryOfMenu;
    }

    public List<MenuInfo> findMenuNameAndPrice() {

        return repository.findMenuNameAndPrice();
    }

    public List<String> scalarTypedQuery() {

        return repository.scalarTypedQuery();
    }

    public List<Object[]> scalarQuery() {

        return repository.scalarQuery();
    }

    public List<CategoryInfoDTO> newProjection() {
        return repository.newProjection();
    }
}
