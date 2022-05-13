package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);

    // TODO: 2022/5/11 @Param,只有一个参数并且要用到动态sql，就需要用注解。
    int selectDiscussPostRows(int userId);

}
