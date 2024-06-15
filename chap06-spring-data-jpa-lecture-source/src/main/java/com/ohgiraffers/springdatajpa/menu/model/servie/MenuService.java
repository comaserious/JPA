package com.ohgiraffers.springdatajpa.menu.model.servie;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;

import com.ohgiraffers.springdatajpa.menu.model.dao.MenuRepository;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public MenuService(MenuRepository repository,ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper=modelMapper;
    }

    /*1. findById()*/
    public MenuDTO findMenuByCode(int menuCode) {

        Menu menu = repository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

//        MenuDTO menuDTO = new MenuDTO(menu.getMenuCode(),menu.getMenuName(),menu.getMenuPrice(),menu.getCategoryCode(),menu.getOrderableStatus());
            // 매번 이런식으로 entity => DTO 로 바꾸는것은 매우 번거로운 일이다. (=>BeanConfig)


        return modelMapper.map(menu,MenuDTO.class);
        // 첫번째 인자 바꿀 녀석, 두번째 인자 바꾼후의 타입을 작성한다.
    }

    // 페이징 처리 없이 메뉴 전체 조회
    public List<MenuDTO> findAllMenu() {

        List<Menu> allMenu = repository.findAll(Sort.by("menuCode").descending());

//        for(Menu m :allMenu){
//            menuDTOList.add(modelMapper.map(m,MenuDTO.class));
//        }
        return allMenu.stream()
                .map(menu -> modelMapper.map(menu,MenuDTO.class))
                .collect(Collectors.toList());
    }

    // 페이징 처리를 통한 메뉴 전체 조회
    public Page<MenuDTO> findAllMenu(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0: pageable.getPageNumber() -1,
                2,
                Sort.by("menuCode").descending());

        Page<Menu> menus = repository.findAll(pageable);

        return menus.map(menu -> modelMapper.map(menu,MenuDTO.class));
    }

    /*Query 메소드를 사용해서 조회하기*/
    public List<MenuDTO> findByMenuPrice(int menuPrice) {

//        List<Menu> menuList = repository.findByMenuPriceGreaterThan(menuPrice);
//        List<Menu> menuList = repository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);

        List<Menu> menuList = repository.findByMenuPriceGreaterThan(menuPrice,Sort.by("menuPrice").descending());

        return menuList.stream().map(menu -> modelMapper.map(menu,MenuDTO.class)).collect(Collectors.toList());
    }

    // save() 등록 관련 메소드
    @Transactional
    public void registNewMenu(MenuDTO menuDTO) {

        repository.save(modelMapper.map(menuDTO,Menu.class));

    }

    @Transactional
    public void modifyMenu(MenuDTO menuDTO) {

        Menu menu = repository.findById(menuDTO.getMenuCode()).orElseThrow(IllegalArgumentException::new);

        /*1. setter 사용해서 수정해보기 */
//        menu.setMenuName(menuDTO.getMenuName());
        // 권장하지는 않는다 Entity 의 무결성을 위배하기 때문에....

        /*2. @Builder */
//        menu = menu.toBuilder().menuName(menuDTO.getMenuName()).build();
//
//        repository.save(menu);
//        System.out.println("menu = " + menu);

        /*3. Entity 클래스 내부에서 builder 패턴 사용해서 구현*/
        menu = menu.menuName(menuDTO.getMenuName()).builder();

//        repository.save(menu);
        // 변경감지로 save 하지 않아도 자동으로 DB 가 업데이트가 된다

    }

    @Transactional
    public void deleteMenu(int menuCode) {
        Menu menu = repository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

//        repository.delete(menu);
        repository.deleteById(menuCode);
    }
}
