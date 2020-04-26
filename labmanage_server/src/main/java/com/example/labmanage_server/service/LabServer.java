package com.example.labmanage_server.service;

import com.example.labmanage_server.domain.*;
import com.example.labmanage_server.mapper.EqpMapper;
import com.example.labmanage_server.mapper.LabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 查询一个用有实验室是加入实验室
     * @param id
     * @return
     */
    public List<Blog> getBlogs(Integer id){
        LabtoUser lu = labMapper.getLuByUserId(id);
        if (lu==null){
            return null;
        }else{
            //查询博客
            ArrayList<Blog> list=new ArrayList<>();
            list.add(new Blog());//查询操作
            return list;
        }
    }
    /**
     * 让一个用户加入某实验室
     */
    public boolean addUsertoLab(LabtoUser lu){
        LabtoUser lued = labMapper.getLuByUserId(lu.getUserid());
        if (lued!=null){
            return false;
        }
        if (labMapper.addUserToLab(lu)>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 查询实验室人员
     */
    public List<LabtoUser> getUserByLabid(LabtoUser labtoUser){
        return labMapper.getUserBylabid(labtoUser);
    }
    /**
     * 设置标签
     */
    public boolean setTag(LabtoUser labtoUser){
        if (labMapper.setTag(labtoUser)>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 删除人员
     */
    public boolean deleteUser(LabtoUser labtoUser){
        if (labMapper.deleteUser(labtoUser)>0){
            return true;
        }else{
            return false;
        }
    }
}
