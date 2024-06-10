package com.javaclass.associationmapping.section02.onetomany;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OneToManyService {

    private OneToManyRepository oneToManyRepository;

    public OneToManyService(OneToManyRepository oneToManyRepository){
        this.oneToManyRepository = oneToManyRepository;
    }

    @Transactional(readOnly = true)
    public Category findCategory(int categoryCode) {

        Category category = oneToManyRepository.findCategory(categoryCode);

//        System.out.println("category = " + category);

        List<Menu> menuList = category.getMenuList();

        for(Menu m: menuList){
            System.out.println("m = " + m);
        }
        return category;
    }

    @Transactional
    public void registMenu(CategoryDTO categoryDTO) {

        Category category = new Category(categoryDTO.getCategoryCode(),categoryDTO.getCategoryName(),categoryDTO.getRefCategoryCode(),null)    ;

        Menu menu = new Menu(
                categoryDTO.getMenuDTOS().get(0).getMenuCode(),
                categoryDTO.getMenuDTOS().get(0).getMenuName(),
                categoryDTO.getMenuDTOS().get(0).getMenuPrice(),
                categoryDTO.getMenuDTOS().get(0).getCategoryCode(),
                categoryDTO.getMenuDTOS().get(0).getOrderableStatus()
        );

        List<Menu> menuList = new ArrayList<>();

        menuList.add(menu);
        category.setMenuList(menuList);

        oneToManyRepository.registCategory(category);
    }
}
