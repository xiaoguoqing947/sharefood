package com.example.sharefood.domain;

public class LikeTable {
    private Integer id;

    private Integer msid;

    private String islike;

    private String isstart;

    private Integer isview;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMsid() {
        return msid;
    }

    public void setMsid(Integer msid) {
        this.msid = msid;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike == null ? null : islike.trim();
    }

    public String getIsstart() {
        return isstart;
    }

    public void setIsstart(String isstart) {
        this.isstart = isstart == null ? null : isstart.trim();
    }

    public Integer getIsview() {
        return isview;
    }

    public void setIsview(Integer isview) {
        this.isview = isview;
    }
}