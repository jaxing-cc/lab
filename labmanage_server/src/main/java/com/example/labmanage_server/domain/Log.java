package com.example.labmanage_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Log {
    private Integer id;
    private Integer did;
    private  Integer uid;
    private User user;
    private Date date;
    private  Declare declare;

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", did=" + did +
                ", uid=" + uid +
                ", user=" + user +
                ", date=" + date +
                ", declare=" + declare +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Declare getDeclare() {
        return declare;
    }

    public void setDeclare(Declare declare) {
        this.declare = declare;
    }
}
