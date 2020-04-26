package com.example.labmanage_server.mapper;

import com.example.labmanage_server.domain.Blog;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogMapper {
    @Update("UPDATE `blogs` SET good=good +1  WHERE (`id`=#{id});")
    int Good(Integer id);
    @Insert("INSERT INTO blogs " +
            "(`uid`, `text`, `data`, `titile`, `good`) " +
            "VALUES (#{uid}, #{text}, #{data}, #{titile}, 0);")
    int addBlogs(Blog blog);
    @Select("select * from blogs where titile like '%${titile}%' ORDER BY id DESC")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="uid",property="user",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER)),
            @Result(column="text",property="text"),
            @Result(column="data",property="data"),
            @Result(column="titile",property="titile"),
            @Result(column="good",property="good"),
    })
    List<Blog> getAll(String titile);
    @Select("select * from blogs where uid= #{id} ORDER BY id DESC")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="uid",property="user",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER)),
            @Result(column="text",property="text"),
            @Result(column="data",property="data"),
            @Result(column="titile",property="titile"),
            @Result(column="good",property="good"),
    })
    List<Blog> getByUid(Integer id);
    @Delete("delete from blogs where id=#{id}")
    int deleteById(Integer id);
}
