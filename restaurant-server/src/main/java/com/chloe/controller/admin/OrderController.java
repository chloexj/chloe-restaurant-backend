package com.chloe.controller.admin;

import com.chloe.dto.*;
import com.chloe.result.PageResult;
import com.chloe.result.Result;
import com.chloe.service.OrderService;
import com.chloe.vo.OrderStatisticsVO;
import com.chloe.vo.OrderVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "Order admin related api")
public class OrderController {
@Autowired
private OrderService orderService;
    //cancel order
@PutMapping("/cancel")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO){
    log.info("Cancel order:{}",ordersCancelDTO);
orderService.cancelOrder(ordersCancelDTO);
        return Result.success();
    }
    @GetMapping("/details/{id}")
    public Result<OrderVO> checkOrders(@PathVariable Long id){
OrderVO orderVO= orderService.getById(id);
return Result.success(orderVO);
    }
@GetMapping("/conditionSearch")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO){
log.info("Order page query:{}",ordersPageQueryDTO);
PageResult pageResult = orderService.pageQuery(ordersPageQueryDTO);
return Result.success(pageResult);

}

    @PutMapping("/confirm")
    public Result confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
   orderService.confirmOrder(ordersConfirmDTO);


    return Result.success();
    }
    @PutMapping("/rejection")
    public Result rejectOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO){
    orderService.rejectOrder(ordersRejectionDTO);
    return Result.success();
    }

    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> statisticsOrders(){
        OrderStatisticsVO orderStatisticsVO=orderService.getStatistics();
return Result.success(orderStatisticsVO);
}
@PutMapping("/complete/{id}")
public Result completeOrder(@PathVariable Long id){
    orderService.completeOrder(id);
    return Result.success();
}

@PutMapping("delivery/{id}")
    public Result deliverOrder(@PathVariable Long id){
    orderService.deliverOrder(id);
    return Result.success();
}


}
