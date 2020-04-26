package com.example.labmanage_server.controller;

import com.example.labmanage_server.domain.Blog;
import com.example.labmanage_server.domain.Lab;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.domain.QueryInfo;
import com.example.labmanage_server.service.BlogServer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogServer server;
    @RequestMapping("/add")
    @ResponseBody
    public Msg add(@RequestBody Blog blog){
        if (blog.getText()==null||blog.getTitile()==null){
            return Msg.fail();
        }
        if (server.addBlog(blog)){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    @RequestMapping("/getAll")
    @ResponseBody
    public Msg getAll(@RequestBody QueryInfo info){
        if (info.getPagenum()==null||info.getQuery()==null){
            return Msg.fail();
        }
        PageHelper.startPage(info.getPagenum(),info.getPagesize());
        List<Blog> all = server.getAll(info.getQuery());
        if (all==null){
            return Msg.fail();
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(all);
        return Msg.success()
                .add("datas",pageInfo.getList())
                .add("total",pageInfo.getTotal());
    }
    @RequestMapping("/getByUid")
    @ResponseBody
    public Msg getByUid(@RequestBody QueryInfo info){


        if (info.getPagenum()==null||info.getPagesize()==null||info.getUid()==null){


            return Msg.fail();
        }

        PageHelper.startPage(info.getPagenum(),info.getPagesize());
        List<Blog> all = server.getByUid(info.getUid());
        if (all==null){
            return Msg.fail();
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(all);
        return Msg.success()
                .add("datas",pageInfo.getList())
                .add("total",pageInfo.getTotal());
    }
    @RequestMapping("/good")
    @ResponseBody
    public Msg good(@RequestBody QueryInfo info){
        if(info.getId()==null){
            return Msg.fail();
        }
        server.good(info.getId());
        return Msg.success();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Msg delete(@RequestBody QueryInfo info){
        if(info.getId()==null){
            return Msg.fail();
        }
       if (server.deleteById(info.getId())){
           return Msg.success();
       }else{
           return Msg.fail();
       }
    }
}
