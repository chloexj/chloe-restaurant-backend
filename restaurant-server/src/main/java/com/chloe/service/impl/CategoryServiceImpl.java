package com.chloe.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.chloe.constant.MessageConstant;
import com.chloe.dto.CategoryDTO;
import com.chloe.dto.CategoryPageQueryDTO;
import com.chloe.entity.Category;
import com.chloe.exception.DeletionNotAllowedException;
import com.chloe.mapper.CategoryMapper;
import com.chloe.mapper.DishMapper;
import com.chloe.mapper.SetMapper;
import com.chloe.result.PageResult;
import com.chloe.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
@Autowired
    private  DishMapper dishMapper;
@Autowired
private SetMapper setMapper;
@Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        long total = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void updateInfo(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.update(category);
    }

    //update status
    @Override
    public void updateStatus(Integer status, Long id) {
        Category category =
                Category.builder().status(status).id(id).build();
        categoryMapper.update(category);
    }

    @Override
    public void add(CategoryDTO categoryDTO) {

        Category category =
                Category.builder().updateTime(LocalDateTime.now())
                        .status(0)
                        .build();
        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.insert(category);
    }

    @Override
    public void delete(Long id) {
        Integer count= dishMapper.countByCategoryId(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        count=setMapper.countByCategoryId(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        categoryMapper.delete(id);
    }

    @Override
    public List<Category> getByType(Integer type) {
        List<Category> list= categoryMapper.get(type);
        return list;
    }
}
