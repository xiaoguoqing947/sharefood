package com.example.sharefood.controller;

import com.example.sharefood.domain.Customer;
import com.example.sharefood.domain.dto.customer.CustomerRegistForm;
import com.example.sharefood.mapping.CustomerMapper;
import com.example.sharefood.service.inter.AdminSer;
import com.example.sharefood.service.inter.CustomerSer;
import com.example.sharefood.util.Token;
import com.example.sharefood.util.ValidateMethod;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
            rep.getSession().setAttribute("tip", "admin_login_success");
            resultMap.put("status", "success");
            String token = Token.getTokenString(rep.getSession());
            resultMap.put("token", token);
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
            customerSer.addSession(rep, customer);
            String token = Token.getTokenString(rep.getSession());
            resultMap.put("token", token);
        }
        return resultMap;
    }

    /*普通用户登录*/
    @GetMapping("/loginout.action")
    public String loginout(HttpServletRequest rep, Model model) {
        customerSer.destroySession(rep);
        model.addAttribute("statu", "unlogin");
        return "index";
    }

    /*普通用户注册*/
    @ResponseBody
    @PostMapping("/register.action")
    public Map<String, Object> register(@RequestBody CustomerRegistForm form) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);
        customer.setInsertdate(new Date());
        if (customerSer.addCustomer(customer)) {
            resultMap.put("status", "success");
        } else {
            resultMap.put("status", "fail");
        }
        return resultMap;
    }

    /*用户名的验证*/
    @ResponseBody
    @PostMapping("/validateName.action")
    public boolean validateName(@RequestParam String uname) {
        boolean flag = customerSer.validateName(uname);
        return flag;
    }

    /*由此跳转到用户个人中心界面*/
    @GetMapping("/api/admin")
    public String customerAdmin(Model model, HttpServletRequest request) {
        Customer customer = (Customer) request.getSession().getAttribute("admin");
        Customer customerInfo = customerSer.findCustomerByUName(customer.getUsername());
        model.addAttribute("user", customerInfo);
        return "monitor/admin";
    }

    /*由此跳转到管理员个人中心界面*/
    @GetMapping("/api/sysadmin")
    public String sysAdmin(Model model, HttpServletRequest request) {
        return "monitor/sysadmin";
    }

    /*系统管理员界面加载所有用户信息*/
    @ResponseBody
    @PostMapping("/api/sysadmin/customerInfo")
    public Map<String, Object> customerInfo(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> searchMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            List<Customer> customers = customerSer.findCustomerList(searchMap);
            resultMap.put("customers", customers);
            resultMap.put("status", "success");
        }else{
            resultMap.put("status", "fail");
        }
        return resultMap;
    }
}
