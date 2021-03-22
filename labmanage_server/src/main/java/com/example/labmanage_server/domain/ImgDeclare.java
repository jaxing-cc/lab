package com.example.labmanage_server.domain;

import java.io.Serializable;

public class ImgDeclare implements Serializable {
    private Integer id;
    private Integer did;
    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ImgDeclare{" +
                "id=" + id +
                ", did=" + did +
                ", path='" + path + '\'' +
                '}';
    }
}
