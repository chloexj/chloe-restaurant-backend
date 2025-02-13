package com.chloe.service;

import com.chloe.dto.SetmealDTO;
import com.chloe.dto.SetmealPageQueryDTO;
import com.chloe.entity.Setmeal;
import com.chloe.result.PageResult;
import com.chloe.vo.DishItemVO;
import com.chloe.vo.SetmealVO;

import java.util.List;

public interface SetService {
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void add(SetmealDTO setmealDTO);

    void deleteByIds(List<Long> ids);

    SetmealVO getBySetId(Long id);

    void update(SetmealDTO setmealDTO);

    void updateStatus(Integer status, Long id);
    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemById(Long id);
}
