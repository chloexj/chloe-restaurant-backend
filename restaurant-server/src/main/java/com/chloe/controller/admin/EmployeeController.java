package com.chloe.controller.admin;

import com.chloe.constant.JwtClaimsConstant;
import com.chloe.dto.EmployeeDTO;
import com.chloe.dto.EmployeeLoginDTO;
import com.chloe.dto.EmployeePageQueryDTO;
import com.chloe.entity.Employee;
import com.chloe.properties.JwtProperties;
import com.chloe.result.PageResult;
import com.chloe.result.Result;
import com.chloe.service.EmployeeService;
import com.chloe.utils.JwtUtil;
import com.chloe.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags="Employee related api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "employee login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "employee logout")
    public Result<String> logout() {
        return Result.success();
    }

    // create new employee
    @PostMapping
    @ApiOperation("Create new employee")
    public Result create(@RequestBody EmployeeDTO employeeDTO){
        log.info("Create new employee:{}",employeeDTO);
        System.out.println("Current thread id:"+Thread.currentThread().getId());
        employeeService.create(employeeDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("Employee paging query")
    //因为不是json所以不用加request body
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
log.info("employee paging query:{}",employeePageQueryDTO);
PageResult pageResult=employeeService.pageQuery(employeePageQueryDTO);
return Result.success(pageResult);

    }

    //非查询类就不用泛型了
    @PostMapping("/status/{status}")
    @ApiOperation("Change employee status")
    public Result changeStatus( @PathVariable Integer status,Long id){
        log.info("Change employee status:{},{}",id,status);
        employeeService.changeStatus(status,id);
        return Result.success();
    }
//Get employee info by ID
    @GetMapping("/{id}")
    @ApiOperation("Get employee info by ID")
    public Result<Employee> getById(@PathVariable Long id){
       Employee employee= employeeService.getById(id);
        return Result.success(employee);
    }

    //update employee info
   @PutMapping
@ApiOperation("Update employee info")
    public Result updateEmp(@RequestBody EmployeeDTO employeeDTO){
        log.info("Update employee info:{}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
   }
}
