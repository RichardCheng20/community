package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//业务组件的注解
@Service
//多个实例，每次访问都可以创建新的bean
//@Scope("prototype")
public class AlphaService {

    //alphaDao注入给service
    @Autowired
    private AlphaDao alphaDao;

    public AlphaService() {
        System.out.println("实例化AlphaService");
    }

    //注解容器自动调用这个方法,这方法会在构造器之后调用
    @PostConstruct
    public void init() {
        System.out.println("初始化AlphaService");
    }

    //在销毁对象之前调用
    @PreDestroy
    public void destroy() {
        System.out.println("销毁AlphaService");
    }

    //service依赖dao
    public String find() {
        return alphaDao.select();
    }
}
