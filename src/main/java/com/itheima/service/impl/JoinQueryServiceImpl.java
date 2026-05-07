package com.itheima.service.impl;

import com.itheima.mapper.JoinQueryMapper;
import com.itheima.pojo.AgeOrderStat;
import com.itheima.pojo.UserOrderDetail;
import com.itheima.pojo.UserOrderStat;
import com.itheima.service.JoinQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinQueryServiceImpl implements JoinQueryService {

    @Autowired
    private JoinQueryMapper joinQueryMapper;

    @Override
    public List<UserOrderStat> findUserOrderStats(Integer limit) {
        return joinQueryMapper.findUserOrderStats(limit);
    }

    @Override
    public List<UserOrderStat> findUserOrderStatsByIdRange(Integer beginId, Integer endId) {
        return joinQueryMapper.findUserOrderStatsByIdRange(beginId, endId);
    }

    @Override
    public List<UserOrderDetail> findUserOrderDetails(Integer userId) {
        return joinQueryMapper.findUserOrderDetails(userId);
    }

    @Override
    public List<AgeOrderStat> findOrderStatsByAge() {
        return joinQueryMapper.findOrderStatsByAge();
    }

    @Override
    public List<UserOrderStat> findTopSpenders(Integer limit) {
        return joinQueryMapper.findTopSpenders(limit);
    }

    @Override
    public Long countUsersWithOrders() {
        return joinQueryMapper.countUsersWithOrders();
    }

    @Override
    public Long sumTotalOrderAmount() {
        return joinQueryMapper.sumTotalOrderAmount();
    }
}
