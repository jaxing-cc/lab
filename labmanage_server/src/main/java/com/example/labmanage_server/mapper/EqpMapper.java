package com.example.labmanage_server.mapper;

import com.example.labmanage_server.domain.Eqp;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EqpMapper {

    @Select("select *from eqp where  isdel != 0 ORDER BY id DESC")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="state",property="state"),
            @Result(column="name",property="name"),
            @Result(column="date",property="date"),
            @Result(column="isdel",property="isdel"),
            @Result(column="labid",property="lab",
                    one=@One(select="com.example.labmanage_server.mapper.LabMapper.getLabById",fetchType= FetchType.EAGER))
    })
    List<Eqp> getAllEqp();

    @Select("select * from eqp where id=#{id} and isdel != 0")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="state",property="state"),
            @Result(column="name",property="name"),
            @Result(column="date",property="date"),
            @Result(column="isdel",property="isdel"),
            @Result(column="labid",property="lab",
                    one=@One(select="com.example.labmanage_server.mapper.LabMapper.getLabById",fetchType= FetchType.EAGER))
    })
    Eqp getEqpById(Integer id);

    //根据实验室查设备
    @Select("select * from eqp where labid=#{labid} and name  like '%${query}%' and isdel != 0 ORDER BY id DESC")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="state",property="state"),
            @Result(column="name",property="name"),
            @Result(column="date",property="date"),
            @Result(column="isdel",property="isdel"),
            @Result(column="labid",property="lab",
                    one=@One(select="com.example.labmanage_server.mapper.LabMapper.getLabById",fetchType= FetchType.EAGER))
    })
    List<Eqp> getEqpByLabId(Integer labid,String query);
    //实验室id查设备数 内部调用
    @Select("select COUNT(*) from eqp where labid=#{labid} and isdel != 0 ")
    int getEqpByLabId_test(Integer labid);
    /**
     * 修改设备状态
     */
    @Update("UPDATE `eqp` SET `state`=#{state} WHERE (`id`=#{id})")
    int setEqpState(Integer state,Integer id);

    /**
     * 修改设备信息
     */
    @Update("UPDATE `eqp` SET `state`=#{state},`labid`=#{labid}, `name`=#{name} WHERE (`id`=#{id})")
    int setEqpInfo(Eqp eqp);
    /**
     * 删除设备 假删除
     */
    @Update("UPDATE `eqp` SET `isdel`=0 WHERE (`id`=#{id})")
    int deleteEqp(Integer id);
    /**
     * 增加
     */
    @Insert("INSERT INTO `eqp` (`state`, `labid`, `name`, `date`, `isdel`)" +
            " VALUES ( #{state}, #{labid}, #{name}, #{date}, #{isdel})")
    int addEqp(Eqp eqp);


}
