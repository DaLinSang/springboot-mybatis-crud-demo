package com.itheima.mapper;


import com.itheima.pojo.AgeCount;
import com.itheima.pojo.UserTransactionLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserTransactionLogMapper {


    // insertSingle(UserTransactionLog userTransactionLog);
    // 单条插入（供批量执行器调用）
    int insert(UserTransactionLog log);

    @Select("select * from user_transaction_log where id = #{id}")
    UserTransactionLog findById(Integer id);

    @Select("select * from user_transaction_log where age = #{age}")
    List<UserTransactionLog> findByAge(Integer age);

    //分页查询，写在xml里面
    List<UserTransactionLog> list(LocalDate begin, LocalDate end);

    @Select("SELECT COUNT(*) FROM user_transaction_log")
    Long count();

    // 查询最大ID
    @Select("SELECT COALESCE(MAX(id), 0) FROM user_transaction_log")
    Integer findMaxId();

    // 按年龄分组统计
    @Select("SELECT age, COUNT(*) as count FROM user_transaction_log GROUP BY age ORDER BY count DESC")
    List<AgeCount> countByAge();

    // 统计总积分
    @Select("SELECT COALESCE(SUM(total_points), 0) FROM user_transaction_log")
    Long sumTotalPoints();

    // 统计账户平均值
    @Select("SELECT AVG(account_balance) FROM user_transaction_log")
    Long avgAccount();

    // 基于主键的游标分页查询（性能优于OFFSET分页）
    @Select("SELECT * FROM user_transaction_log WHERE id > #{lastMaxId} ORDER BY id LIMIT #{pageSize}")
    List<UserTransactionLog> listByCursor(@Param("lastMaxId") Integer lastMaxId, @Param("pageSize") Integer pageSize);

    // 基于主键的按页码分页查询（ID连续时性能稳定）
    @Select("SELECT * FROM user_transaction_log WHERE id > ((${page} - 1) * #{pageSize}) ORDER BY id LIMIT #{pageSize}")
    List<UserTransactionLog> listByPage(@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    // 普通OFFSET分页查询
    @Select("SELECT * FROM user_transaction_log ORDER BY id LIMIT #{offset}, #{pageSize}")
    List<UserTransactionLog> listByOffset(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    // 按手机号查询
    @Select("SELECT * FROM user_transaction_log WHERE phone = #{phone}")
    List<UserTransactionLog> findByPhone(@Param("phone") String phone);

    // 按用户名查询
    @Select("SELECT * FROM user_transaction_log WHERE username = #{username}")
    List<UserTransactionLog> findByUsername(@Param("username") String username);



//    @Select("select * from user_transaction_log where age = #{age}")
//    UserTransactionLog findByAge(Integer age);

    // 可选：一次性插入多条（另一种方式，但不推荐大数据量）
    //int batchInsert(@Param("list") List<UserTransactionLog> list);
}