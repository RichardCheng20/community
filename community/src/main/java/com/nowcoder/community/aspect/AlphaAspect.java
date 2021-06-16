package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {

    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut() { //定义切点，加上注解 第一个*表示任何返回值，然后写上包名，所有的方法，（..）所有参数和返回值

    }

    @Before("pointcut()")
    public void before() { //定义通知，在切点的开始
        System.out.println("before");
    }

    @After("pointcut()")
    public void after() {//定义通知，在切点的结束
        System.out.println("after");
    }

    @AfterReturning("pointcut()")
    public void afterRetuning() { //有了返回值以后处理逻辑
        System.out.println("afterRetuning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() { //抛异常的时候植入代码
        System.out.println("afterThrowing");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable { //前后都植入逻辑
        System.out.println("around before");
        Object obj = joinPoint.proceed(); //程序执行的时候会执行代理对象，这个逻辑会到代理对象里代替原始对象
        System.out.println("around after");
        return obj;
    }

}
