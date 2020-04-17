package com.example.labmanage_server.mapper;

import com.example.labmanage_server.domain.Log;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LogMapper {
    @Insert("INSERT INTO `log` (`did`, `uid` , `date`) VALUES (#{did}, #{uid} , #{date})")
    int addLog(Integer did,Integer uid,Date date);

    /**
     * 查询所有
     */
    @Select("select * from `log` where date > #{date} ORDER BY id DESC")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="did",property="declare",
                    one=@One(select="com.example.labmanage_server.mapper.DeclareMapper.getById",fetchType= FetchType.EAGER)),
            @Result(column="uid",property="user",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER)),
    })
    List<Log> getAll(Date date);
}
