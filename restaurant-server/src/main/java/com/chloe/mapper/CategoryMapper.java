package com.chloe.mapper;

import com.github.pagehelper.Page;
import com.chloe.annotation.AutoFill;
import com.chloe.dto.CategoryPageQueryDTO;
import com.chloe.entity.Category;
import com.chloe.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
@AutoFill(value = OperationType.UPDATE)
    void update(Category category);
    @AutoFill(value = OperationType.INSERT)

@Insert("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user) " +
        "values (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Category category);

@Delete("delete from category where id=#{id}")
    void delete(Long id);


List<Category> get(Integer type);
}
