package com.example.labmanage_server.service;

import com.example.labmanage_server.domain.Notice;
import com.example.labmanage_server.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServer {
    @Autowired
    NoticeMapper mapper;

    /**
     * 添加一个公告
     * @param notice
     * @return
     */
    public boolean add(Notice notice){
        if (mapper.add(notice)>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 删除一个公告
     */
    public boolean delete(Integer id){
        if (mapper.delete(id)>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 获取所有
     */
    public List<Notice> getAll(){
        return mapper.getAll();
    }
}
