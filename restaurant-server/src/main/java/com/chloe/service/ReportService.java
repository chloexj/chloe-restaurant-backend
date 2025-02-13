package com.chloe.service;

import com.chloe.vo.OrderReportVO;
import com.chloe.vo.SalesTop10ReportVO;
import com.chloe.vo.TurnoverReportVO;
import com.chloe.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnoverStatistics(LocalDate begin,LocalDate end);

    UserReportVO getuserStatistics(LocalDate begin, LocalDate end);

   OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getTop10Statistics(LocalDate begin, LocalDate end);

    void exportBusinessData(HttpServletResponse response) throws IOException;
}
