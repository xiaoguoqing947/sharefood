package com.example.sharefood.controller.admin;

import com.example.sharefood.domain.Customer;
import com.example.sharefood.service.inter.CustomerSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/api/file")
public class FileCtrl {
    @Autowired
    private CustomerSer customerSer;

    @ResponseBody
    @PostMapping("/upload/updateHeadPic")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Customer customer=new Customer();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        System.err.println("传过来的值" + file);
        if (file.isEmpty()) {
            System.err.println("上传文件不可为空");
        }
        String fileName = file.getOriginalFilename();//得到文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//得到后缀名
        System.err.println("suffixName:" + suffixName);
        String filepath = "E:/sharefood/src/main/resources/static/images/users/";//指定图片上传到哪个文件夹的路径
        fileName = UUID.randomUUID() + suffixName;//重新命名图片，变成随机的名字
        System.err.println("fileName:" + fileName);
        File dest = new File(filepath + fileName);//在上传的文件夹处创建文件
        try {
            file.transferTo(dest);//把上传的图片写入磁盘中
        } catch (IOException e) {
            e.printStackTrace();
        }
        customer.setHeadpic("/images/users/"+fileName);
        Customer sessionCustomer=(Customer) request.getSession().getAttribute("admin");
        customer.setId(sessionCustomer.getId());
        if (customerSer.updateCustomerHeadPic(customer)) {
            resultMap.put("code", 200);
        }
        return resultMap;
    }
}
