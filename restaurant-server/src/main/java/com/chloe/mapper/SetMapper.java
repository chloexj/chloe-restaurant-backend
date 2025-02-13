package com.chloe.mapper;

import com.github.pagehelper.Page;
import com.chloe.annotation.AutoFill;
import com.chloe.dto.SetmealPageQueryDTO;
import com.chloe.entity.Setmeal;
import com.chloe.enumeration.OperationType;
import com.chloe.vo.DishItemVO;
import com.chloe.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMapper {
    @Select("select count(id) from setmeal where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);

    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);
@Select("select * from setmeal where id=#{id}")
    Setmeal getById(Long id);


    void deleteBatch(List<Long> ids);

    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    List<Setmeal> list(Setmeal setmeal);

    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);
@Select("select count(id) from setmeal where status=#{status}")
    Integer getStatus(Integer status);
}
