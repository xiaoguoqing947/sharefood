package com.example.sharefood.controller.admin;

import com.example.sharefood.domain.Customer;
import com.example.sharefood.domain.dto.customer.UpdateCustomerForm;
import com.example.sharefood.service.inter.CustomerSer;
import com.example.sharefood.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/customer")
public class CustomerCtrl {
    @Autowired
    private CustomerSer customerSer;

    @ResponseBody
    @PostMapping("/detail")
    public Map<String, Object> initCustomer(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            resultMap.put("success",1);
            Customer customer = (Customer) request.getSession().getAttribute("admin");
            Customer customerInfo = customerSer.findCustomerByUName(customer.getUsername());
            resultMap.put("customerInfo",customerInfo);
        }else{
            resultMap.put("success",0);
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String, Object> updateCustomer(UpdateCustomerForm form, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request) && customerSer.updateCustomer(form, request)) {
            resultMap.put("status", "success");
        } else {
            resultMap.put("status", "fail");
        }
        return resultMap;
    }
}
