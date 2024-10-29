package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        //1.处理业务异常（地址簿为空、购物车数据为空）
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        //查一下带过来的用户的购物车里有没有内容，没内容就抛出
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list == null || list.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //2.向订单表插入一条数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setStatus(Orders.PENDING_PAYMENT);
        //使用时间戳作为订单号
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(userId);
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());

        orderMapper.insert(orders);
//3.向订单明细表插入多条数据
        List<OrderDetail> orderDetailList=new ArrayList<>();
        for (ShoppingCart cart : list) {
            OrderDetail orderDetail=new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetailList);
//4.清空购物车
        shoppingCartMapper.deleteByUserId(userId);
        //5.封装VO对象
    OrderSubmitVO orderSubmitVO= OrderSubmitVO.builder()
            .id(orders.getId())
            .orderTime(orders.getOrderTime())
            .orderNumber(orders.getNumber())
            .orderAmount(orders.getAmount())
            .build();
        return orderSubmitVO;
    }

    @Override
    public void cancelOrder(OrdersCancelDTO ordersCancelDTO) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(ordersCancelDTO.getId());

        //支付状态
        Integer payStatus = ordersDB.getPayStatus();
        if (payStatus == Orders.PAID) {
            //用户已支付，需要退款
//            String refund = weChatPayUtil.refund(
//                    ordersDB.getNumber(),
//                    ordersDB.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
            log.info("申请退款,但是没做支付功能哈，所以没有内容");
        }

        //更新订单表的状态： 订单id, cancelReason
        //1. status, 2. cancelResason
        Orders orders =new Orders();
        BeanUtils.copyProperties(ordersCancelDTO,orders);
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
        //此处还应该有退款语句啊，但是没做支付模块，就不管他了

    }

    @Override
    public OrderVO getById(Long id) {

        //查Order表
        Orders orders= orderMapper.getById(id);
        //查order detail 表
        //可以多表联查哈 不能多表联查，因为返回的是LIST
        Long ordersId = orders.getId();
        List<OrderDetail> list= orderDetailMapper.getByOrdersId(ordersId);
        //得到订单菜品信息
        StringBuilder sb=new StringBuilder();
        for (OrderDetail orderDetail : list) {
            sb.append(orderDetail.getName()+"*"+orderDetail.getNumber()+";");
        }
        String orderDishes = sb.toString();

        //把上面三个搞到要传递的VO类里去
        OrderVO orderVO=new OrderVO();
BeanUtils.copyProperties(orders,orderVO);
orderVO.setOrderDetailList(list);
orderVO.setOrderDishes(orderDishes);
        return orderVO;
    }

    @Override
    public PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
Page<Orders> page=orderMapper.pageQuery(ordersPageQueryDTO);
// 此处要额外传一个orderDishes，而且还要搭配list，所以result和总条数拆开
        List<OrderVO> list=new ArrayList<>();
        List<Orders> orders = page.getResult();
        if(!CollectionUtils.isEmpty(orders)){
            for (Orders order : orders) {
                //将每一条orders复制到orderVO
                OrderVO orderVO=new OrderVO();
                BeanUtils.copyProperties(order,orderVO);
       //增加orderDishes信息
                Long ordersId = order.getId();
                List<OrderDetail> detailsLists= orderDetailMapper.getByOrdersId(ordersId);
                //得到订单菜品信息
                StringBuilder sb=new StringBuilder();
                for (OrderDetail detailsList : detailsLists) {
                    sb.append(detailsList.getName()+"*"+detailsList.getNumber()+";");
                }
                String orderDishes = sb.toString();
                orderVO.setOrderDishes(orderDishes);

                //加到list中
                list.add(orderVO);
            }
        }


        return new PageResult(page.getTotal(),list);
    }

    @Override
    public void confirmOrder( OrdersConfirmDTO ordersConfirmDTO) {
        //更新订单表的状态： 订单id, cancelReason
        //1. status, 2. cancelResason
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersConfirmDTO,orders);

        orders.setStatus(Orders.CONFIRMED);


        orderMapper.update(orders);

    }

    @Override
    public void rejectOrder(OrdersRejectionDTO ordersRejectionDTO) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(ordersRejectionDTO.getId());

        // 订单只有存在且状态为2（待接单）才可以拒单
        if (ordersDB == null || !ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        //支付状态
        Integer payStatus = ordersDB.getPayStatus();
        if (payStatus == Orders.PAID) {
//            //用户已支付，需要退款
//            String refund = weChatPayUtil.refund(
//                    ordersDB.getNumber(),
//                    ordersDB.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
            log.info("申请退款,但是没做支付功能哈，所以没有内容");
        }
        //此处还应该有退款语句啊，但是没做支付模块，就不管他了
        //更新订单表的状态： 订单id, cancelReason
        //1. status, 2. cancelResason
        Orders orders =new Orders();
        BeanUtils.copyProperties(ordersRejectionDTO,orders);
        orders.setStatus(Orders.CANCELLED);
//        orders.setId(ordersRejectionDTO.getId());
//        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        orders.setCancelTime(LocalDateTime.now());

        orderMapper.update(orders);

    }

    @Override
    public OrderStatisticsVO getStatistics() {
    Integer confirmed=  orderMapper.getStatistics(Orders.CONFIRMED);
    Integer deliveryInProgress=  orderMapper.getStatistics(Orders.DELIVERY_IN_PROGRESS);
    Integer toBeConfirmed=  orderMapper.getStatistics(Orders.TO_BE_CONFIRMED);
   OrderStatisticsVO orderStatisticsVO= new OrderStatisticsVO();
   orderStatisticsVO.setConfirmed(confirmed);
   orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
   orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);

        return orderStatisticsVO;
    }

    @Override
    public void completeOrder(Long id) {
      Orders orders=  Orders.builder().id(id).build();
        orders.setStatus(Orders.COMPLETED);
        orders.setDeliveryTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

}
