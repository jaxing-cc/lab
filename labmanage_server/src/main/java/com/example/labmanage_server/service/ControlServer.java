package com.example.labmanage_server.service;


import com.example.labmanage_server.domain.Control;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.mapper.ControlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能控制 接口在user中
 */
@Service
public class ControlServer {

    @Autowired
    ControlMapper controlMapper;

    /**
     * 返回等级对应功能
     * @param rank
     * @return
     */
    public Msg getControl(Integer rank){
        List<Control> control =null;
        try{
          control=  controlMapper.getControl(rank);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (control!=null){
            return Msg.success().add("control",control);
        }else{
            return Msg.fail();
        }
    }

}
