package com.chloe.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.chloe.constant.MessageConstant;
import com.chloe.constant.PasswordConstant;
import com.chloe.constant.StatusConstant;
import com.chloe.dto.EmployeeDTO;
import com.chloe.dto.EmployeeLoginDTO;
import com.chloe.dto.EmployeePageQueryDTO;
import com.chloe.entity.Employee;
import com.chloe.exception.AccountLockedException;
import com.chloe.exception.AccountNotFoundException;
import com.chloe.exception.PasswordErrorException;
import com.chloe.mapper.EmployeeMapper;
import com.chloe.result.PageResult;
import com.chloe.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {

        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对

        //encrypt password from front end
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    //create new employee
    @Override
    public void create(EmployeeDTO employeeDTO) {
        System.out.println("Current thread id:" + Thread.currentThread().getId());
        Employee employee = new Employee();
        //copy properties from DTO object to entity
        BeanUtils.copyProperties(employeeDTO, employee);
        //set account's status
        employee.setStatus(StatusConstant.ENABLE);
        //set password
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //set time

        //set id who create this

//        BaseContext.removeCurrentId();
        employeeMapper.insert(employee);
    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> records = page.getResult();

        return new PageResult(total, records);
    }

    //Change employee status
    @Override
    public void changeStatus(Integer status, Long id) {
        Employee employee= Employee.builder()
                .status(status)
                .id(id).build();
        employeeMapper.update(employee);
    }

    @Override
    public Employee getById(Long id) {
       Employee employee= employeeMapper.getById(id);
       employee.setPassword("****");
        return employee;
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employeeMapper.update(employee);
    }

}
