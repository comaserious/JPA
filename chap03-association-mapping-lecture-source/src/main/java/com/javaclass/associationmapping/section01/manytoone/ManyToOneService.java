package com.javaclass.associationmapping.section01.manytoone;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManyToOneService {

    private ManyToOneRepository manyToOneRepository;

    public ManyToOneService(ManyToOneRepository manyToOneRepository){
        this.manyToOneRepository= manyToOneRepository;
    }


    public Menu findMenu(int menuCode) {

        return manyToOneRepository.findMenu(menuCode);
    }

    public String findCategoryName(int menuCode) {

        return  manyToOneRepository.findCategoryName(menuCode);
    }

    @Transactional
    public void registMenu(MenuDTO newMenu) {

        Menu menu = new Menu(newMenu.getMenuCode(),newMenu.getMenuName(),newMenu.getMenuPrice(),new Category(newMenu.getCategoryDTO().getCategoryCode(),newMenu.getCategoryDTO().getCategoryName(),newMenu.getCategoryDTO().getRefCategoryCode()),newMenu.getOrderableStatus());

        manyToOneRepository.save(menu);
    }
}
