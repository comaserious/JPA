package com.ohgiraffers.nativequery.section02.simple2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class NamedNativeQueryTest {

    @PersistenceContext
    private EntityManager manager;

    @DisplayName("named native test 1")
    @Test
    void test1 (){
        List<Object[]> categories = manager.createNamedQuery("categoryNamedNativeQuery").getResultList();

        categories.forEach(row -> {
            for(Object column : row){
                System.out.println(column + "/");
            }
            System.out.println();
        });
    }
}
