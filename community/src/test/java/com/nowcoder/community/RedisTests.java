package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    //访问string
    @Test
    public void testStrings() {
        String redisKey = "test:count";

        redisTemplate.opsForValue().set(redisKey, 1); //存的方式

        System.out.println(redisTemplate.opsForValue().get(redisKey)); //取数据打印
        System.out.println(redisTemplate.opsForValue().increment(redisKey)); //增加
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    //访问hash
    @Test
    public void testHashes() {
        String redisKey = "test:user";

        redisTemplate.opsForHash().put(redisKey, "id", 1); //存
        redisTemplate.opsForHash().put(redisKey, "username", "zhangsan");

        System.out.println(redisTemplate.opsForHash().get(redisKey, "id"));//取
        System.out.println(redisTemplate.opsForHash().get(redisKey, "username"));
    }

    //访问列表
    @Test
    public void testLists() {
        String redisKey = "test:ids";

        redisTemplate.opsForList().leftPush(redisKey, 101); //左边进
        redisTemplate.opsForList().leftPush(redisKey, 102);
        redisTemplate.opsForList().leftPush(redisKey, 103);

        System.out.println(redisTemplate.opsForList().size(redisKey)); //获取数据量
        System.out.println(redisTemplate.opsForList().index(redisKey, 0));
        System.out.println(redisTemplate.opsForList().range(redisKey, 0, 2));

        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
    }

    //测试set
    @Test
    public void testSets() {
        String redisKey = "test:teachers";

        redisTemplate.opsForSet().add(redisKey, "刘备", "关羽", "张飞", "赵云", "诸葛亮");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        System.out.println(redisTemplate.opsForSet().members(redisKey));
    }

    //测试有序set
    @Test
    public void testSortedSets() {
        String redisKey = "test:students";

        redisTemplate.opsForZSet().add(redisKey, "唐僧", 80);
        redisTemplate.opsForZSet().add(redisKey, "悟空", 90);
        redisTemplate.opsForZSet().add(redisKey, "八戒", 50);
        redisTemplate.opsForZSet().add(redisKey, "沙僧", 70);
        redisTemplate.opsForZSet().add(redisKey, "白龙马", 60);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey)); //数据量
        System.out.println(redisTemplate.opsForZSet().score(redisKey, "八戒")); //查看分数
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey, "八戒")); //大到小排序
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, 2)); //取范围内的大到小排序

    }

    //访问key
    @Test
    public void testKeys() {
        redisTemplate.delete("test:user");

        System.out.println(redisTemplate.hasKey("test:user"));

        redisTemplate.expire("test:students", 10, TimeUnit.SECONDS);
    }

    // 多次访问同一个key
    @Test
    public void testBoundOperations() {
        String redisKey = "test:count"; //绑定key
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get()); //提前绑定了不用传入key
    }

    // 编程式事务
    // 启用事务以后，再去执行Redis命令时，并不会执行命令，会把命令放到队列里存着，
    // 直到提交事务的时候，发给Redis服务器一起执行。例如中途有查询操作，就不会立刻返回结果
    // Spring 支持编程式事务。声明式事务虽更简单，但是只能精确到一个方法（配置+transaction注解）。
    @Test
    public void testTransactional() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String redisKey = "test:tx";

                operations.multi(); //开启事务

                operations.opsForSet().add(redisKey, "zhangsan");
                operations.opsForSet().add(redisKey, "lisi");
                operations.opsForSet().add(redisKey, "wangwu");

                System.out.println(operations.opsForSet().members(redisKey)); //查一下数据

                return operations.exec(); //提交事务
            }
        });
        System.out.println(obj);
    }

}
