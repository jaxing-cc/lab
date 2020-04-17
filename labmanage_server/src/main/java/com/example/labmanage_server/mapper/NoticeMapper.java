package com.example.labmanage_server.mapper;


import com.example.labmanage_server.domain.Notice;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMapper {
    /**
     * 添加一个公告
     */
    @Insert("INSERT INTO `notice` (`uid`, `info`, `date`) VALUES (#{uid}, #{info}, #{date});")
   int add(Notice notice);

    @Delete("DELETE FROM `notice` WHERE (`id`=#{id})")
    int delete(Integer id);

    @Select("select * from notice ORDER BY id DESC")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="info",property="info"),
            @Result(column="date",property="date"),
            @Result(column="uid",property="user",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER))
    })
    List<Notice> getAll();
}
