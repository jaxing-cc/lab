package com.example.labmanage_server.service;


import com.example.labmanage_server.domain.Eqp;
import com.example.labmanage_server.domain.Msg;
import com.example.labmanage_server.mapper.EqpMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EqpServer {
    @Autowired
    EqpMapper eqpMapper;

    /**
     * 获取全部设备
     * @return
     */
   public Msg getAllEqp(){
       List<Eqp> eqps;
       try{
           eqps=eqpMapper.getAllEqp();
       }catch (Exception e){
           eqps=null;
           e.printStackTrace();
       }
       if (eqps==null){
           return Msg.fail();
       }else{
           return Msg.success().add("eqps",eqps);
       }
   }

   public List<Eqp> getEqpByLabId(Integer labid,String query){
       List<Eqp> eqps;
       if (labid==null){
           return null;
       }
       try{
           eqps=eqpMapper.getEqpByLabId(labid,query);
       }catch (Exception e){
           eqps=null;
           e.printStackTrace();
       }
      return eqps;
   }
    /**
     * 修改设备信息
     */
    public boolean setEqpInfo(Eqp eqp){
        if (eqp.getId()==null){
            return false;
        }
        if (eqp.getState()==null){
            return false;
        }
        if (eqp.getLabid()==null){
            return false;
        }
        if (eqp.getName()==null){
            return false;
        }
        if (eqpMapper.setEqpInfo(eqp)>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 删除设备 假删除
     */
    public boolean deleteEqp(Integer id){
        if(eqpMapper.deleteEqp(id)>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 增加
     */
    public boolean addEqp(Eqp eqp){
        if (eqp.getLabid()==null){
            return false;
        }
        if (eqp.getName()==null){
            return false;
        }
        eqp.setIsdel(1);
        eqp.setDate(new Date());
        eqp.setState(1);
        if(eqpMapper.addEqp(eqp)>0){
            return true;
        }else{
            return false;
        }
    }

}
