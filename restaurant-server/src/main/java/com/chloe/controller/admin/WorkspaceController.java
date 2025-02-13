package com.chloe.controller.admin;

import com.chloe.result.Result;
import com.chloe.service.WorkspaceService;
import com.chloe.vo.BusinessDataVO;
import com.chloe.vo.DishOverViewVO;
import com.chloe.vo.OrderOverViewVO;
import com.chloe.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@Slf4j
@Api(tags = "workspace related api")
@RequestMapping("/admin/workspace")
public class WorkspaceController {
@Autowired
private WorkspaceService workspaceService;
    @GetMapping("/businessData")
    public Result<BusinessDataVO> getBusinessData(){
    log.info("Get today's business data");
        LocalDate date = LocalDate.now();
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
        LocalDateTime begin = LocalDateTime.of(date, LocalTime.MIN);
    BusinessDataVO businessDataVO= workspaceService.getBusinessData(begin,end);
    return Result.success(businessDataVO);
}

@GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> overviewSetmeals(){
    log.info("Get setmeal data");
    SetmealOverViewVO setmealOverViewVO= workspaceService.getOverviewSetmeal();
    return Result.success(setmealOverViewVO);
}
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> overviewDish(){
        log.info("Get dish data");
        DishOverViewVO dishOverViewVO= workspaceService.getOverviewDish();
        return Result.success(dishOverViewVO);
    }

    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> overviewOrder(){
        log.info("Get order data");
        OrderOverViewVO orderOverViewVO= workspaceService.getOverviewOrder();
        return Result.success(orderOverViewVO);

    }
}
