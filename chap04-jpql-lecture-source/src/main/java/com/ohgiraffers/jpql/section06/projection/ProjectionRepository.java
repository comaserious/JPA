package com.ohgiraffers.jpql.section06.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectionRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Menu> singleEntityTest() {

        String jpql = "select m from section06Menu m";

        List<Menu> menuList = manager.createQuery(jpql,Menu.class).getResultList();

        return menuList;
    }

    public Menu findMenu(int menuCode) {

        return manager.find(Menu.class,menuCode);
    }

    public BidirectionCategory biDirectionProjection(int menuCode) {

        String jpql = "select m.category from biDirectionMenu m join m.category where m.menuCode = :menuCode";

        BidirectionCategory categoryOfMenu = manager.createQuery(jpql,BidirectionCategory.class).setParameter("menuCode",menuCode).getSingleResult();
        return categoryOfMenu;
    }

    public List<MenuInfo> findMenuNameAndPrice() {

        String jpql= "select m.menuInfo from embeddedMenu m";

        List<MenuInfo> menuInfos = manager.createQuery(jpql,MenuInfo.class).getResultList();

        return menuInfos;
    }

    public List<String> scalarTypedQuery() {

        String jpql = "select c.categoryName from section06Category c";

        List<String> categoryNameList = manager.createQuery(jpql,String.class).getResultList();

        return categoryNameList;
    }

    public List<Object[]> scalarQuery() {

        String jpql = "select m.categoryName, m.categoryCode from section06Category m";

        List<Object[]> resultList = manager.createQuery(jpql).getResultList();

        return resultList;
    }

    public List<CategoryInfoDTO> newProjection() {

        String jpql = "select new com.ohgiraffers.jpql.section06.projection.CategoryInfoDTO(c.categoryCode,c.categoryName) from section06Category c";

        List<CategoryInfoDTO> resultCategoryList = manager.createQuery(jpql,CategoryInfoDTO.class).getResultList();

        return resultCategoryList;
    }
}
