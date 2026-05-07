package com.itheima.mapper;

import com.itheima.pojo.AgeOrderStat;
import com.itheima.pojo.UserOrderDetail;
import com.itheima.pojo.UserOrderStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JoinQueryMapper {

    @Select("SELECT u.id as userId, u.username, u.age, COUNT(o.id) as orderCount, " +
            "COALESCE(SUM(o.total_amount), 0) as totalSpent " +
            "FROM user_transaction_log u " +
            "INNER JOIN orders o ON u.id = o.user_id " +
            "GROUP BY u.id, u.username, u.age " +
            "ORDER BY orderCount DESC " +
            "LIMIT #{limit}")
    List<UserOrderStat> findUserOrderStats(@Param("limit") Integer limit);

    @Select("SELECT u.id as userId, u.username, u.age, COUNT(o.id) as orderCount, " +
            "COALESCE(SUM(o.total_amount), 0) as totalSpent " +
            "FROM user_transaction_log u " +
            "LEFT JOIN orders o ON u.id = o.user_id " +
            "WHERE u.id BETWEEN #{beginId} AND #{endId} " +
            "GROUP BY u.id, u.username, u.age")
    List<UserOrderStat> findUserOrderStatsByIdRange(@Param("beginId") Integer beginId, @Param("endId") Integer endId);

    @Select("SELECT u.id as userId, u.username, o.id as orderId, o.order_no as orderNo, " +
            "o.product_name as productName, o.total_amount as totalAmount " +
            "FROM user_transaction_log u " +
            "INNER JOIN orders o ON u.id = o.user_id " +
            "WHERE u.id = #{userId} " +
            "ORDER BY o.create_time DESC")
    List<UserOrderDetail> findUserOrderDetails(@Param("userId") Integer userId);

    @Select("SELECT u.age, COUNT(o.id) as orderCount, AVG(o.total_amount) as avgAmount, " +
            "SUM(o.total_amount) as totalAmount " +
            "FROM user_transaction_log u " +
            "LEFT JOIN orders o ON u.id = o.user_id " +
            "GROUP BY u.age " +
            "ORDER BY u.age")
    List<AgeOrderStat> findOrderStatsByAge();

    @Select("SELECT u.id as userId, u.username, u.age, COUNT(o.id) as orderCount, " +
            "COALESCE(SUM(o.total_amount), 0) as totalSpent " +
            "FROM user_transaction_log u " +
            "LEFT JOIN orders o ON u.id = o.user_id " +
            "GROUP BY u.id, u.username, u.age " +
            "ORDER BY totalSpent DESC " +
            "LIMIT #{limit}")
    List<UserOrderStat> findTopSpenders(@Param("limit") Integer limit);

    @Select("SELECT COUNT(*) FROM user_transaction_log u INNER JOIN orders o ON u.id = o.user_id")
    Long countUsersWithOrders();

    @Select("SELECT COALESCE(SUM(o.total_amount), 0) FROM orders o")
    Long sumTotalOrderAmount();
}
