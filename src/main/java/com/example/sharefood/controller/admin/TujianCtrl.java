package com.example.sharefood.controller.admin;

import com.example.sharefood.domain.Tujian;
import com.example.sharefood.domain.dto.tj.SearchTjForm;
import com.example.sharefood.service.inter.TujianSer;
import com.example.sharefood.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/api/tujian")
public class TujianCtrl {
    @Autowired
    private TujianSer tujianSer;

    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String, Object> queryListUrl(@RequestBody SearchTjForm tjForm, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            resultMap.put("result", "success");
            if (tjForm.getCurrentPage() == null || "".equals(tjForm.getCurrentPage().trim())) {
                tjForm.setCurrentPage("0");
            }
            if (tjForm.getPageSize() == null || "".equals(tjForm.getPageSize().trim())) {
                tjForm.setPageSize("10");
            }
            tjForm.setIndexCount(Integer.parseInt(tjForm.getCurrentPage()) * Integer.parseInt(tjForm.getPageSize()));
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("currentPage", Integer.parseInt(tjForm.getCurrentPage()));
            searchMap.put("pageSize", Integer.parseInt(tjForm.getPageSize()));
            searchMap.put("indexCount", tjForm.getIndexCount());
            searchMap.put("picName", tjForm.getPicName());
            int totalCount = tujianSer.findCount(searchMap);
            Map<String, Object> resultPagerMap = new HashMap<String, Object>();
            resultPagerMap.put("page", tjForm.getCurrentPage() + 1);
            resultPagerMap.put("recTotal", totalCount);
            resultPagerMap.put("recPerPage", tjForm.getPageSize());
            resultMap.put("pager", resultPagerMap);
            if (totalCount > 0) {
                List<Tujian> tujianList = tujianSer.listTujian(searchMap);
                resultMap.put("data", tujianList);
            } else {
                resultMap.put("message", "查无数据");
            }
        } else {
            resultMap.put("message", "请上传参数");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/initUpdate")
    public Map<String, Object> initUpdate(@RequestParam("id") String id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            Map<String, Object> searchMap = new HashMap<String, Object>();
            Tujian tujian = tujianSer.findTj(id);
            resultMap.put("tujian", tujian);
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
//        System.out.println(resultMap);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String, Object> update(@RequestParam("pic") MultipartFile file, HttpServletRequest request, @RequestParam Map param) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            Tujian tujian = new Tujian();
            System.err.println("传过来的值" + file);
            if (file.isEmpty()) {
                System.err.println("上传文件不可为空");
            }
            String fileName = file.getOriginalFilename();//得到文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));//得到后缀名
            System.err.println("suffixName:" + suffixName);
            String filepath = "E:/sharefood/src/main/resources/static/images/tujian/";//指定图片上传到哪个文件夹的路径
            fileName = UUID.randomUUID() + suffixName;//重新命名图片，变成随机的名字
            System.err.println("fileName:" + fileName);
            File dest = new File(filepath + fileName);//在上传的文件夹处创建文件
            try {
                file.transferTo(dest);//把上传的图片写入磁盘中
            } catch (IOException e) {
                e.printStackTrace();
            }
            tujian.setPic("/images/tujian/" + fileName);
            tujian.setId(Integer.parseInt((String) param.get("id")));
            tujian.setTitle((String) param.get("title"));
            tujian.setTjtype((String) param.get("tjtype"));
            tujian.setTjdesc((String) param.get("tjdesc"));
            if (tujianSer.updateTj(tujian)) {
                resultMap.put("code", 200);
            }
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/detail")
    public Map<String, Object> detailMenu(@RequestParam("id") String id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            resultMap.put("success", "1");
            Tujian tujian = tujianSer.findTj(id);
            resultMap.put("tujian", tujian);
        } else {
            resultMap.put("success", "0");
        }
        return resultMap;
    }
}
