package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderDetail {
    private Integer userId;
    private String username;
    private Long orderId;
    private String orderNo;
    private String productName;
    private BigDecimal totalAmount;
}
