package com.example.labmanage_server.mapper;

import com.example.labmanage_server.domain.Lab;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabMapper {
    @Select("select * from lab")
    List<Lab> getAllEqp();

    @Select("select * from lab where name like '%${query}%' ORDER BY id DESC")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="uid",property="master",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER))
    })
    List<Lab> getAllEqpByName(String query);

    @Select("select * from lab where id=#{id} ")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="uid",property="master",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER))
    })
    Lab getLabById(Integer id);
/**
 * 通过管理员id查询实验室
 */
    @Select("select * from lab where uid = #{id}")
    List<Lab> getLabByuid(Integer id);

    /**
     * 实验室修改  uid name
     */
    @Update("UPDATE `lab` SET `name`=#{name},`uid`=#{uid} WHERE (`id`=#{id})")
    int setLabinfo(String name,Integer uid,Integer id);
    /**
     * 新增
     */
    @Insert("INSERT INTO `lab` (`uid`, `name`) VALUES ( -1 , #{name})")
    int addLab(String name);

    @Delete("DELETE FROM `lab` WHERE (`id`=#{id}) ")
    int deleteLab(Integer id);

}
