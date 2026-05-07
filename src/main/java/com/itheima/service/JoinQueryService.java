package com.itheima.service;

import com.itheima.pojo.AgeOrderStat;
import com.itheima.pojo.UserOrderDetail;
import com.itheima.pojo.UserOrderStat;

import java.util.List;

public interface JoinQueryService {

    List<UserOrderStat> findUserOrderStats(Integer limit);

    List<UserOrderStat> findUserOrderStatsByIdRange(Integer beginId, Integer endId);

    List<UserOrderDetail> findUserOrderDetails(Integer userId);

    List<AgeOrderStat> findOrderStatsByAge();

    List<UserOrderStat> findTopSpenders(Integer limit);

    Long countUsersWithOrders();

    Long sumTotalOrderAmount();
}
