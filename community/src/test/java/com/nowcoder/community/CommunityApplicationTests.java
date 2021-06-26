package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import com.nowcoder.community.service.AlphaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
//测试代码以某一个为配置类
@ContextConfiguration(classes = CommunityApplication.class)
//IOC核心spring容器,类要实现容器就得实现这个接口
class CommunityApplicationTests implements ApplicationContextAware {

	private ApplicationContext applicationContext;//把容器暂存起来后面可以使用它

	@Override
	//applicationContext就是spring容器
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	//测试Spring容器 19.51
	public void testApplicationContext() {
		System.out.println(applicationContext);

		//从容器里获取自动装配的bean
		//通过类型获取
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());

		//通过名字获取， 然后转型，想用hibernate里的内容
		 alphaDao = applicationContext.getBean("alphaDaoHibernate", AlphaDao.class);
		System.out.println(alphaDao.select());

	}

	@Test
	public void testBeanManagement() {
		AlphaDao alphaService = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaService);
	}

	@Test
	public void testBeanConfig() {
		SimpleDateFormat simpleDateFormat =
				applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}

	//注入需要注解，加到成员变量之前即可
	@Autowired
	//如果说希望注入的是hibernate，就得加注解
	@Qualifier("alphaDaoHibernate")
	private AlphaDao alphaDao;

	//想获取AlphaService 可以这样做
	@Autowired
	private AlphaService alphaService;


	@Autowired
	private SimpleDateFormat simpleDateFormat;

	//测试一下依赖注入
	@Test
	public void testDI() {
		System.out.println(alphaDao);

		System.out.println(alphaService);
		System.out.println(simpleDateFormat);
	}

}
