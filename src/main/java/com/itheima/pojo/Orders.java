package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private Long id;
    private String orderNo;
    private Integer userId;
    private String productName;
    private Integer productCount;
    private BigDecimal totalAmount;
    private Integer orderStatus;
    private LocalDateTime createTime;
    private LocalDateTime payTime;
}
