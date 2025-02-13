package com.chloe.service;

import com.chloe.dto.EmployeeDTO;
import com.chloe.dto.EmployeeLoginDTO;
import com.chloe.dto.EmployeePageQueryDTO;
import com.chloe.entity.Employee;
import com.chloe.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void create(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void changeStatus(Integer status, Long id);

    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);
}
