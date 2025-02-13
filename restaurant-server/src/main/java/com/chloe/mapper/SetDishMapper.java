package com.chloe.mapper;

import com.chloe.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetDishMapper {
@Select("select * from setmeal_dish where setmeal_id=#{setmealId}")
List<SetmealDish> getBySetmealId(Long setmealId);

    List<Long> getSetIdByDishIds(List<Long> dishIds);

    void deleteBatch(List<Long> setmealIds);

    void insert(List<SetmealDish> setmealDishes);
}