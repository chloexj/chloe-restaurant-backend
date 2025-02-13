package com.chloe.service.impl;

import com.chloe.entity.Orders;
import com.chloe.mapper.DishMapper;
import com.chloe.mapper.OrderMapper;
import com.chloe.mapper.SetMapper;
import com.chloe.mapper.UserMapper;
import com.chloe.service.WorkspaceService;
import com.chloe.vo.BusinessDataVO;
import com.chloe.vo.DishOverViewVO;
import com.chloe.vo.OrderOverViewVO;
import com.chloe.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
@Autowired
private OrderMapper orderMapper;
@Autowired
private UserMapper userMapper;
@Autowired
private SetMapper setMapper;
@Autowired
private DishMapper dishMapper;
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin,LocalDateTime end) {

        Map map=new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        //new users
        Integer newUser = userMapper.countByMap(map);
//order completion rate
        Integer totalOrder = orderMapper.countByMap(map);
        map.put("status", Orders.COMPLETED);
        Integer validOrder = orderMapper.countByMap(map);
        Double orderCompletionRate=0.0;
        if(validOrder!=0){
            orderCompletionRate=validOrder.doubleValue()/totalOrder;
        }
        //turnover
        Double turnover = 0.0;
        Double turnoverPre=orderMapper.sumByMap(map);
        if(turnoverPre!=null){
            turnover = turnoverPre;
        }
        //unit price
        Double unitPrice=0.0;
        if(turnover!=0) {
           unitPrice = turnover / validOrder;
        }
        return BusinessDataVO.builder().newUsers(newUser).orderCompletionRate(orderCompletionRate)
                .turnover(turnover).unitPrice(unitPrice).validOrderCount(validOrder).build();
    }

    @Override
    public SetmealOverViewVO getOverviewSetmeal() {
       Integer onSale= setMapper.getStatus(1);
       Integer notOnSale= setMapper.getStatus(0);
        return SetmealOverViewVO.builder().sold(onSale).discontinued(notOnSale).build();
    }

    @Override
    public DishOverViewVO getOverviewDish() {
        Integer onSale= dishMapper.getStatus(1);
        Integer notOnSale= dishMapper.getStatus(0);
        return DishOverViewVO.builder().sold(onSale).discontinued(notOnSale).build();
    }

    @Override
    public OrderOverViewVO getOverviewOrder() {
        //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款
        LocalDate date = LocalDate.now();
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
        LocalDateTime begin = LocalDateTime.of(date, LocalTime.MIN);
        Map map=new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        Integer totalOrder = orderMapper.countByMap(map);
        map.put("status", Orders.CANCELLED);
        Integer cancelOrder = orderMapper.countByMap(map);
map.replace("status",Orders.COMPLETED);
        Integer completeOrder = orderMapper.countByMap(map);
        map.replace("status",Orders.DELIVERY_IN_PROGRESS);
        Integer deliveredOrder = orderMapper.countByMap(map);
        map.replace("status",Orders.TO_BE_CONFIRMED);
        Integer waitingOrder = orderMapper.countByMap(map);

        return OrderOverViewVO.builder().allOrders(totalOrder).cancelledOrders(cancelOrder)
                .completedOrders(completeOrder).deliveredOrders(deliveredOrder).waitingOrders(waitingOrder).build();
    }
}
