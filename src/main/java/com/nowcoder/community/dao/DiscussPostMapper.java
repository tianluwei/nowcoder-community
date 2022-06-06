package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // TODO: 2022/5/11 @Param,只有一个参数并且要用到动态sql，就需要用注解。
//    @Param注解用于给参数取别名。如果只有一个参数，并且在<if>里使用，则必须加别名。
    int selectDiscussPostRows(int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

//    返回整数，就是你更新的行数
    int updateCommentCount(int id,int commentCount);

}
