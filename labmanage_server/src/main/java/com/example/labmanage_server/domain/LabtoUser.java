package com.example.labmanage_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 实验室和人员的对应关系实体类
 * 作用: 绑定实验室和人员的关系
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class LabtoUser {
    private Integer id;
    private Integer labid;
    private Integer userid;
    private String className;
    private String stuNum;
    private Lab lab;
    private User user;
    private String tag;

    @Override
    public String toString() {
        return "LabtoUser{" +
                "id=" + id +
                ", labid=" + labid +
                ", userid=" + userid +
                ", className='" + className + '\'' +
                ", stuNum='" + stuNum + '\'' +
                ", lab=" + lab +
                ", user=" + user +
                ", tag='" + tag + '\'' +
                '}';
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabid() {
        return labid;
    }

    public void setLabid(Integer labid) {
        this.labid = labid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }
}
