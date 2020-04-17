package com.example.labmanage_server.controller;


import com.example.labmanage_server.domain.*;
import com.example.labmanage_server.service.LabServer;
import com.example.labmanage_server.utils.TokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/lab")
public class LabController {

    @Autowired
    LabServer server;

    @RequestMapping(value = "/getAllLab",method = RequestMethod.POST)
    @ResponseBody
    public Msg getAllLab(){
        Msg allLab = server.getAllLab();
        return allLab;
    }

    @RequestMapping(value = "/getAllLabByPage",method = RequestMethod.POST)
    @ResponseBody
    public Msg getAllLabByPage(@RequestBody QueryInfo queryInfo){
        if (queryInfo.getPagenum()==null||queryInfo.getName()==null){
            return Msg.fail();
        }
        PageHelper.startPage(queryInfo.getPagenum(),100);
        List<Lab> labs = server.getAllLabByPage(queryInfo.getName());
        if (labs==null){
            return Msg.fail();
        }
        PageInfo<Lab> pageInfo = new PageInfo<Lab>(labs);
        return Msg.success()
                .add("datas",pageInfo.getList())
                .add("total",pageInfo.getTotal());
    }
    /**
     * 不分页
     * @param queryInfo
     * @return
     */
    @RequestMapping(value = "/getLabByUid",method = RequestMethod.POST)
    @ResponseBody
    public Msg getLabByUid(@RequestBody QueryInfo queryInfo){
        if (queryInfo.getUid()==null){
            return Msg.fail();
        }
        List allLab=server.getLabByuid(queryInfo.getUid());
        return Msg.success().add("datas",allLab);
    }

    /**
     * 修改实验室信息
     */
    @RequestMapping(value = "/setLabInfo",method = RequestMethod.POST)
    @ResponseBody
    public Msg setLabInfo(@RequestBody QueryInfo queryInfo){
        if (queryInfo.getName()==null||queryInfo.getQuery()==null||
        queryInfo.getUid()==null||queryInfo.getId()==null){
            return Msg.fail();
        }
        if(server.setLabinfo(queryInfo.getName(), queryInfo.getQuery()//String name,String radio,Integer uid,Integer id
                , queryInfo.getUid(), queryInfo.getId())){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    /**
     * 添加实验室
     */
    @RequestMapping(value = "/addLab",method = RequestMethod.POST)
    @ResponseBody
    public Msg addLab(@RequestBody QueryInfo queryInfo){
        if (queryInfo.getName()==null||queryInfo.getName().equals("")){
            return Msg.fail();
        }
        if(server.addLab(queryInfo.getName())){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    /**
     * 删除实验室
     */
    @RequestMapping(value = "/deleteLab",method = RequestMethod.POST)
    @ResponseBody
    public Msg deleteLab(@RequestBody QueryInfo queryInfo){
        if (queryInfo.getId()==null){
            return Msg.fail();
        }
        if(server.deleteLab(queryInfo.getId())){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

}
