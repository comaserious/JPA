package com.javaclass.associationmapping.section03.bidirection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BidirectionService {

    private BidirectionRepository bidirectionRepository;

    public BidirectionService(BidirectionRepository bidirectionRepository){
        this.bidirectionRepository = bidirectionRepository;
    }



    public Menu findMenu(int menuCode) {

        return bidirectionRepository.findMenu(menuCode);
    }

    // OneToMany 이기 때문에 @Transactional 이 필요하다
    @Transactional(readOnly = true)
    public Category findCategory(int categoryCode) {

        Category foundCategory = bidirectionRepository.findCategoryCode(categoryCode);

        System.out.println("[category menuList]"+ foundCategory.getMenuList());
        return foundCategory;
    }

    @Transactional
    public void registMenu(Menu menu) {

        bidirectionRepository.saveMenu(menu);
    }

    @Transactional
    public void insertCategory(Category category) {
        bidirectionRepository.saveCategory(category);
    }
}
