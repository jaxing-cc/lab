package com.example.labmanage_server.controller;

import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.domain.Notice;
import com.example.labmanage_server.domain.QueryInfo;
import com.example.labmanage_server.service.NoticeServer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeServer noticeServer;

    @RequestMapping("/add")
    @ResponseBody
    public Msg add(@RequestBody Notice notice){
        if (notice.getInfo() ==null||notice.getUid()==null){
            return Msg.fail();
        }
        notice.setDate(new Date());
        if (noticeServer.add(notice)){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    @RequestMapping("/del")
    @ResponseBody
    public Msg delete(@RequestBody Notice notice){
        if (notice.getId()==null){
            return Msg.fail();
        }
        if (noticeServer.delete(notice.getId())){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    @RequestMapping("/getAll")
    @ResponseBody
    public Msg getAll(@RequestBody QueryInfo queryInfo){
        if (queryInfo.getPagenum()==null){return Msg.fail();}
        if (queryInfo.getPagesize()==null){
            queryInfo.setPagesize(3);
        }
        PageHelper.startPage(queryInfo.getPagenum(),queryInfo.getPagesize());
        List<Notice> all = noticeServer.getAll();
        PageInfo<Notice> pageInfo=new PageInfo<>(all);
        return Msg.success()
                .add("datas",pageInfo.getList())
                .add("total",pageInfo.getTotal());
    }
}
