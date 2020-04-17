package com.example.labmanage_server.controller;

import com.example.labmanage_server.domain.Declare;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.domain.QueryInfo;
import com.example.labmanage_server.service.DeclareServer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/declare")
public class DeclareController {

     HashMap<String,Object> map=new HashMap<>();
    @Autowired
    DeclareServer declareServer;

    @RequestMapping(value = "/sendDeclare",method = RequestMethod.POST)
    @ResponseBody
    public Msg sendDeclare(@RequestBody Declare declare){//uid  reason eid
        if (declare==null){
            return Msg.fail();
        }
        if (declareServer.addDeclare(declare)){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }

    /**
     * 获取用户的申报列表
     * @param queryInfo
     * @return
     */
    @RequestMapping(value = "/getByUid",method = RequestMethod.POST)
    @ResponseBody
    public Msg getByUid(@RequestBody QueryInfo queryInfo){//uid  reason eid
       if (queryInfo.getUid()==null||queryInfo.getPagenum()==null){
           return Msg.fail();
       }
        List<Declare> declares=null;
       try {
           PageHelper.startPage(queryInfo.getPagenum(),100);
           declares= declareServer.getByUid(queryInfo.getUid());
       }catch (Exception e){
           e.printStackTrace();
           return Msg.fail();
       }
        if (declares==null){
            return Msg.fail();
        }
        PageInfo<Declare> pageInfo = new PageInfo<Declare>(declares);

        return Msg.success()
                .add("datas",pageInfo.getList())
                .add("total",pageInfo.getTotal());

    }

    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    @ResponseBody
    public Msg delete(@RequestBody QueryInfo queryInfo){
//        System.out.println(queryInfo.getId());
        if (queryInfo.getId()==null){
            return Msg.fail();
        }
        if(declareServer.deleteDeclare(queryInfo.getId())){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
    @RequestMapping(value = "/getAllByDone",method = RequestMethod.POST)
    @ResponseBody
    public Msg getAllByState(@RequestBody QueryInfo declare){
        if (declare.getPagenum()==null){
            declare.setPagenum(1);
        }
        if (declare.getDone()==null){
            System.out.println("没有状态参数！");
            return Msg.fail();
        }
        if (declare.getDate()==null){
            String dateStr="2000-01-01";
            Date date=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            try {
                simpleDateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("时间转化出错了");
            }
            declare.setDate(date);
        }
        System.out.println(declare);
        List<Declare> list = null;
        try {
            PageHelper.startPage(declare.getPagenum(),100);
            list=declareServer.getAllByState(declare.getDone(), declare.getDate());
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail();
        }
        if (list==null){
            return Msg.fail();
        }
        PageInfo<Declare> pageInfo = new PageInfo<Declare>(list);
        return Msg.success()
                .add("datas",pageInfo.getList())
                .add("total",pageInfo.getTotal());
    }

    /**
     * 处理申报
     * Integer done,Integer id,Integer uid,Integer eid
     */
    @RequestMapping(value = "/setDone",method = RequestMethod.POST)
    @ResponseBody
    public  Msg setDone(@RequestBody QueryInfo queryInfo){
        if (queryInfo.getId()==null||queryInfo.getDone()==null||queryInfo.getUid()==null||queryInfo.getEid()==null){
            return Msg.fail();
        }
        if (declareServer.setDone(queryInfo.getDone(),queryInfo.getId(),queryInfo.getUid(),queryInfo.getEid())){
            return Msg.success();
        }else{
            return Msg.fail();
        }
    }
}
