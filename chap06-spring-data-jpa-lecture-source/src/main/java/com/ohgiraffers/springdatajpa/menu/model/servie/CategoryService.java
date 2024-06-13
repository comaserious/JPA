package com.ohgiraffers.springdatajpa.menu.model.servie;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.model.dao.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository repository, ModelMapper modelMapper){
        this.repository=repository;
        this.modelMapper = modelMapper;
    }

    /*5. @Query : jpql, native Query */
    public List<CategoryDTO> findAll() {

        List<Category> categoryList = repository.findAllCategory();

        return categoryList.stream().map(category->modelMapper.map(category,CategoryDTO.class)).collect(Collectors.toList());
    }
}
