package com.chloe.mapper;

import com.github.pagehelper.Page;
import com.chloe.annotation.AutoFill;
import com.chloe.dto.EmployeePageQueryDTO;
import com.chloe.entity.Employee;
import com.chloe.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);
@AutoFill(value = OperationType.INSERT)
    //create new employee
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, update_user) " +
            "values" +
            " (#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Employee employee);

    //paging query
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

//update employee info
@AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    //get employee info by id
@Select("select *from employee where id =#{id}")
    Employee getById(Long id);
}
