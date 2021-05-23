package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//测试代码以某一个为配置类
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTests {
    //设为静态的每个地方都能用,传入类就是logger的名字,这样不同的logger在不同的类下就有区别，就能知道是哪里的logger
    private static final Logger logger = LoggerFactory.getLogger(LoggerTests.class);

    @Test
    public void testLogger() {
        System.out.println(logger.getName());

        logger.debug("debug log");//暂时调试用以后不需要了
        logger.info("info log"); //启用线程池，定时任务
        logger.warn("warn log");//这个用得相对少
        logger.error("error log");
    }
}
