package com.example.sharefood.domain;

public class LikeTable {
    private Integer id;

    private Integer msid;

    private Integer islike;

    private Integer unlike;

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

    public Integer getIslike() {
        return islike;
    }

    public void setIslike(Integer islike) {
        this.islike = islike;
    }

    public Integer getUnlike() {
        return unlike;
    }

    public void setUnlike(Integer unlike) {
        this.unlike = unlike;
    }

    public Integer getIsview() {
        return isview;
    }

    public void setIsview(Integer isview) {
        this.isview = isview;
    }
}