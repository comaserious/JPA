package com.ohgiraffers.springdatajpa.menu.model.dao;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/*필기.
*  JpaRepository<내가사용할 Entity,해당 클래스의 id 타입> 상속받을 것이다.
*  Repository <= CrudRepository <= PagingAndSortingRepository <= JpaRepository 
*  - EntityMangerFactory , EntityManger , EntityTransaction 자동 구현
*  */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    // generic 으로 엔티티 Menu 를 사용하였기 때문에 find엔티티명By필드명 에서 엔티티명을 생략할 수 있다.



    List<Menu> findByMenuPriceGreaterThan(int menuPrice);
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(int menuPrice);

    List<Menu> findByMenuPriceGreaterThan(int menuPrice, Sort menuPrice1);
}
