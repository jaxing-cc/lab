package com.example.labmanage_server.service;

import com.example.labmanage_server.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.labmanage_server.domain.Log;

import java.util.Date;
import java.util.List;

@Service
public class LogServer {
    @Autowired
    LogMapper logMapper;

    public  List<Log> getAll(Date date){
        List<Log> logs=null;
        try{
            logs= logMapper.getAll(date);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  logs;
    }
}
