package com.example.labmanage_server.controller;

import com.example.labmanage_server.domain.Eqp;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.domain.QueryInfo;
import com.example.labmanage_server.service.EqpServer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/eqp")
public class EqpController {

    @Autowired
    EqpServer eqpServer;

    @RequestMapping(value = "/getAllEqp",method = RequestMethod.POST)
    @ResponseBody
    public Msg getAllEqp(){
        Msg allEqp = eqpServer.getAllEqp();
        return allEqp;
    }

    /**
     * 通过实验室查设备 分页
     * @param query
     * @return
     */
    @RequestMapping(value = "/getEqpByLabid",method = RequestMethod.POST)
    @ResponseBody
    public Msg getEqpByLabid(@RequestBody QueryInfo query){
//        System.out.println("参数:"+query);
        PageHelper.startPage(query.getPagenum(),100);
        List<Eqp> eqps = eqpServer.getEqpByLabId( query.getLabid(), query.getQuery());
        if (eqps==null){
            return Msg.fail();
        }
        PageInfo<Eqp> pageInfo = new PageInfo<Eqp>(eqps);
//        System.out.println("查询到的数据"+pageInfo.getList());
        return Msg.success()
                .add("eqps",pageInfo.getList())
                .add("total",pageInfo.getTotal());
    }

    /**
     * 修改
     * @param eqp
     * @return
     */
    @RequestMapping(value = "/setEqp",method = RequestMethod.POST)
    @ResponseBody
    public Msg setEqp(@RequestBody Eqp eqp) {
//        System.out.println(eqp);
        if (eqpServer.setEqpInfo(eqp)) {
            return Msg.fail();
        } else {
            return Msg.success();
        }
    }
    /**
     * 删除
     * @param eqp
     * @return
     */
    @RequestMapping(value = "/delEqp",method = RequestMethod.POST)
    @ResponseBody
    public Msg delEqp(@RequestBody Eqp eqp) {
        if (eqp.getId()==null){
            return Msg.fail("参数接收失败");
        }
        if (eqpServer.deleteEqp(eqp.getId())) {
            return Msg.fail();
        } else {
            return Msg.success();
        }
    }
    /**
     * 添加
     * @param eqp
     * @return
     */
    @RequestMapping(value = "/addEqp",method = RequestMethod.POST)
    @ResponseBody
    public Msg addEqp(@RequestBody Eqp eqp) {
        if (eqpServer.addEqp(eqp)) {
            return Msg.fail();
        } else {
            return Msg.success();
        }
    }
}
