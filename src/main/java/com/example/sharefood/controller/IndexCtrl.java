package com.example.sharefood.controller;

import com.example.sharefood.domain.*;
import com.example.sharefood.service.inter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexCtrl {
    private TujianSer tujianSer;
    private MeiShiSer meiShiSer;
    private DicsSer dicsSer;
    private TagSer tagSer;
    private LikeTableSer likeTableSer;
    @Autowired
    public IndexCtrl(TujianSer tujianSer, MeiShiSer meiShiSer, DicsSer dicsSer, TagSer tagSer, LikeTableSer likeTableSer) {
        this.tujianSer = tujianSer;
        this.meiShiSer = meiShiSer;
        this.dicsSer = dicsSer;
        this.tagSer = tagSer;
        this.likeTableSer = likeTableSer;
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "isAdmin", required = false) String statu, Model model) {
        if (statu == null) {
            model.addAttribute("statu", "unlogin");
        } else if (statu.equals("yes") || statu.equals("admin")) {
            model.addAttribute("statu", "admin");
        } else if (statu.equals("no") || statu.equals("user")) {
            model.addAttribute("statu", "user");
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        List<Tujian> tujianList = tujianSer.findList(searchMap);
        List<MeiShi> meiShiList=meiShiSer.findCurrentMs();
        model.addAttribute("meiShiList",meiShiList);
        model.addAttribute("tujianList",tujianList);
        return "index";
    }

    @GetMapping("/food")
    public String index1(@RequestParam(value = "isAdmin", required = false) String statu,@RequestParam(value = "type", required = false) String type,@RequestParam(value = "tag", required = false) String tag, Model model) {
        if (statu == null || "unlogin".equals(statu)) {
            model.addAttribute("statu", "unlogin");
        } else if (statu.equals("yes") || statu.equals("admin")) {
            model.addAttribute("statu", "admin");
        } else if (statu.equals("no") || statu.equals("user")) {
            model.addAttribute("statu", "user");
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("tag",tag);
        searchMap.put("type",type);
        searchMap.put("fb","1");
        List<MeiShi>  meiShiList=meiShiSer.listMeiShi(searchMap);
        model.addAttribute("meiShiList",meiShiList);
        return "index-1";
    }

    @GetMapping("/detailfood")
    public String index2(@RequestParam(value = "isAdmin", required = false) String statu,@RequestParam(value = "detail", required = false) String id, Model model) {
        if (statu == null || "unlogin".equals(statu)) {
            model.addAttribute("statu", "unlogin");
        } else if (statu.equals("yes") || statu.equals("admin")) {
            model.addAttribute("statu", "admin");
        } else if (statu.equals("no") || statu.equals("user")) {
            model.addAttribute("statu", "user");
        }
        MeiShi meiShi = meiShiSer.findMsById(id);
        meiShi.setTypes(dicsSer.findDicsNameByValueAndType("type", meiShi.getTypes()));
        meiShi.setIsfb(dicsSer.findDicsNameByValueAndType("fb", meiShi.getIsfb()));
        meiShi.setIsdiscount(dicsSer.findDicsNameByValueAndType("discount", meiShi.getIsdiscount()));
        meiShi.setRecommendcrowd(dicsSer.findDicsNameByValueAndType("crowl", meiShi.getRecommendcrowd()));
        model.addAttribute("meiShi",meiShi);
        return "index-2";
    }

    @GetMapping("/foodblog")
    public String index3(@RequestParam(value = "isAdmin", required = false) String statu, Model model) {
        if (statu == null || "unlogin".equals(statu)) {
            model.addAttribute("statu", "unlogin");
        } else if (statu.equals("yes") || statu.equals("admin")) {
            model.addAttribute("statu", "admin");
        } else if (statu.equals("no") || statu.equals("user")) {
            model.addAttribute("statu", "user");
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("fb","1");
        List<LikeTable> likeTableList=likeTableSer.findList();
        List<Tag> tagList=tagSer.findList();
        List<MeiShi>  meiShiList=meiShiSer.listMeiShi(searchMap);
        for (int i = 0; i < meiShiList.size(); i++) {
            /*标签的没有设置*/
            meiShiList.get(i).setTypes(dicsSer.findDicsNameByValueAndType("type", meiShiList.get(i).getTypes()));
            meiShiList.get(i).setMstag(tagSer.findTagById(Integer.parseInt(meiShiList.get(i).getMstag())));
        }
        model.addAttribute("tagList",tagList);
        model.addAttribute("likeTableList",likeTableList);
        model.addAttribute("meiShiList",meiShiList);
        return "index-3";
    }
}
