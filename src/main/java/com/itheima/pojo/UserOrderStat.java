package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderStat {
    private Integer userId;
    private String username;
    private Integer age;
    private Long orderCount;
    private BigDecimal totalSpent;
}
