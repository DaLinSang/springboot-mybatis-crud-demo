package com.itheima.service;

import com.itheima.pojo.PageBean;
import com.itheima.pojo.AgeCount;
import com.itheima.pojo.UserTransactionLog;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

//@Service
public interface UserTransactionLogService {

    void batchInsertWithExecutor(int total);

    void insert(UserTransactionLog userTransactionLog);

    UserTransactionLog findById(Integer id);


    List<UserTransactionLog> findByAge(Integer age);

    PageBean page(Integer page, Integer pageSize, LocalDate begin, LocalDate end);

    Long count();

    List<AgeCount> countByAge();

    // 追加数据（查询最大ID后继续插入）
    void batchInsertAppend(Integer total);

    // 基于主键的游标分页查询
    List<UserTransactionLog> pageByCursor(Integer lastMaxId, Integer pageSize);

    // 基于主键的按页码分页查询
    List<UserTransactionLog> pageById(Integer page, Integer pageSize);

    // 普通OFFSET分页查询
    List<UserTransactionLog> pageByOffset(Integer page, Integer pageSize);

    // 统计总积分
    Long sumTotalPoints();

    // 按手机号查询
    List<UserTransactionLog> findByPhone(String phone);

    // 按用户名查询
    List<UserTransactionLog> findByUsername(String username);

    // 统计账户平均值
    Long avgAccount();

}
