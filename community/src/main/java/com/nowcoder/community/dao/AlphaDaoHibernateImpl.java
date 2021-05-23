package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

//访问数据库的注解,括号里面是定义名字
@Repository("alphaDaoHibernate")
public class AlphaDaoHibernateImpl implements AlphaDao{
    @Override
    public String select() {
        return "Hibernate";
    }
}
