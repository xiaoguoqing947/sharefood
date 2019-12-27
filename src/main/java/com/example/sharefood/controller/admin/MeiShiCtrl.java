package com.example.sharefood.controller.admin;

import com.example.sharefood.domain.*;
import com.example.sharefood.domain.Dictionary;
import com.example.sharefood.domain.dto.ms.CheckMsForm;
import com.example.sharefood.domain.dto.ms.SearchMsForm;
import com.example.sharefood.service.inter.DicsSer;
import com.example.sharefood.service.inter.MeiShiSer;
import com.example.sharefood.service.inter.TagSer;
import com.example.sharefood.util.DateUtils;
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
@RequestMapping("/api/meishi")
public class MeiShiCtrl {
    @Autowired
    private MeiShiSer meiShiSer;
    @Autowired
    private DicsSer dicsSer;
    @Autowired
    private TagSer tagSer;

    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String, Object> queryListUrl(@RequestBody SearchMsForm msForm, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            resultMap.put("result", "success");
            if (msForm.getCurrentPage() == null || "".equals(msForm.getCurrentPage().trim())) {
                msForm.setCurrentPage("0");
            }
            if (msForm.getPageSize() == null || "".equals(msForm.getPageSize().trim())) {
                msForm.setPageSize("10");
            }
            msForm.setIndexCount(Integer.parseInt(msForm.getCurrentPage()) * Integer.parseInt(msForm.getPageSize()));
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("currentPage", Integer.parseInt(msForm.getCurrentPage()));
            searchMap.put("pageSize", Integer.parseInt(msForm.getPageSize()));
            searchMap.put("indexCount", msForm.getIndexCount());
            searchMap.put("foodName", msForm.getFoodName());
            /*查询是谁上传的*/
            Customer customer = (Customer) req.getSession().getAttribute("admin");
            if (customer != null) {
                searchMap.put("user", customer.getUsername());
            } else {
                searchMap.put("user", "admin");
            }
            int totalCount = meiShiSer.findCount(searchMap);
            Map<String, Object> resultPagerMap = new HashMap<String, Object>();
            resultPagerMap.put("page", msForm.getCurrentPage() + 1);
            resultPagerMap.put("recTotal", totalCount);
            resultPagerMap.put("recPerPage", msForm.getPageSize());
            resultMap.put("pager", resultPagerMap);
            if (totalCount > 0) {
                List<MeiShi> meiShiList = meiShiSer.listMeiShi(searchMap);
                for (int i = 0; i < meiShiList.size(); i++) {
                    /*标签的没有设置*/
                    meiShiList.get(i).setTypes(dicsSer.findDicsNameByValueAndType("type", meiShiList.get(i).getTypes()));
                    meiShiList.get(i).setIsfb(dicsSer.findDicsNameByValueAndType("fb", meiShiList.get(i).getIsfb()));
                    meiShiList.get(i).setIsdiscount(dicsSer.findDicsNameByValueAndType("discount", meiShiList.get(i).getIsdiscount()));
                    meiShiList.get(i).setRecommendcrowd(dicsSer.findDicsNameByValueAndType("crowl", meiShiList.get(i).getRecommendcrowd()));
                }
                resultMap.put("data", meiShiList);
            } else {
                resultMap.put("message", "查无数据");
            }
        } else {
            resultMap.put("message", "请上传参数");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/initAdd")
    public Map<String, Object> initAdd(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            List<Dictionary> dictionarys = dicsSer.findList();
            List<Tag> tags = tagSer.findList();
            resultMap.put("dictionarys", dictionarys);
            resultMap.put("tags", tags);
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/add")
    public Map<String, Object> addMeiShi(@RequestParam("mspic") MultipartFile file, HttpServletRequest request, @RequestParam Map param) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            MeiShi meiShi = new MeiShi();
            if (file.isEmpty()) {
                System.err.println("上传文件不可为空");
            }
            String fileName = file.getOriginalFilename();//得到文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));//得到后缀名
            System.err.println("suffixName:" + suffixName);
            String filepath = "E:/sharefood/src/main/resources/static/images/meishi/";//指定图片上传到哪个文件夹的路径
            fileName = UUID.randomUUID() + suffixName;//重新命名图片，变成随机的名字
            System.err.println("fileName:" + fileName);
            File dest = new File(filepath + fileName);//在上传的文件夹处创建文件
            try {
                file.transferTo(dest);//把上传的图片写入磁盘中
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*数字字典字段*/
            meiShi.setIsfb((String) param.get("fb"));
            meiShi.setRecommendcrowd((String) param.get("recommendcrowd"));
            meiShi.setIsdiscount((String) param.get("isdiscount"));
            meiShi.setTypes((String) param.get("types"));
            /*美食标签*/
            meiShi.setMstag((String) param.get("mstag"));
            /*普通字段*/
            meiShi.setMsname((String) param.get("msname"));
            meiShi.setMsaddress((String) param.get("msaddress"));
            meiShi.setMsnumber((String) param.get("msnumber"));
            meiShi.setContent((String) param.get("content"));
            /*时间*/
            meiShi.setDiscountstime(DateUtils.strToDate((String) param.get("discountstime")));
            meiShi.setDiscountetime(DateUtils.strToDate((String) param.get("discountetime")));
            meiShi.setInsertdate(new Date());

            /*查询是谁上传的*/
            Customer customer = (Customer) request.getSession().getAttribute("admin");
            if (customer != null) {
                meiShi.setSenduser(customer.getUsername());
            } else {
                meiShi.setSenduser("admin");
            }

            meiShi.setMspic("/images/meishi/" + fileName);
            if (meiShiSer.addMs(meiShi)) {
                resultMap.put("code", 200);
            }
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/initUpdate")
    public Map<String, Object> initUpdate(@RequestParam("id") String id, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            List<Dictionary> dictionarys = dicsSer.findList();
            List<Tag> tags = tagSer.findList();
            MeiShi meiShi = meiShiSer.findMsById(id);
            resultMap.put("meiShi", meiShi);
            resultMap.put("dictionarys", dictionarys);
            resultMap.put("tags", tags);
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String, Object> updateMeiShi(@RequestParam("updmspic") MultipartFile file, HttpServletRequest request, @RequestParam Map param) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            MeiShi meiShi = new MeiShi();
            if (file.isEmpty()) {
                /*数字字典字段*/
                meiShi.setRecommendcrowd((String) param.get("updrecommendcrowd"));
                meiShi.setIsdiscount((String) param.get("updisdiscount"));
                meiShi.setTypes((String) param.get("updtypes"));
                /*美食标签*/
                meiShi.setMstag((String) param.get("updmstag"));
                /*普通字段*/
                meiShi.setMsname((String) param.get("updmsname"));
                meiShi.setMsaddress((String) param.get("updmsaddress"));
                meiShi.setMsnumber((String) param.get("updmsnumber"));
                meiShi.setContent((String) param.get("updcontent"));
                meiShi.setId(Integer.parseInt((String) param.get("updId")));
                /*时间*/
                meiShi.setDiscountstime(DateUtils.strToDate((String) param.get("upddiscountstime")));
                meiShi.setDiscountetime(DateUtils.strToDate((String) param.get("upddiscountetime")));
                if (meiShiSer.updateMs(meiShi)) {
                    resultMap.put("code", 200);
                }
                return resultMap;
            } else {
                String fileName = file.getOriginalFilename();//得到文件名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));//得到后缀名
                System.err.println("suffixName:" + suffixName);
                String filepath = "E:/sharefood/src/main/resources/static/images/meishi/";//指定图片上传到哪个文件夹的路径
                fileName = UUID.randomUUID() + suffixName;//重新命名图片，变成随机的名字
                System.err.println("fileName:" + fileName);
                File dest = new File(filepath + fileName);//在上传的文件夹处创建文件
                try {
                    file.transferTo(dest);//把上传的图片写入磁盘中
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*数字字典字段*/
                meiShi.setRecommendcrowd((String) param.get("updrecommendcrowd"));
                meiShi.setIsdiscount((String) param.get("updisdiscount"));
                meiShi.setTypes((String) param.get("updtypes"));
                /*美食标签*/
                meiShi.setMstag((String) param.get("updmstag"));
                /*普通字段*/
                meiShi.setMsname((String) param.get("updmsname"));
                meiShi.setMsaddress((String) param.get("updmsaddress"));
                meiShi.setMsnumber((String) param.get("updmsnumber"));
                meiShi.setContent((String) param.get("updcontent"));
                meiShi.setId(Integer.parseInt((String) param.get("updId")));
                /*时间*/
                meiShi.setDiscountstime(DateUtils.strToDate((String) param.get("upddiscountstime")));
                meiShi.setDiscountetime(DateUtils.strToDate((String) param.get("upddiscountetime")));
                /*查询是谁上传的*/
                meiShi.setMspic("/images/meishi/" + fileName);
                if (meiShiSer.updateMs(meiShi)) {
                    resultMap.put("code", 200);
                }
            }
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String, Object> deleteMs(@RequestParam("id") String id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request) && meiShiSer.deleteMs(id)) {
            resultMap.put("success", "1");
        } else {
            resultMap.put("success", "0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/initCheck")
    public Map<String, Object> initCheckMs(@RequestParam("id") String id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            List<Dictionary> dictionarys = dicsSer.findList();
            MeiShi meiShi = meiShiSer.findMsById(id);
            resultMap.put("meiShi", meiShi);
            resultMap.put("dictionarys", dictionarys);
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/check")
    public Map<String, Object> check(CheckMsForm form, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            MeiShi meiShi = new MeiShi();
            meiShi.setIsfb(form.getCheckfb());
            if (form.getCheckId() != null) {
                meiShi.setId(Integer.parseInt(form.getCheckId()));
            }
            if (meiShiSer.updateMs(meiShi)) {
                resultMap.put("success", "1");
            } else {
                resultMap.put("success", "0");
            }
        }
        return resultMap;
    }
}
