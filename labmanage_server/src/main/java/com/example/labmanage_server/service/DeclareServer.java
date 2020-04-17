package com.example.labmanage_server.service;

import com.example.labmanage_server.domain.Declare;
import com.example.labmanage_server.domain.Eqp;
import com.example.labmanage_server.domain.User;
import com.example.labmanage_server.mapper.DeclareMapper;
import com.example.labmanage_server.mapper.EqpMapper;
import com.example.labmanage_server.mapper.LogMapper;
import com.example.labmanage_server.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeclareServer {
    @Autowired
    DeclareMapper declareMapper;

    @Autowired
    EqpMapper eqpMapper;

    @Autowired
    UserMapper usermapper=null;

    @Autowired
    LogMapper logMapper;
    /**
     * 添加一个declare表
     * @param declare
     * @return
     */
    public boolean addDeclare(Declare declare){
        if (declare.getEid()==null||declare.getUid()==null||declare.getReason()==null){
            return false;
        }
        //设置当前时间
        declare.setDate(new Date());
        //设置处理状态
        declare.setDone(0);
        //获取实验室管理人
        Eqp eqpById;
        try{
          eqpById =  eqpMapper.getEqpById(declare.getEid());//获取被申报设备
            if (eqpById==null){return false;}
            System.out.println(eqpById);
            if (eqpById.getLab().getMaster().getId()!=null){
                declare.setMaster(eqpById.getLab().getMaster().getId());
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            eqpById=null;
        }
        if (eqpById==null){return false;}

      //持久化
       if(declareMapper.addDeclare(declare)>0){
           return true;
       }else{
           return false;
       }
    }

    public List<Declare> getByUid(Integer uid){
        List<Declare> declares;
        if (uid==null){
            return null;
        }
        try{
            declares=declareMapper.getDeclare(uid);
        }catch (Exception e){
            declares=null;
            e.printStackTrace();
        }
        return declares;
    }

    public boolean deleteDeclare(Integer id){
        if(declareMapper.deleteDeclare(id)>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 拿到所有申报表By done
     *
     */
    public List<Declare> getAllByState (Integer done,Date date){
        List<Declare> declares = null;
        try{
            declares=declareMapper.getAllByState(done,date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return declares;
    }

    /**
     * ***main
     * 处理申报！
     * 状态值，申报表id 处理人id 设备id
     */
    public boolean setDone(Integer done,Integer id,Integer uid,Integer eid){
        Declare declare=declareMapper.getById(id);
        if (declare==null){
            return false;
        }
        //设置申报表状态
        if (declareMapper.setDone(done,id)>0){
            if (done==4){//设备已经维修完毕
                if(logMapper.addLog(id,uid,new Date())>0){//添加日志
                    eqpMapper.setEqpState(1, eid);//把设备改成健康
                        return true;
                }else{
                    declareMapper.setDone(declare.getDone(),id);
                    eqpMapper.setEqpState(1, eid);//把设备改成健康
                    return false;
                }
            }
            if (done==1||done==2||done==3){//设备改成未认证状态
                eqpMapper.setEqpState(0, eid);//把设备改成故障
                    return true;
            }
            if (done==0){
                return true;
            }
            declareMapper.setDone(declare.getDone(),id);
            return false;
        }else{
            return false;
        }
    }
}
