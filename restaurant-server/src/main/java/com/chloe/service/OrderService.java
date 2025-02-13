package com.chloe.service;

import com.chloe.dto.*;
import com.chloe.result.PageResult;
import com.chloe.vo.OrderStatisticsVO;
import com.chloe.vo.OrderSubmitVO;
import com.chloe.vo.OrderVO;

public interface OrderService {
   PageResult pageQueryUser(Integer page, Integer pageSize, Integer status);

    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    void cancelOrder(OrdersCancelDTO ordersCancelDTO);

    OrderVO getById(Long id);

    PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    void confirmOrder( OrdersConfirmDTO ordersConfirmDTO);

    void rejectOrder(OrdersRejectionDTO ordersRejectionDTO);

    OrderStatisticsVO getStatistics();

    void completeOrder(Long id);

    void deliverOrder(Long id);

 void cancelOrder4User(Long id);

 void repeatOrder(Long id);

    void reminder(Long id);
}
