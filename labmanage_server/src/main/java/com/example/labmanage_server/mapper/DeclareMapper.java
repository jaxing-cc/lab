package com.example.labmanage_server.mapper;


import com.example.labmanage_server.domain.Declare;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 申报表的操作
 */

@Repository
public interface DeclareMapper {
    @Select("select * from `declare` where id = #{id} ")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="reason",property="reason"),
            @Result(column="done",property="done"),
            @Result(column="date",property="date"),
            @Result(column="eid",property="eqp",
                    one=@One(select="com.example.labmanage_server.mapper.EqpMapper.getEqpById",fetchType= FetchType.EAGER)),
            @Result(column="master",property="mast",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.LAZY)),
            @Result(column="uid",property="user",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER))
    })
    public Declare getById(Integer id);
    /**
     * 增加一个申报表记录
     */
    @Insert("INSERT INTO `declare` (`uid`, `reason`, `done`, `eid`, `date`, `master`)" +
            " VALUES (#{uid}, #{reason}, #{done}, #{eid}, #{date}, #{master});")
    int addDeclare(Declare declare);
    /**
     * 通过uid查询申报表
     */
    @Select("select * from `declare` where uid = #{uid} ORDER BY id DESC")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="reason",property="reason"),
            @Result(column="done",property="done"),
            @Result(column="date",property="date"),
            @Result(column="eid",property="eqp",
                    one=@One(select="com.example.labmanage_server.mapper.EqpMapper.getEqpById",fetchType= FetchType.EAGER)),
            @Result(column="master",property="mast",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER)),
            @Result(column="uid",property="user",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.LAZY))
    })
    List<Declare> getDeclare(Integer uid);

    /**
     * 删除一个申报请求，真删除！！
     */
    @Delete("DELETE FROM `declare` WHERE id = #{id}")
    int deleteDeclare(Integer id);

    /**
     * 获取所有的申报请求
     * 按状态和时间
     */
    @Select("select * from `declare` where done=#{done} and date>#{startTime} ORDER BY id DESC")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="reason",property="reason"),
            @Result(column="done",property="done"),
            @Result(column="date",property="date"),
            @Result(column="eid",property="eqp",
                    one=@One(select="com.example.labmanage_server.mapper.EqpMapper.getEqpById",fetchType= FetchType.EAGER)),
            @Result(column="master",property="mast",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER)),
            @Result(column="uid",property="user",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.LAZY))
    })
    List<Declare> getAllByState(Integer done, Date startTime);

    /**
     * 设置状态
     * @param done
     * @param id
     * @return
     */
    @Update("UPDATE `declare` SET `done`=#{done} WHERE (`id`=#{id})")
    int setDone(Integer done,Integer id);
}
