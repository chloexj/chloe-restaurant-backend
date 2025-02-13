package com.chloe.mapper;

import com.github.pagehelper.Page;
import com.chloe.dto.GoodsSalesDTO;
import com.chloe.dto.OrdersPageQueryDTO;
import com.chloe.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    void update(Orders orders);
@Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
@Select("select count(id) from orders where status=#{status}")
    Integer getStatistics(Integer status);
@Select("select * from orders where status=#{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);

    Double sumByMap(Map map);

    Integer countByMap(Map map);

    List<GoodsSalesDTO> getTop10(LocalDateTime begin, LocalDateTime end);

@Select("select count(id) from orders where status=#{status}")
    Integer getStatus(Integer status);
}
