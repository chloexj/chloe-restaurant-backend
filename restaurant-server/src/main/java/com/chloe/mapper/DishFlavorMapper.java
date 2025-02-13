package com.chloe.mapper;

import com.chloe.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void insertBatch(List<DishFlavor> flavors);
@Delete("delete from dish_flavor where dish_id=#{dishId}")
    void deleteById(Long dishId);

    void deleteBatch(List<Long> dishIds);
@Select("select *from dish_flavor where dish_id=#{dishId}")
    List<DishFlavor> getById(Long dishId);
}
