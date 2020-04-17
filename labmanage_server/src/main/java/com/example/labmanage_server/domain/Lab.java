package com.example.labmanage_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Lab implements Serializable {
    private Integer id;
    private String name;
    private Integer uid;
    private List<Eqp> eqps;
    private User master;

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public List<Eqp> getEqps() {
        return eqps;
    }

    public void setEqps(List<Eqp> eqps) {
        this.eqps = eqps;
    }

    @Override
    public String toString() {
        return "Lab{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uid=" + uid +
                ", eqps=" + eqps +
                ", master=" + master +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
