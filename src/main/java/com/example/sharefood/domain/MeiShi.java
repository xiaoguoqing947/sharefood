package com.example.sharefood.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class MeiShi {
    private Integer id;

    private String types;

    private String msname;

    private String content;

    private String mspic;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-mm-dd HH:mm")
    private Date insertdate;

    private String senduser;

    private String mstag;

    private String msaddress;

    private String isfb;

    private String isdiscount;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-mm-dd HH:mm")
    private Date discountstime;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-mm-dd HH:mm")
    private Date discountetime;

    private String msnumber;

    private String recommendcrowd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types == null ? null : types.trim();
    }

    public String getMsname() {
        return msname;
    }

    public void setMsname(String msname) {
        this.msname = msname == null ? null : msname.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getMspic() {
        return mspic;
    }

    public void setMspic(String mspic) {
        this.mspic = mspic == null ? null : mspic.trim();
    }

    public Date getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(Date insertdate) {
        this.insertdate = insertdate;
    }

    public String getSenduser() {
        return senduser;
    }

    public void setSenduser(String senduser) {
        this.senduser = senduser == null ? null : senduser.trim();
    }

    public String getMstag() {
        return mstag;
    }

    public void setMstag(String mstag) {
        this.mstag = mstag == null ? null : mstag.trim();
    }

    public String getMsaddress() {
        return msaddress;
    }

    public void setMsaddress(String msaddress) {
        this.msaddress = msaddress == null ? null : msaddress.trim();
    }

    public String getIsfb() {
        return isfb;
    }

    public void setIsfb(String isfb) {
        this.isfb = isfb == null ? null : isfb.trim();
    }

    public String getIsdiscount() {
        return isdiscount;
    }

    public void setIsdiscount(String isdiscount) {
        this.isdiscount = isdiscount == null ? null : isdiscount.trim();
    }

    public Date getDiscountstime() {
        return discountstime;
    }

    public void setDiscountstime(Date discountstime) {
        this.discountstime = discountstime;
    }

    public Date getDiscountetime() {
        return discountetime;
    }

    public void setDiscountetime(Date discountetime) {
        this.discountetime = discountetime;
    }

    public String getMsnumber() {
        return msnumber;
    }

    public void setMsnumber(String msnumber) {
        this.msnumber = msnumber == null ? null : msnumber.trim();
    }

    public String getRecommendcrowd() {
        return recommendcrowd;
    }

    public void setRecommendcrowd(String recommendcrowd) {
        this.recommendcrowd = recommendcrowd == null ? null : recommendcrowd.trim();
    }
}