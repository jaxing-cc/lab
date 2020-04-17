package com.example.labmanage_server.service;

import com.example.labmanage_server.domain.Lab;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.domain.User;
import com.example.labmanage_server.mapper.LabMapper;
import com.example.labmanage_server.mapper.UserMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServer {
    @Autowired
    UserMapper mapper=null;
    @Autowired
    LabMapper labMapper=null;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public Msg login(String username, String password) {
        if(mapper==null) return null;
        User user= mapper.getUser(username,password);
        if (user==null){
            return Msg.fail();
        }else{
            return Msg.success().add("user",user);
        }
    }

    /**
     * 注册方法
     * @return
     */
    public Msg register(User user){
        if (user.getName()==null||user.getUsername()==null||
        user.getPassword()==null||user.getPhone()==null){
            return Msg.fail("请求参数错误!");
        }
        if(mapper.getUserByUsername(user.getUsername())!=null){
            return Msg.fail("用户名重复！");
        }
        if(mapper.addUser(user)>0){
            return Msg.success("注册成功!");
        }else{
            return Msg.fail("注册失败!");
        }
    }
    /**
     * 修改信息
     * @param user
     * @return
     */
    public boolean update(User user){
        if (mapper.update(user)>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 通过username查找用户
     */
    public User getUserByUsername(String username){
        if (username==null){
            return null;
        }
        return mapper.getUserByUsername(username);
    }
    /**
     * 根据名称获取
     */
    public List<User> getUsers(String query){
        return  mapper.getUsersByName(query);
    }
    /**
     * 权限修改
     */
    public Msg setUerRank(User user){
        if (user.getUserrank()==null||user.getId()==null){
            return Msg.fail("请求失败");
        }
        User userById = mapper.getUserById(user.getId());
        if (userById==null){
            return Msg.fail("该用户已经注销");
        }
        if (userById.getUserrank()==2){
            //该用户是管理员
            //需要先清除该成员绑定的实验室
            List<Lab> labs = labMapper.getLabByuid(user.getId());
            if (labs.size()==0){
                if (mapper.setUerRank(user)>0){
                    return Msg.success("修改成功");
                }else{
                    return Msg.fail("修改失败");
                }
            }else{
                return Msg.fail("请用户有绑定的实验室,请处理后再修改!");
            }
        }
        if (mapper.setUerRank(user)>0){
            return Msg.success("修改成功");
        }else{
            return Msg.fail("修改失败");
        }
    }

    /**
     * 注销用户真删除
     */
    public boolean deletUserById(Integer id){
        if (mapper.deletUserById(id)>0){
            return true;
        }else{
            return false;
        }
    }
}
