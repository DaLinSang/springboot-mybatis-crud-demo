package com.itheima.service.impl;

//import com.itheima.entity.UserTransactionLog;
//import com.example.demo.mapper.UserTransactionLogMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.UserTransactionLogMapper;
import com.itheima.pojo.AgeCount;
import com.itheima.pojo.PageBean;
import com.itheima.pojo.UserTransactionLog;
import com.itheima.service.UserTransactionLogService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserTransactionLogServiceImpl implements UserTransactionLogService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private UserTransactionLogMapper mapper;

    private final Random random = new Random();

    /**
     * 方法1：使用 MyBatis BATCH 执行器，逐条插入但批量提交（推荐，性能高且安全）
     * @param total 插入总数，此处为10000
     */
    @Transactional
    public void batchInsertWithExecutor(int total) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            mapper = sqlSession.getMapper(UserTransactionLogMapper.class);
            int batchSize = 1000;  // 每1000条提交一次
            for (int i = 1; i <= total; i++) {
                UserTransactionLog log = generateRandomLog(i);
                mapper.insert(log);
                if (i % batchSize == 0) {
                    sqlSession.flushStatements();  // 发送到数据库但未提交
                    sqlSession.commit();
                    sqlSession.clearCache();
                    System.out.println("第"+(i/batchSize+1)+"次提交数据库");
                }
            }
            sqlSession.flushStatements();
            sqlSession.commit();
        }
        System.out.println("批量插入 " + total + " 条数据完成");
    }

    @Override
    public void insert(UserTransactionLog userTransactionLog) {
        mapper.insert(userTransactionLog);
    }

    @Override
    public UserTransactionLog findById(Integer id) {
        return mapper.findById(id);
    }

    @Override
    public List<UserTransactionLog> findByAge(Integer age) {
        return mapper.findByAge(age);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, LocalDate begin, LocalDate end) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        //执行查询
        List<UserTransactionLog> userTransactionLogList = mapper.list(begin,end);
        Page<UserTransactionLog> p = (Page<UserTransactionLog>) userTransactionLogList;
        //封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public Long count() {
        Long count = mapper.count();
        return count;
    }

    @Override
    public List<AgeCount> countByAge() {
        return mapper.countByAge();
    }

    @Override
    public Long sumTotalPoints() {
        return mapper.sumTotalPoints();
    }

    @Override
    public List<UserTransactionLog> findByPhone(String phone) {
        return mapper.findByPhone(phone);
    }

    @Override
    public List<UserTransactionLog> findByUsername(String username) {
        return mapper.findByUsername(username);
    }

    @Override
    public Long avgAccount() {
        return mapper.avgAccount();
    }

    /**
     * 追加数据（查询最大ID后继续插入）
     * @param total 要追加的数量
     */
    @Override
    public void batchInsertAppend(Integer total) {
        Integer maxId = mapper.findMaxId();
        int startId = maxId + 1;
        System.out.println("当前最大ID: " + maxId + "，开始追加数据，从 " + startId + " 开始");

        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            mapper = sqlSession.getMapper(UserTransactionLogMapper.class);
            int batchSize = 1000;
            for (int i = 1; i <= total; i++) {
                int currentId = startId + i - 1;
                UserTransactionLog log = generateRandomLog(currentId);
                mapper.insert(log);
                if (i % batchSize == 0) {
                    sqlSession.flushStatements();
                    sqlSession.commit();
                    sqlSession.clearCache();
                    System.out.println("第" + (i / batchSize) + "次提交数据库，已插入 " + i + " 条");
                }
            }
            sqlSession.flushStatements();
            sqlSession.commit();
        }
        System.out.println("追加插入 " + total + " 条数据完成");
    }

    /**
     * 基于主键的游标分页查询（性能优于OFFSET分页）
     * @param lastMaxId 上一次查询的最大ID（首次查询传0）
     * @param pageSize 每页条数
     * @return 查询结果列表
     */
    @Override
    public List<UserTransactionLog> pageByCursor(Integer lastMaxId, Integer pageSize) {
        return mapper.listByCursor(lastMaxId, pageSize);
    }

    /**
     * 基于主键的按页码分页查询
     * @param page 页码（从1开始）
     * @param pageSize 每页条数
     * @return 查询结果列表
     */
    @Override
    public List<UserTransactionLog> pageById(Integer page, Integer pageSize) {
        return mapper.listByPage(page, pageSize);
    }

    /**
     * 普通OFFSET分页查询
     * @param page 页码（从1开始）
     * @param pageSize 每页条数
     * @return 查询结果列表
     */
    @Override
    public List<UserTransactionLog> pageByOffset(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        return mapper.listByOffset(offset, pageSize);
    }


    /**
     * 方法2：使用 XML 中的 batchInsert 方法，每批500条（避免SQL过长）
     */
//    @Transactional
//    public void batchInsertWithXml(int total) {
//        int batchSize = 500;
//        List<UserTransactionLog> batchList = new ArrayList<>(batchSize);
//        for (int i = 1; i <= total; i++) {
//            batchList.add(generateRandomLog(i));
//            if (i % batchSize == 0) {
//                userTransactionLogMapper.batchInsert(batchList);
//                batchList.clear();
//            }
//        }
//        if (!batchList.isEmpty()) {
//            userTransactionLogMapper.batchInsert(batchList);
//        }
//        System.out.println("XML 批量插入 " + total + " 条数据完成");
//    }

    // 生成随机测试数据（1万条各字段都有值）
    public UserTransactionLog generateRandomLog(int seq) {
        UserTransactionLog log = new UserTransactionLog();
        log.setUserId(seq);
        log.setUsername("user" + seq);
        log.setAge(18 + random.nextInt(50));
        log.setEmail("user" + seq + "@example.com");
        log.setRegistrationDate(LocalDate.now().minusDays(random.nextInt(365 * 3)));
        log.setLastLoginTime(LocalDateTime.now().minusHours(random.nextInt(720)));
        log.setIsActive(random.nextBoolean());
        log.setAccountBalance(BigDecimal.valueOf(random.nextDouble() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP));
        log.setTotalPoints(random.nextInt(5000));
        log.setPhone("138" + String.format("%08d", random.nextInt(100000000)));
        log.setAddress("虚拟地址" + seq);
        String[] genders = {"Male", "Female", "Other"};
        log.setGender(genders[random.nextInt(3)]);
        log.setBirthday(LocalDate.now().minusYears(18 + random.nextInt(50)).minusDays(random.nextInt(365)));
        log.setMembershipLevel(1 + random.nextInt(5));
        log.setReferrerId(random.nextBoolean() ? random.nextInt(10000) : null);
        log.setStatus(1);
        log.setRemark("batch insert test");
        log.setUpdateTime(LocalDateTime.now());
        log.setCreateTime(LocalDateTime.now());
        return log;
    }
}
