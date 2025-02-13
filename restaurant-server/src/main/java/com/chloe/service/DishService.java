package com.chloe.service;

import com.chloe.dto.DishDTO;
import com.chloe.dto.DishPageQueryDTO;
import com.chloe.entity.Dish;
import com.chloe.result.PageResult;
import com.chloe.vo.DishVO;

import java.util.List;

public interface DishService {
    public void addWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    DishVO getByIdWithFlavor(Long id);

    void updateStatus(Integer status, Long id);

    void update(DishDTO dishDTO);

   List<DishVO> getByCategoryId(Long categoryId);

    List<DishVO> listWithFlavor(Dish dish);
}
