package com.example.sharefood.domain;

public class Tujian {
    private Integer id;

    private String title;

    private String pic;

    private String tjtype;

    private String tjdesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getTjtype() {
        return tjtype;
    }

    public void setTjtype(String tjtype) {
        this.tjtype = tjtype == null ? null : tjtype.trim();
    }

    public String getTjdesc() {
        return tjdesc;
    }

    public void setTjdesc(String tjdesc) {
        this.tjdesc = tjdesc == null ? null : tjdesc.trim();
    }
}