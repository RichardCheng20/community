package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    //查询返回的是帖子对象,将来要查我发过的帖子
    //考虑到分页 offset起始行的行号，limit页面最多显示的数据
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);
    //需要知道一共有多少行,返回行数。userId为0不参与，个人主页我的贴子就需要这个条件
    //@Param注解的作用是给参数起别名,如果需要动态拼一个条件，并且这个方法有且只有这一个条件/参数，那么参数之前必须有别名
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(int id, int commentCount);
}
