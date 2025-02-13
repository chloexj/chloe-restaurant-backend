package com.chloe.service;

import com.chloe.dto.CategoryDTO;
import com.chloe.dto.CategoryPageQueryDTO;
import com.chloe.entity.Category;
import com.chloe.result.PageResult;

import java.util.List;

public interface CategoryService {
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateInfo(CategoryDTO categoryDTO);

    void updateStatus(Integer status, Long id);

    void add(CategoryDTO categoryDTO);

    void delete(Long id);

    List<Category> getByType(Integer type);
}
