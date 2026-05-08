package com.itheima.controller;

import com.itheima.pojo.AgeOrderStat;
import com.itheima.pojo.Result;
import com.itheima.pojo.UserOrderDetail;
import com.itheima.pojo.UserOrderStat;
import com.itheima.service.JoinQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/test/join")
public class JoinQueryController {

    @Autowired
    private JoinQueryService joinQueryService;

    @GetMapping("/userOrderStats")
    public Result findUserOrderStats(@RequestParam(defaultValue = "20") Integer limit) {
        long begin = System.currentTimeMillis();
        log.info("查询用户订单统计，前{}条", limit);
        log.info("这一条是为了测试Git123456用的");
        log.info("这一条是为了测试Git123456fix用的");
        log.info("这一条是为了测试Git master 用的");
        List<UserOrderStat> list = joinQueryService.findUserOrderStats(limit);
        long end = System.currentTimeMillis();
        log.info("查询耗时：{} 毫秒", end - begin);
        return Result.success(list);
    }

    @GetMapping("/userOrderStats/range/{beginId}/{endId}")
    public Result findUserOrderStatsByIdRange(@PathVariable Integer beginId, @PathVariable Integer endId) {
        long begin = System.currentTimeMillis();
        log.info("查询用户订单统计，ID范围：{}-{}", beginId, endId);
        List<UserOrderStat> list = joinQueryService.findUserOrderStatsByIdRange(beginId, endId);
        long end = System.currentTimeMillis();
        log.info("查询耗时：{} 毫秒", end - begin);
        return Result.success(list);
    }

    @GetMapping("/user/{userId}/orders")
    public Result findUserOrderDetails(@PathVariable Integer userId) {
        long begin = System.currentTimeMillis();
        log.info("查询用户订单详情，userId={}", userId);
        List<UserOrderDetail> list = joinQueryService.findUserOrderDetails(userId);
        long end = System.currentTimeMillis();
        log.info("查询耗时：{} 毫秒", end - begin);
        return Result.success(list);
    }

    @GetMapping("/stats/byAge")
    public Result findOrderStatsByAge() {
        long begin = System.currentTimeMillis();
        log.info("按年龄段统计订单");
        List<AgeOrderStat> list = joinQueryService.findOrderStatsByAge();
        long end = System.currentTimeMillis();
        log.info("查询耗时：{} 毫秒", end - begin);
        return Result.success(list);
    }

    @GetMapping("/topSpenders")
    public Result findTopSpenders(@RequestParam(defaultValue = "20") Integer limit) {
        long begin = System.currentTimeMillis();
        log.info("查询消费总额前{}名", limit);
        List<UserOrderStat> list = joinQueryService.findTopSpenders(limit);
        long end = System.currentTimeMillis();
        log.info("查询耗时：{} 毫秒", end - begin);
        return Result.success(list);
    }

    @GetMapping("/summary")
    public Result getSummary() {
        long begin = System.currentTimeMillis();
        log.info("查询多表联查汇总统计");
        Long usersWithOrders = joinQueryService.countUsersWithOrders();
        Long totalOrderAmount = joinQueryService.sumTotalOrderAmount();
        long end = System.currentTimeMillis();

        Map<String, Object> summary = new HashMap<>();
        summary.put("usersWithOrders", usersWithOrders);
        summary.put("totalOrderAmount", totalOrderAmount);
        summary.put("queryTimeMs", end - begin);

        log.info("查询耗时：{} 毫秒", end - begin);
        return Result.success(summary);
    }
}
