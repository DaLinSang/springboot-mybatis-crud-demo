package com.itheima.controller;


import com.itheima.pojo.AgeCount;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.Result;
import com.itheima.pojo.UserTransactionLog;
import com.itheima.service.UserTransactionLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/test")
public class UserTransactionLogController {

    @Autowired
    private UserTransactionLogService userTransactionLogService;

    //初始时往数据库批量插入数据操作
    @GetMapping("/insertByBatch")
    public Result insertByPatch(){
        log.info("开始插入数据");
        Long begin = System.currentTimeMillis();
        userTransactionLogService.batchInsertWithExecutor(10000);  // 初始插入50万条
        Long end = System.currentTimeMillis();
        log.info("插入完成，程序退出，用时：{} 毫秒",end-begin);
        return Result.success();
    }

    //追加数据（自动查询最大ID后继续插入）
    @GetMapping("/insertAppend/{addNum}")
    public Result insertAppend(@PathVariable Integer addNum){
        log.info("开始追加数据");
        Long begin = System.currentTimeMillis();
        userTransactionLogService.batchInsertAppend(addNum);  // 追加50万条
        Long end = System.currentTimeMillis();
        log.info("追加完成，程序退出，用时：{} 毫秒",end-begin);
        return Result.success();
    }

    //增加人员信息
    @GetMapping("/insertSingle")
    public Result insert(@RequestBody UserTransactionLog userTransactionLog){
        log.info("新增人员：{}",userTransactionLog);
        userTransactionLogService.insert(userTransactionLog);
        return Result.success();
    }

    //根据id查询员工信息
    @GetMapping("/id/{id}")
    public Result findById(@PathVariable Integer id){
        log.info("根据id查询：id:{}",id);
        Long begin = System.currentTimeMillis();
        UserTransactionLog userTransactionLog = userTransactionLogService.findById(id);
        Long end = System.currentTimeMillis();
        log.info("查询用时：{} 毫秒",end-begin);
        return Result.success(userTransactionLog);
    }

    //根据age查询员工信息
    @GetMapping("/age/{age}")
    public Result findByAge(@PathVariable Integer age){
        log.info("根据age查询：age:{}",age);
        Long begin = System.currentTimeMillis();
        List<UserTransactionLog> userList = userTransactionLogService.findByAge(age);
        Long end = System.currentTimeMillis();
        log.info("查询用时：{} 毫秒",end-begin);
        return Result.success(userList);
    }

    //日期分页查询数据
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        Long runBegin = System.currentTimeMillis();
        log.info("分页查询，参数：{},{},{},{}",page,pageSize,begin,end);
        PageBean pageBean = userTransactionLogService.page(page,pageSize,begin,end);
        Long runEnd = System.currentTimeMillis();
        log.info("查询用时：{} 毫秒",runEnd-runBegin);
        return Result.success(pageBean);
    }

    //基于主键的游标分页查询（适合深度分页）
    @GetMapping("/pageByCursor")
    public Result pageByCursor(@RequestParam(defaultValue = "0") Integer lastMaxId,
                               @RequestParam(defaultValue = "10") Integer pageSize){
        Long runBegin = System.currentTimeMillis();
        log.info("游标分页查询，参数：lastMaxId={}, pageSize={}", lastMaxId, pageSize);
        List<UserTransactionLog> list = userTransactionLogService.pageByCursor(lastMaxId, pageSize);
        Long runEnd = System.currentTimeMillis();
        log.info("查询用时：{} 毫秒",runEnd-runBegin);
        return Result.success(list);
    }

    //基于主键的按页码分页查询
    @GetMapping("/pageById")
    public Result pageById(@RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer pageSize){
        Long runBegin = System.currentTimeMillis();
        log.info("按页码分页查询，参数：page={}, pageSize={}", page, pageSize);
        List<UserTransactionLog> list = userTransactionLogService.pageById(page, pageSize);
        Long runEnd = System.currentTimeMillis();
        log.info("查询用时：{} 毫秒",runEnd-runBegin);
        return Result.success(list);
    }

    //普通OFFSET分页查询
    @GetMapping("/pageByOffset")
    public Result pageByOffset(@RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize){
        Long runBegin = System.currentTimeMillis();
        log.info("普通分页查询，参数：page={}, pageSize={}", page, pageSize);
        List<UserTransactionLog> list = userTransactionLogService.pageByOffset(page, pageSize);
        Long runEnd = System.currentTimeMillis();
        log.info("查询用时：{} 毫秒",runEnd-runBegin);
        return Result.success(list);
    }

    //聚合查询总记录数
    @GetMapping("/count")
    public Result count(){
        log.info("聚合查询");
        Long runBegin = System.currentTimeMillis();
        Long count = userTransactionLogService.count();
        Long runEnd = System.currentTimeMillis();
        log.info("聚合查询用时：{} 毫秒",runEnd-runBegin);
        return Result.success(count);
    }

    //按年龄分组统计记录数
    @GetMapping("/countByAge")
    public Result countByAge(){
        log.info("按年龄分组统计记录数");
        Long runBegin = System.currentTimeMillis();
        List<AgeCount> ageCountList = userTransactionLogService.countByAge();
        Long runEnd = System.currentTimeMillis();
        log.info("按年龄分组统计记录数用时：{} 毫秒",runEnd-runBegin);
        return Result.success(ageCountList);
    }

    //统计总积分
    @GetMapping("/sumTotalPoints")
    public Result sumTotalPoints(){
        log.info("统计总积分");
        Long runBegin = System.currentTimeMillis();
        Long totalPoints = userTransactionLogService.sumTotalPoints();
        Long runEnd = System.currentTimeMillis();
        log.info("统计总积分用时：{} 毫秒",runEnd-runBegin);
        return Result.success(totalPoints);
    }

    //按手机号查询
    @GetMapping("/phone/{phone}")
    public Result findByPhone(@PathVariable String phone){
        log.info("按手机号查询：phone={}", phone);
        Long runBegin = System.currentTimeMillis();
        List<UserTransactionLog> list = userTransactionLogService.findByPhone(phone);
        Long runEnd = System.currentTimeMillis();
        log.info("按手机号查询用时：{} 毫秒",runEnd-runBegin);
        return Result.success(list);
    }

    //按用户名查询
    @GetMapping("/username/{username}")
    public Result findByUsername(@PathVariable String username){
        log.info("按用户名查询：username={}", username);
        Long runBegin = System.currentTimeMillis();
        List<UserTransactionLog> list = userTransactionLogService.findByUsername(username);
        Long runEnd = System.currentTimeMillis();
        log.info("按用户名查询用时：{} 毫秒",runEnd-runBegin);
        return Result.success(list);
    }

    //统计账户平均值
    @GetMapping("/avgAccount")
    public Result avgAccount(){
        log.info("统计账户平均值");
        Long runBegin = System.currentTimeMillis();
        Long avg = userTransactionLogService.avgAccount();
        Long runEnd = System.currentTimeMillis();
        log.info("统计账户平均值用时：{} 毫秒",runEnd-runBegin);
        return Result.success(avg);
    }
}



