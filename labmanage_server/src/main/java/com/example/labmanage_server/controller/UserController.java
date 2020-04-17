package com.example.labmanage_server.controller;

import com.example.labmanage_server.domain.Eqp;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.domain.QueryInfo;
import com.example.labmanage_server.domain.User;
import com.example.labmanage_server.service.ControlServer;
import com.example.labmanage_server.service.UserServer;
import com.example.labmanage_server.utils.TokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServer server;
    @Autowired
    ControlServer controlServer;
    /**
     * 用户登录
     * @param user
     * @return user对象
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Msg login(@RequestBody User user){
//        System.out.println(user);
        //用户登录
        Msg msg =server.login(user.getUsername(),user.getPassword());
        if (msg.isFlag()){
            String token = TokenUtil.sign(user);
            msg.add("token",token);
        }
        return msg;
    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Msg register(@RequestBody User user){
        return server.register(user);
    }
    /**
     * 分发权限功能
     */
    @RequestMapping(value = "/getControl",method = RequestMethod.GET)
    @ResponseBody
    public Msg getControl(User user){
        if (user.getUserrank()==null){
            return Msg.fail();
        }
        return controlServer.getControl(user.getUserrank());
    }
    /**
     * 修改用户信息
     */
    @RequestMapping(value = "/updateinfo",method = RequestMethod.POST)
    @ResponseBody
    public Msg update(@RequestBody User user){
        System.out.println(user);
        if (user.getId()==null||user.getPassword()==null||user.getPhone()==null){
            return Msg.fail();
        }
        if (server.update(user)){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    /**
     * username查询
     */
    @RequestMapping(value = "/getuser",method = RequestMethod.GET)
    @ResponseBody
        public Msg getUserByUsername(String username){
        User user = server.getUserByUsername(username);
        if (user==null){
            return Msg.fail("无法查询到该用户");
        }else{
            return Msg.success("已查询到该用户").add("user",user);
        }
    }
    /**
     * username查询
     */
    @RequestMapping(value = "/getuserTest",method = RequestMethod.GET)
    @ResponseBody
    public Msg getUserByUsernameTest(String username){
        User user = server.getUserByUsername(username);
        if (user==null||user.getUserrank()!=2){
            return Msg.fail("无法查询到该管理员");
        }else{
            return Msg.success("已查询到该管理员").add("user",user);
        }
    }

    @RequestMapping(value = "/getusers",method = RequestMethod.POST)
    @ResponseBody
    public Msg getUserByname(@RequestBody QueryInfo queryInfo){
       if (queryInfo.getPagenum()==null){queryInfo.setPagenum(1);}
        if (queryInfo.getPagesize()==null){queryInfo.setPagesize(100);}
        if (queryInfo.getName()==null){queryInfo.setName("");}
        PageHelper.startPage(queryInfo.getPagenum(),queryInfo.getPagesize());
        List<User> users = server.getUsers(queryInfo.getName());
        if (users==null){
            return Msg.fail("未查询到数据!");
        }
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        return Msg.success()
                .add("datas",pageInfo.getList())
                .add("total",pageInfo.getTotal());

    }
    /**
     * 权限设置
     */
    @RequestMapping(value = "/setUserRank",method = RequestMethod.POST)
    @ResponseBody
    public Msg setUserRank(@RequestBody User user){
       return server.setUerRank(user);
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    @ResponseBody
    public Msg deleteById(@RequestBody User user){
        if (user.getId()==null){
            return Msg.fail();
        }
        if (server.deletUserById(user.getId())){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
}
