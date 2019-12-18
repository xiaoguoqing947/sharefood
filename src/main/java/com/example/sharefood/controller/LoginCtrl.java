package com.example.sharefood.controller;

import com.example.sharefood.service.inter.AdminSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginCtrl {
    @Autowired
    private AdminSer adminSer;

    /*管理员登录*/
    @ResponseBody
    @PostMapping("/loginAdmin.action")
    public Map<String, Object> doAdminLogin(@RequestParam("username") String username, @RequestParam("password") String password,HttpServletRequest rep) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean result = adminSer.login(username,password);
        if (result) {
            resultMap.put("status", "success");
        }
        return resultMap;
    }
}
