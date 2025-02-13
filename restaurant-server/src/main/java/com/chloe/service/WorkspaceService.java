package com.chloe.service;

import com.chloe.vo.BusinessDataVO;
import com.chloe.vo.DishOverViewVO;
import com.chloe.vo.OrderOverViewVO;
import com.chloe.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {
    BusinessDataVO getBusinessData(LocalDateTime bein,LocalDateTime end);

    SetmealOverViewVO getOverviewSetmeal();

    DishOverViewVO getOverviewDish();

    OrderOverViewVO getOverviewOrder();
}
