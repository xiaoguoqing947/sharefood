package com.example.sharefood.controller;

import com.example.sharefood.domain.*;
import com.example.sharefood.service.inter.*;
import com.example.sharefood.util.PersonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
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
        /*查询所有图鉴信息*/
        List<Tujian> tujianList = tujianSer.findList(searchMap);
        /*查询所有美食信息*/
        List<MeiShi> meiShiList = meiShiSer.findCurrentMs();
        model.addAttribute("meiShiList", meiShiList);
        model.addAttribute("tujianList", tujianList);
        return "index";
    }

    @GetMapping("/food")
    public String index1(@RequestParam(value = "isAdmin", required = false) String statu, @RequestParam(value = "type", required = false) String type, @RequestParam(value = "tag", required = false) String tag, Model model) {
        if (statu == null || "unlogin".equals(statu)) {
            model.addAttribute("statu", "unlogin");
        } else if (statu.equals("yes") || statu.equals("admin")) {
            model.addAttribute("statu", "admin");
        } else if (statu.equals("no") || statu.equals("user")) {
            model.addAttribute("statu", "user");
        }
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("tag", tag);
        searchMap.put("type", type);
        searchMap.put("fb", "1");
        /*查询指定条件的美食信息*/
        List<MeiShi> meiShiList = meiShiSer.listMeiShi(searchMap);
        model.addAttribute("meiShiList", meiShiList);
        return "index-1";
    }

    @GetMapping("/detailfood")
    public String index2(@RequestParam(value = "isAdmin", required = false) String statu, @RequestParam(value = "detail", required = false) String id, Model model, HttpServletRequest request) {
        if (statu == null || "unlogin".equals(statu)) {
            model.addAttribute("statu", "unlogin");
        } else if (statu.equals("yes") || statu.equals("admin")) {
            model.addAttribute("statu", "admin");
        } else if (statu.equals("no") || statu.equals("user")) {
            model.addAttribute("statu", "user");
            /*获取用户ID*/
            Customer customer = (Customer) request.getSession().getAttribute("admin");
            /*根据美食ID查找相关美食的收藏，点赞信息*/
            LikeTable likeTable = likeTableSer.findLikeTableByMsId(id);
            if (likeTable != null) {
                String isLikeUsers = likeTable.getIslike();
                String isStartUsers = likeTable.getIsstart();
                if (isLikeUsers == null) {
                    model.addAttribute("zan", "no");
                } else {
                    LinkedList<Integer> likeList = PersonUtils.getNumberInStr(isLikeUsers);
                    if (likeList.contains(customer.getId())) {
                        model.addAttribute("zan", "yes");
                    } else {
                        model.addAttribute("zan", "no");
                    }
                }
                if (isStartUsers == null) {
                    model.addAttribute("collect", "no");
                } else {
                    LinkedList<Integer> startList = PersonUtils.getNumberInStr(isStartUsers);
                    if (startList.contains(customer.getId())) {
                        model.addAttribute("collect", "yes");
                    } else {
                        model.addAttribute("collect", "no");
                    }
                }
            }
        }
        /*根据美食ID查找相关美食的详细信息*/
        MeiShi meiShi = meiShiSer.findMsById(id);
        meiShi.setTypes(dicsSer.findDicsNameByValueAndType("type", meiShi.getTypes()));
        meiShi.setIsfb(dicsSer.findDicsNameByValueAndType("fb", meiShi.getIsfb()));
        meiShi.setIsdiscount(dicsSer.findDicsNameByValueAndType("discount", meiShi.getIsdiscount()));
        meiShi.setRecommendcrowd(dicsSer.findDicsNameByValueAndType("crowl", meiShi.getRecommendcrowd()));
        model.addAttribute("meiShi", meiShi);
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
        searchMap.put("fb", "1");
        /*查询所有美食收藏，点赞，浏览的一些记录统计*/
        List<LikeTable> likeTableList = likeTableSer.findList();
        for (int i = 0; i < likeTableList.size(); i++) {
            /*通过自定义的函数对数据库收藏字段和点赞字段进行统计 */
            if (likeTableList.get(i).getIslike() == null) {
                likeTableList.get(i).setIslike("0");
            } else {
                LinkedList<Integer> listLike = PersonUtils.getNumberInStr(likeTableList.get(i).getIslike());
                likeTableList.get(i).setIslike(String.valueOf(listLike.size()));
            }
            if (likeTableList.get(i).getIsstart() == null) {
                likeTableList.get(i).setIsstart("0");
            } else {
                LinkedList<Integer> listStart = PersonUtils.getNumberInStr(likeTableList.get(i).getIsstart());
                likeTableList.get(i).setIsstart(String.valueOf(listStart.size()));
            }
        }
        /*查询用户自定义的所有标签*/
        List<Tag> tagList = tagSer.findList();
        /*查询所有美食*/
        List<MeiShi> meiShiList = meiShiSer.listMeiShi(searchMap);
        for (int i = 0; i < meiShiList.size(); i++) {
            /*标签的没有设置*/
            meiShiList.get(i).setTypes(dicsSer.findDicsNameByValueAndType("type", meiShiList.get(i).getTypes()));
            meiShiList.get(i).setMstag(tagSer.findTagById(Integer.parseInt(meiShiList.get(i).getMstag())));
        }
        model.addAttribute("tagList", tagList);
        model.addAttribute("likeTableList", likeTableList);
        model.addAttribute("meiShiList", meiShiList);
        return "index-3";
    }
}
