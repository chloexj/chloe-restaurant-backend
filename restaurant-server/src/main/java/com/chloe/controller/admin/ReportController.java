package com.chloe.controller.admin;

import com.chloe.result.Result;
import com.chloe.service.ReportService;
import com.chloe.vo.OrderReportVO;
import com.chloe.vo.SalesTop10ReportVO;
import com.chloe.vo.TurnoverReportVO;
import com.chloe.vo.UserReportVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Api(tags = "Statistics related api")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;
@GetMapping("/turnoverStatistics")
public Result<TurnoverReportVO> turnoverStatistics(
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){

log.info("Turnover statistics:{},{}",begin,end);
    return Result.success(reportService.getTurnoverStatistics(begin,end));
}
@GetMapping("/userStatistics")
public Result<UserReportVO> userStatistics( @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
    log.info("user statistics:{},{}",begin,end);

    return Result.success(reportService.getuserStatistics(begin,end));
    }

    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> ordersStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("orders statistics:{},{}",begin,end);

        return Result.success(reportService.getOrdersStatistics(begin,end));
    }

    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> topTen(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("top 10 statistics:{},{}",begin,end);

        return Result.success(reportService.getTop10Statistics(begin,end));
    }

    //数据导出
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            reportService.exportBusinessData(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
