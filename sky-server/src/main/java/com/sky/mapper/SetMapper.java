package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetMapper {
    @Select("select count(id) from setmeal where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    @AutoFill(value = OperationType.INSERT)
@Insert("insert into setmeal (category_id, name, price, description, image, create_time, update_time, create_user, update_user) " +
        "values " +
        "(#{categoryId},#{name},#{price},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser}) ")
    void insert(Setmeal setmeal);
}
