package com.example.sharefood.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PingLun {
    private Integer id;

    private Integer msid;

    private String ownerid;

    private String fromid;

    private String fromname;

    private String fromavatar;

    private String content;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-mm-dd HH:mm")
    private Date insertdate;

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

    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid == null ? null : ownerid.trim();
    }

    public String getFromid() {
        return fromid;
    }

    public void setFromid(String fromid) {
        this.fromid = fromid == null ? null : fromid.trim();
    }

    public String getFromname() {
        return fromname;
    }

    public void setFromname(String fromname) {
        this.fromname = fromname == null ? null : fromname.trim();
    }

    public String getFromavatar() {
        return fromavatar;
    }

    public void setFromavatar(String fromavatar) {
        this.fromavatar = fromavatar == null ? null : fromavatar.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(Date insertdate) {
        this.insertdate = insertdate;
    }
}