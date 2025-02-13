package com.chloe.mapper;

import com.github.pagehelper.Page;
import com.chloe.annotation.AutoFill;
import com.chloe.dto.DishPageQueryDTO;
import com.chloe.entity.Dish;
import com.chloe.enumeration.OperationType;
import com.chloe.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @Select("select count(id) from dish where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);

@AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
@Select("select * from dish where id=#{id}")
    Dish getById(Long id);
@Delete("delete from dish where id=#{id}")
    void deleteById(Long id);

    void deleteBatch( List<Long> ids);

@AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);


    List<Dish> getByCategoryId(Dish dish);
@Select("select count(id) from dish where status=#{status}")
    Integer getStatus(Integer status);
}
