package com.itheima.mapper;

import com.itheima.pojo.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrdersMapper {

    @Insert("INSERT INTO orders (order_no, user_id, product_name, product_count, total_amount, order_status, create_time, pay_time) " +
            "VALUES (#{orderNo}, #{userId}, #{productName}, #{productCount}, #{totalAmount}, #{orderStatus}, #{createTime}, #{payTime})")
    int insert(Orders orders);

    @Select("SELECT * FROM orders WHERE id = #{id}")
    Orders findById(Long id);

    @Select("SELECT * FROM orders WHERE user_id = #{userId}")
    List<Orders> findByUserId(Integer userId);

    @Select("SELECT COUNT(*) FROM orders")
    Long count();

    @Select("SELECT COALESCE(MAX(id), 0) FROM orders")
    Integer findMaxId();
}
