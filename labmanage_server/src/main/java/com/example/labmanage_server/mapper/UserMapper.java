package com.example.labmanage_server.mapper;

import com.example.labmanage_server.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    /**
     * 名称查询
     */
    @Select("select * from user where name like  '%${query}%' ORDER BY id DESC")
    List<User> getUsersByName(String query);

    //id查用户
    @Select("select * from user where id=#{id} ")
    User getUserById(Integer id);

    /**
     * 用户名查重
     * @param username
     * @return
     */
    @Select("select * from user where username=#{username}")
    User getUserByUsername(String username);


    @Insert("INSERT INTO `user` (`name`, `username`, `password`, `phone`, `userrank`)" +
            " VALUES (#{name}, #{username}, #{password}, #{phone}, 3);")
    int addUser(User user);
    /**
     * 注册
     */
    @Select("select * from user where username=#{username} and password=#{password} ")
    User getUser(String username, String password);

    @Update("UPDATE `user` SET `password`=#{password}, `phone`=#{phone} WHERE (`id`=#{id})")
    int update(User user);

    /**
     * 权限修改
     */
    @Update("UPDATE `user` SET `userrank`=#{userrank} WHERE (`id`=#{id})")
    int setUerRank(User user);

    /**
     * 删除用户真删除
     */
    @Delete("DELETE FROM `user` WHERE (`id`=#{id}) ")
    int deletUserById(Integer id);
}
