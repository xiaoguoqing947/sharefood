package com.example.sharefood.controller;

import com.example.sharefood.domain.Tujian;
import com.example.sharefood.mapping.TujianMapper;
import com.example.sharefood.service.inter.TujianSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexCtrl {

    @Autowired
    private TujianSer tujianSer;

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
        model.addAttribute("tujianList",tujianList);
        return "index";
    }

    @GetMapping("/food")
    public String index1(@RequestParam(value = "isAdmin", required = false) String statu, Model model) {
        if (statu == null || "unlogin".equals(statu)) {
            model.addAttribute("statu", "unlogin");
        } else if (statu.equals("yes") || statu.equals("admin")) {
            model.addAttribute("statu", "admin");
        } else if (statu.equals("no") || statu.equals("user")) {
            model.addAttribute("statu", "user");
        }
        return "index-1";
    }

    @GetMapping("/detailfood")
    public String index2(@RequestParam(value = "isAdmin", required = false) String statu, Model model) {
        if (statu == null || "unlogin".equals(statu)) {
            model.addAttribute("statu", "unlogin");
        } else if (statu.equals("yes") || statu.equals("admin")) {
            model.addAttribute("statu", "admin");
        } else if (statu.equals("no") || statu.equals("user")) {
            model.addAttribute("statu", "user");
        }
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
        return "index-3";
    }
}
