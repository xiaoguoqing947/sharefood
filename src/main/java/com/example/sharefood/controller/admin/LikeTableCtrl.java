package com.example.sharefood.controller.admin;

import com.example.sharefood.domain.Customer;
import com.example.sharefood.domain.LikeTable;
import com.example.sharefood.service.inter.LikeTableSer;
import com.example.sharefood.util.PersonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class LikeTableCtrl {

    @Autowired
    private LikeTableSer likeTableSer;

    /*用户浏览记录添加*/
    @ResponseBody
    @PostMapping("/liketable/addView")
    public Map<String, Object> addView(@RequestParam("msId") String msId, @RequestParam("view") String view, HttpServletRequest request) {
        System.out.println(msId + "-" + view);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        LikeTable likeTable = likeTableSer.findLikeTableByMsId(msId);
        if (likeTable == null) {
            LikeTable lTable = new LikeTable();
            lTable.setMsid(Integer.parseInt(msId));
            lTable.setIsview(Integer.parseInt(view));
            if (likeTableSer.addLikeTable(lTable)) {
                resultMap.put("status", "addSuccess");
            } else {
                resultMap.put("status", "addFail");
            }
        } else {
            likeTable.setMsid(Integer.parseInt(msId));
            likeTable.setIsview(likeTable.getIsview() + 1);
            if (likeTableSer.updateLikeTable(likeTable)) {
                resultMap.put("status", "updateSuccess");
            } else {
                resultMap.put("status", "updateFail");
            }
        }
        return resultMap;
    }

    /*用户点赞记录添加*/
    @ResponseBody
    @PostMapping("/api/liketable/addLike")
    public Map<String, Object> addLike(@RequestParam("msId") String msId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Customer customer = (Customer) request.getSession().getAttribute("admin");
        if (customer != null) {
            int userId = customer.getId();
            LikeTable likeTable = likeTableSer.findLikeTableByMsId(msId);
            if (likeTable == null) {
                LikeTable lTable = new LikeTable();
                lTable.setMsid(Integer.parseInt(msId));
                lTable.setIslike(String.valueOf(userId));
                if (likeTableSer.addLikeTable(lTable)) {
                    resultMap.put("status", "success");
                } else {
                    resultMap.put("status", "fail");
                }
            } else {
                likeTable.setMsid(Integer.parseInt(msId));
                if (likeTable.getIslike() == null || "".equals(likeTable.getIslike())) {
                    likeTable.setIslike(String.valueOf(userId));
                }else{
                    likeTable.setIslike(likeTable.getIslike() + "," + userId);
                }
                if (likeTableSer.updateLikeTable(likeTable)) {
                    resultMap.put("status", "success");
                } else {
                    resultMap.put("status", "fail");
                }
            }
        }
        return resultMap;
    }
    /*用户点赞记录删除*/
    @ResponseBody
    @PostMapping("/api/liketable/deleteLike")
    public Map<String, Object> deleteLike(@RequestParam("msId") String msId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Customer customer = (Customer) request.getSession().getAttribute("admin");
        if (customer != null) {
            int userId = customer.getId();
            LikeTable likeTable = likeTableSer.findLikeTableByMsId(msId);
            String likeUser=likeTable.getIslike();
            LinkedList<Integer> list=PersonUtils.getNumberInStr(likeUser);
            if(list.contains(userId)){
                for(int i=0;i<list.size();i++){
                    if(list.get(i) == userId){
                        list.remove(i);
                    }
                }
            }
            StringBuffer sBuffer = new StringBuffer();
            for(int i=0;i< list.size();i++){
                if(i == list.size()-1){
                    sBuffer.append(list.get(i));
                    break;
                }
                sBuffer.append(list.get(i)+",");
            }
            likeTable.setIslike(String.valueOf(sBuffer));
            if (likeTableSer.updateLikeTable(likeTable)) {
                resultMap.put("status", "success");
            } else {
                resultMap.put("status", "fail");
            }
        }
        return resultMap;
    }

    /*用户收藏记录添加*/
    @ResponseBody
    @PostMapping("/api/liketable/addCollect")
    public Map<String, Object> addCollect(@RequestParam("msId") String msId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Customer customer = (Customer) request.getSession().getAttribute("admin");
        if (customer != null) {
            int userId = customer.getId();
            LikeTable likeTable = likeTableSer.findLikeTableByMsId(msId);
            if (likeTable == null) {
                LikeTable lTable = new LikeTable();
                lTable.setMsid(Integer.parseInt(msId));
                lTable.setIsstart(String.valueOf(userId));
                if (likeTableSer.addLikeTable(lTable)) {
                    resultMap.put("status", "success");
                } else {
                    resultMap.put("status", "fail");
                }
            } else {
                likeTable.setMsid(Integer.parseInt(msId));
                if (likeTable.getIsstart() == null || "".equals(likeTable.getIsstart())) {
                    likeTable.setIsstart(String.valueOf(userId));
                }else{
                    likeTable.setIsstart(likeTable.getIsstart() + "," + userId);
                }
                if (likeTableSer.updateLikeTable(likeTable)) {
                    resultMap.put("status", "success");
                } else {
                    resultMap.put("status", "fail");
                }
            }
        }
        return resultMap;
    }

    /*用户收藏记录删除*/
    @ResponseBody
    @PostMapping("/api/liketable/deleteCollect")
    public Map<String, Object> deleteCollect(@RequestParam("msId") String msId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Customer customer = (Customer) request.getSession().getAttribute("admin");
        if (customer != null) {
            int userId = customer.getId();
            LikeTable likeTable = likeTableSer.findLikeTableByMsId(msId);
            String likeUser=likeTable.getIsstart();
            LinkedList<Integer> list=PersonUtils.getNumberInStr(likeUser);
            if(list.contains(userId)){
                for(int i=0;i<list.size();i++){
                    if(list.get(i) == userId){
                        list.remove(i);
                    }
                }
            }
            StringBuffer sBuffer = new StringBuffer();
            for(int i=0;i< list.size();i++){
                if(i == list.size()-1){
                    sBuffer.append(list.get(i));
                    break;
                }
                sBuffer.append(list.get(i)+",");
            }
            likeTable.setIsstart(String.valueOf(sBuffer));
            if (likeTableSer.updateLikeTable(likeTable)) {
                resultMap.put("status", "success");
            } else {
                resultMap.put("status", "fail");
            }
        }
        return resultMap;
    }
}
