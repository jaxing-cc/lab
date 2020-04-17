package com.example.labmanage_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

public class Notice {
    private Integer id;
    private Integer uid;
    private  String info;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", uid=" + uid +
                ", info='" + info + '\'' +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}
