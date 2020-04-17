package com.example.labmanage_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Eqp implements Serializable {
    private Integer id;
    private  Integer state;
    private  Integer labid;
    private  String name;
    private  Date date;
    private Integer isdel;
    private Lab lab;

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    @Override
    public String toString() {
        return "Eqp{" +
                "id=" + id +
                ", state=" + state +
                ", labid=" + labid +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", isdel=" + isdel +
                ", lab=" + lab +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLabid() {
        return labid;
    }

    public void setLabid(Integer labid) {
        this.labid = labid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}
