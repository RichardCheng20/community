package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class CommunityApplication {

	@PostConstruct
	public void init() { //管理bean初始化，它修饰的方法在构造器调用完就执行
		// 解决netty启动冲突问题
		// redis底层也基于Netty
		// see Netty4Utils.setAvailableProcessors()找到的
		System.setProperty("es.set.netty.runtime.available.processors", "false");
	}
	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}

}
