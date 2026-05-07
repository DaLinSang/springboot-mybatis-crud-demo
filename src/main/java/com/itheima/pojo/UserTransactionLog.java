package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTransactionLog {
    private Long id;               // 自增主键，插入时不需要赋值
    private Integer userId;
    private String username;
    private Integer age;
    private String email;
    private LocalDate registrationDate;
    private LocalDateTime lastLoginTime;
    private Boolean isActive;
    private BigDecimal accountBalance;
    private Integer totalPoints;
    private String phone;
    private String address;
    private String gender;          // 用 String 代替 ENUM 便于批量操作
    private LocalDate birthday;
    private Integer membershipLevel;
    private Integer referrerId;
    private Integer status;
    private String remark;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;

    // 省略 getter/setter (实际请使用 Lombok 或手动生成)
}