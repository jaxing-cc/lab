package com.example.labmanage_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 维修表
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Declare implements Serializable {
    private Integer id;
    private Integer uid;
    private String reason;
    private Integer done;
    private Integer eid;
    private Date date;
    private Integer master;
    private User user;
    private Eqp eqp;
    private User mast;

    @Override
    public String toString() {
        return "Declare{" +
                "id=" + id +
                ", uid=" + uid +
                ", reason='" + reason + '\'' +
                ", done=" + done +
                ", eid=" + eid +
                ", date=" + date +
                ", master=" + master +
                ", user=" + user +
                ", eqp=" + eqp +
                ", mast=" + mast +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Eqp getEqp() {
        return eqp;
    }

    public void setEqp(Eqp eqp) {
        this.eqp = eqp;
    }

    public User getMast() {
        return mast;
    }

    public void setMast(User mast) {
        this.mast = mast;
    }

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMaster() {
        return master;
    }

    public void setMaster(Integer master) {
        this.master = master;
    }
}
