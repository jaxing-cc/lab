package com.example.labmanage_server.domain;

import java.io.Serializable;

/**
 * 主页功能控制
 */
public class Control implements Serializable {
   private Integer id;
    private String info;
    private int rank;
    private String address;

    @Override
    public String toString() {
        return "Control{" +
                "id=" + id +
                ", info='" + info + '\'' +
                ", rank=" + rank +
                ", address='" + address + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
