package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//分页查询结果封装类
@Data//get set方法自动生成
@NoArgsConstructor//无参构造
@AllArgsConstructor//全参构造

public class PageBean {

    private Long total;//总记录数
    private List rows;//数据列表
}
