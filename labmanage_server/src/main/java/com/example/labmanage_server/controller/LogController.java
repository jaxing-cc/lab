package com.example.labmanage_server.controller;

import com.example.labmanage_server.domain.Log;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.domain.QueryInfo;
import com.example.labmanage_server.service.LogServer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogServer logServer;

    @RequestMapping(value = "/getAll",method = RequestMethod.POST)
    @ResponseBody
    public Msg getAll(@RequestBody QueryInfo queryInfo){
        if (queryInfo.getPagenum()==null||queryInfo.getDate()==null){
            return Msg.fail();
        }
        PageHelper.startPage(queryInfo.getPagenum(),100);
        List<Log> all = logServer.getAll(queryInfo.getDate());
        if (all==null){
            return Msg.fail();
        }
        PageInfo<Log> pageInfo=new PageInfo<Log>(all);
//        System.out.println(pageInfo.getList());
        return Msg.success()
                    .add("datas",pageInfo.getList())
                    .add("total",pageInfo.getTotal());
    }
}
