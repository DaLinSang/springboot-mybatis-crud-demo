package com.itheima.controller;

import com.itheima.pojo.Orders;
import com.itheima.pojo.Result;
import com.itheima.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/generate")
    public Result generateOrders() {
        log.info("开始生成订单数据");
        long begin = System.currentTimeMillis();
        ordersService.batchInsertOrders(500000);
        long end = System.currentTimeMillis();
        log.info("生成订单数据完成，耗时：{} 毫秒", end - begin);
        return Result.success("生成订单数据完成，共耗时：" + (end - begin) + " 毫秒");
    }

    @GetMapping("/id/{id}")
    public Result findById(@PathVariable Long id) {
        log.info("根据ID查询订单：id={}", id);
        long begin = System.currentTimeMillis();
        Orders order = ordersService.findById(id);
        long end = System.currentTimeMillis();
        log.info("查询耗时：{} 毫秒", end - begin);
        return Result.success(order);
    }

    @GetMapping("/userId/{userId}")
    public Result findByUserId(@PathVariable Integer userId) {
        log.info("根据用户ID查询订单：userId={}", userId);
        long begin = System.currentTimeMillis();
        List<Orders> orders = ordersService.findByUserId(userId);
        long end = System.currentTimeMillis();
        log.info("查询耗时：{} 毫秒", end - begin);
        return Result.success(orders);
    }

    @GetMapping("/count")
    public Result count() {
        log.info("查询订单总数");
        long begin = System.currentTimeMillis();
        Long count = ordersService.count();
        long end = System.currentTimeMillis();
        log.info("查询耗时：{} 毫秒", end - begin);
        return Result.success(count);
    }
}
