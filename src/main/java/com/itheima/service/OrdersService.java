package com.itheima.service;

import com.itheima.pojo.Orders;

import java.util.List;

public interface OrdersService {

    void batchInsertOrders(int total);

    Orders findById(Long id);

    List<Orders> findByUserId(Integer userId);

    Long count();

    Long sumTotalAmount();
}
