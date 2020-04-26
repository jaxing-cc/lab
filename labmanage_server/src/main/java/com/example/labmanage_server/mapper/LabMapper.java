package com.example.labmanage_server.mapper;

import com.example.labmanage_server.domain.Lab;
import com.example.labmanage_server.domain.LabtoUser;
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

    /**
     * 查询某用户是否加入实验室
     * @param userid
     * @return
     */
    @Select("select * from user_join_lab where userid=#{userid}")
    LabtoUser getLuByUserId(Integer userid);

    /**
     * 添加人员进入实验室
     */
    @Insert("insert into user_join_lab (`labid`,`userid`,`className`,`stuNum`)" +
            " values (#{labid},#{userid},#{className},#{stuNum})")
    int addUserToLab(LabtoUser labtoUser);
    /**
     * 设置标签
     */
    @Update("update user_join_lab set tag=#{tag} where userid = #{userid} and labid=#{labid}")
    int setTag(LabtoUser lu);
    /**
     * 删除人员
     */
    @Delete("delete from user_join_lab where userid = #{userid} and labid=#{labid}")
    int deleteUser(LabtoUser lu);
    /**
     * 查询某实验室人员信息
     */
    @Select("select * from user_join_lab where labid=#{labid} " +
            "and className like '%${className}%' and stuNum like '%${stuNum}%'")
    @Results({
            @Result(id=true,column="id",property="id"),
            @Result(column="labid",property="labid"),
            @Result(column="className",property="className"),
            @Result(column="stuNum",property="stuNum"),
            @Result(column="tag",property="tag"),
            @Result(column="userid",property="user",
                    one=@One(select="com.example.labmanage_server.mapper.UserMapper.getUserById",fetchType= FetchType.EAGER))
    })
    List<LabtoUser> getUserBylabid(LabtoUser labtoUser);
    /**
     * id查
     */

    LabtoUser getLuById(Integer id);
}
