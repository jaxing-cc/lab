package com.example.labmanage_server;

import com.example.labmanage_server.domain.*;
import com.example.labmanage_server.mapper.*;
import com.example.labmanage_server.service.DeclareServer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("all")
class LabmanageServerApplicationTests {

    @Autowired
    EqpMapper eqpMapper;
    @Autowired
    DeclareServer declareServer;
@Autowired
DeclareMapper declareMapper;
    @Autowired
    LabMapper labMapper;

    @Autowired
    LogMapper logMapper;

    @Autowired
    NoticeMapper noticeMapper;
    @Test
    void contextLoads() {
        String dateStr="2020-02-27";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
          date=  simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("时间转化出错了");
        }
        List<Declare> allByState = declareMapper.getAllByState(0, date);
        for (Declare declare : allByState) {
            System.out.println(declare);
        }
    }


    @Test
    public void test(){
        List<Eqp> eqpByLabId = eqpMapper.getEqpByLabId(1,"2");
        for (Eqp eqp : eqpByLabId) {
            System.out.println(eqp);
        }
    }

    @Test
    public void testMain(){
        System.out.println("你好");
    }
    @Test
    public void test2(){
        String dateStr="2020-01-1";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date=  simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("时间转化出错了");
        }
        List<Log> all = logMapper.getAll(date);
        for (Log log : all) {
            System.out.println(log.getUser());
            System.out.println(log.getDeclare());
            System.out.println(log.getDeclare().getEqp());
            System.out.println(log.getDeclare().getEqp().getLab());
            System.out.println(log.getDeclare().getUser());
        }
//        Declare byId = declareMapper.getById(10);
//        System.out.println(byId);
    }

    @Test
    public void testNotice(){
        List<Notice> all = noticeMapper.getAll();
        for (Notice notice : all) {
            System.out.println(notice);
        }
    }
}
