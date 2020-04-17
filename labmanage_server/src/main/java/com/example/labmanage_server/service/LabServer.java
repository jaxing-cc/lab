package com.example.labmanage_server.service;

import com.example.labmanage_server.domain.Eqp;
import com.example.labmanage_server.domain.Lab;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.mapper.EqpMapper;
import com.example.labmanage_server.mapper.LabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabServer {
    @Autowired
    LabMapper labMapper;

    @Autowired
    EqpMapper eqpMapper;

    public Msg getAllLab(){
        List<Lab> labs;
        try{
            labs=labMapper.getAllEqp();
        }catch (Exception e){
            labs=null;
            e.printStackTrace();
        }
        if (labs==null){
            return Msg.fail();
        }else{
            return Msg.success().add("lab",labs);
        }
    }
    public List<Lab> getAllLabByPage(String name){
        return labMapper.getAllEqpByName(name);
    }

    public List<Lab> getLabByuid(Integer id){
        return labMapper.getLabByuid(id);
    }

    public boolean setLabinfo(String name,String radio,Integer uid,Integer id){
        if (radio.equals("1")){
            if (labMapper.setLabinfo(name,uid,id)>0){
                return true;
            }else{
                return false;
            }
        }
        if (radio.equals("2")){
            //注销绑定的管理员
            if (labMapper.setLabinfo(name,-1,id)>0){
                return true;
            }else{
                return false;
            }
        }
        if (radio.equals("3")){
            if (labMapper.setLabinfo(name,uid,id)>0){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    public boolean addLab(String name){
        if (name==null){return false;}
        if (labMapper.addLab(name)>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteLab(Integer id){
        if (id==null){return false;}
        int nums = eqpMapper.getEqpByLabId_test(id);
        if (nums==0){
            if (labMapper.deleteLab(id)>0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }


}
