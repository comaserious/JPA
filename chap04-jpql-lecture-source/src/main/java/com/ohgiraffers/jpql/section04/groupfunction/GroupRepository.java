package com.ohgiraffers.jpql.section04.groupfunction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepository {

    @PersistenceContext
    private EntityManager manager;


    public long countMenuOfCategory(Long categoryCode) {

        String jpql = "select count(m.menuPrice) from section04Menu m where m.categoryCode = : categoryCode";

        Long count = manager.createQuery(jpql,Long.class).setParameter("categoryCode",categoryCode).getSingleResult();

        return count;

    }

    public Long noResultSum(int categoryCode) {

        String jpql= "select sum(m.menuPrice) from section04Menu m where m.categoryCode =:categoryCode";

        Long resultSum = manager.createQuery(jpql,Long.class).setParameter("categoryCode",categoryCode).getSingleResult();

        return resultSum;
    }

    public List<Object[]> selectGroupAndHaving(Long minPrice) {

        String jpql = "select m.categoryCode, sum(m.menuPrice) from section04Menu m group by m.categoryCode having sum(m.menuPrice) >= :minPrice";

        Query query = manager.createQuery(jpql);

        List<Object[]> result = query.setParameter("minPrice",minPrice).getResultList();

        return result;
    }
}
