package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    //Bean 为了被spring容器装配，核心对象是一个接口叫producer
    //实例化接口就是返回这个接口Producer
    @Bean
    public Producer kaptchaProducer() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ"); //随机字符
        properties.setProperty("kaptcha.textproducer.char.length", "4"); //长度限定
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");  //噪声

        //实例化它的实现类
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        //传入参数 可以封装到config对象里，要求传入properties， 即k-v对
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
