package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

//使用mybatis的注解来标识bean
@Mapper
public interface UserMapper {
    //写上增删改和sql配置文件

    //根据Id查询用户
    User selectById(int id);
    //用户名查询
    User selectByName(String username);
    // 邮箱
    User selectByEmail(String email);

    //增加
    int insertUser(User user);

    //修改状态
    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);

}
