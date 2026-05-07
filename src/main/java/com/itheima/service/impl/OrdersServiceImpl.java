package com.itheima.service.impl;

import com.itheima.mapper.OrdersMapper;
import com.itheima.pojo.Orders;
import com.itheima.service.OrdersService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private OrdersMapper ordersMapper;

    private final Random random = new Random();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final String[] productNames = {
            "iPhone 15 Pro Max", "MacBook Pro 16寸", "iPad Pro 12.9寸",
            "AirPods Pro 2", "Apple Watch Ultra", "华为Mate60 Pro",
            "小米14 Ultra", "三星S24 Ultra", "OPPO Find X7",
            "vivo X100 Pro", "Nintendo Switch", "PS5游戏机",
            "Xbox Series X", "DJI Mavic 3", "大疆无人机",
            "戴森吸尘器", "海尔冰箱", "格力空调", "小米电视"
    };

    @Override
    public void batchInsertOrders(int total) {
        Integer maxId = ordersMapper.findMaxId();
        int startId = maxId + 1;
        System.out.println("当前最大订单ID: " + maxId + "，开始生成订单数据，从 " + startId + " 开始");

        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            ordersMapper = sqlSession.getMapper(OrdersMapper.class);
            int batchSize = 1000;

            for (int i = 1; i <= total; i++) {
                Orders order = generateRandomOrder(startId + i - 1);
                ordersMapper.insert(order);

                if (i % batchSize == 0) {
                    sqlSession.flushStatements();
                    sqlSession.commit();
                    sqlSession.clearCache();
                    System.out.println("已生成 " + i + " 条订单数据");
                }
            }
            sqlSession.flushStatements();
            sqlSession.commit();
        }
        System.out.println("批量生成 " + total + " 条订单数据完成！");
    }

    private Orders generateRandomOrder(int seq) {
        Orders order = new Orders();
        order.setOrderNo(generateOrderNo(seq));
        order.setUserId(1 + random.nextInt(500000));
        order.setProductName(productNames[random.nextInt(productNames.length)]);
        order.setProductCount(1 + random.nextInt(10));
        order.setTotalAmount(BigDecimal.valueOf(random.nextDouble() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP));
        order.setOrderStatus(random.nextInt(3));
        order.setCreateTime(LocalDateTime.now().minusDays(random.nextInt(365)));
        order.setPayTime(order.getOrderStatus() > 0 ?
                order.getCreateTime().plusHours(random.nextInt(48)) : null);
        return order;
    }

    private String generateOrderNo(int seq) {
        return "ORD" + LocalDateTime.now().format(formatter) + String.format("%06d", seq);
    }

    @Override
    public Orders findById(Long id) {
        return ordersMapper.findById(id);
    }

    @Override
    public List<Orders> findByUserId(Integer userId) {
        return ordersMapper.findByUserId(userId);
    }

    @Override
    public Long count() {
        return ordersMapper.count();
    }

    @Override
    public Long sumTotalAmount() {
        return null;
    }
}
