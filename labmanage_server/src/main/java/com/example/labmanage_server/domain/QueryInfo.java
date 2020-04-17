package com.example.labmanage_server.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class QueryInfo implements Serializable {
    private  Integer labid;
    private  Integer pagenum;
    private  Integer pagesize;
    private  String query;
    private  Integer uid;
    private Integer id;
    private Integer done;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    private Integer eid;
    private String name;

    @Override
    public String toString() {
        return "QueryInfo{" +
                "labid=" + labid +
                ", pagenum=" + pagenum +
                ", pagesize=" + pagesize +
                ", query='" + query + '\'' +
                ", uid=" + uid +
                ", id=" + id +
                ", done=" + done +
                ", date=" + date +
                ", eid=" + eid +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getLabid() {
        return labid;
    }

    public void setLabid(Integer labid) {
        this.labid = labid;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }
}
