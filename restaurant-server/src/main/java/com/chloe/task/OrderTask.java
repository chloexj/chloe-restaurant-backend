package com.chloe.task;

import com.chloe.entity.Orders;
import com.chloe.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;


       @Scheduled(cron = "0 * * * * ? ")
// @Scheduled(cron="0/10 * * * * ? ") 测试使用
    public void processTimeoutOrder(){
        log.info("Process time out order,{}", LocalDateTime.now());
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> list= orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT,time);
        if(list!=null&&list.size()>0){
            for (Orders orders : list) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("Time out, cancel automatically");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron="0/30 * * * * ? ") 测试使用
    public void processDeliveryOrder(){
        log.info("everyday 1 am, process in delivery order");
        List<Orders> list = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().plusHours(-1));
        if(list!=null&&list.size()>0){
            for (Orders orders : list) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }
        }
    }
}
