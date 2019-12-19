package com.example.sharefood.controller;

import com.example.sharefood.domain.Customer;
import com.example.sharefood.mapping.CustomerMapper;
import com.example.sharefood.service.inter.AdminSer;
import com.example.sharefood.service.inter.CustomerSer;
import com.example.sharefood.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginCtrl {
    private AdminSer adminSer;
    private CustomerSer customerSer;

    @Autowired
    public LoginCtrl(AdminSer adminSer, CustomerSer customerSer) {
        this.adminSer = adminSer;
        this.customerSer = customerSer;
    }

    /*管理员登录*/
    @ResponseBody
    @PostMapping("/loginAdmin.action")
    public Map<String, Object> doAdminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest rep) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean result = adminSer.login(username, password);
        if (result) {
            rep.getSession().setAttribute("tip","admin_login_success");
            resultMap.put("status", "success");
        }
        return resultMap;
    }

    /*普通用户登录*/
    @ResponseBody
    @PostMapping("/login.action")
    public Map<String, Object> doLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest rep) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Customer customer = customerSer.login(username, password);
        if (customer != null) {
            resultMap.put("status", "success");
            customerSer.addSession(rep,customer);
            String token = Token.getTokenString(rep.getSession());
            resultMap.put("token", token);
        }
        return resultMap;
    }

    /*普通用户登录*/
    @GetMapping("/loginout.action")
    public String loginout(HttpServletRequest rep, Model model) {
        customerSer.destroySession(rep);
        model.addAttribute("statu","unlogin");
        return "index";
    }
}
