package com.example.sharefood.controller.admin;

import com.example.sharefood.domain.Customer;
import com.example.sharefood.domain.LikeTable;
import com.example.sharefood.domain.dto.customer.UpdateCustomerForm;
import com.example.sharefood.service.inter.CustomerSer;
import com.example.sharefood.service.inter.LikeTableSer;
import com.example.sharefood.util.PersonUtils;
import com.example.sharefood.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/customer")
public class CustomerCtrl {
    @Autowired
    private CustomerSer customerSer;
    @Autowired
    private LikeTableSer likeTableSer;

    @ResponseBody
    @PostMapping("/detail")
    public Map<String, Object> initCustomer(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            resultMap.put("success", 1);
            Customer customer = (Customer) request.getSession().getAttribute("admin");
            Customer customerInfo = customerSer.findCustomerByUName(customer.getUsername());
            /*获取指定用户发布的文章被点赞和收藏的数量*/
            List<LikeTable> tableList = likeTableSer.findCustomerMsList(customer.getUsername());
            int zanCount=0;
            int collectCount=0;
            for (int i = 0; i < tableList.size(); i++) {
                String isLikeUsers = tableList.get(i).getIslike();
                String isStartUsers = tableList.get(i).getIsstart();
                if(isLikeUsers != null){
                    LinkedList<Integer> likeList = PersonUtils.getNumberInStr(isLikeUsers);
                    zanCount+=likeList.size();
                }
                if(isStartUsers != null){
                    LinkedList<Integer> startList = PersonUtils.getNumberInStr(isStartUsers);
                    collectCount+=startList.size();
                }
            }
            resultMap.put("zan", zanCount);
            resultMap.put("collect", collectCount);
            resultMap.put("customerInfo", customerInfo);
        } else {
            resultMap.put("success", 0);
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

    @ResponseBody
    @PostMapping("/delete")
    public Map<String, Object> deleteMenu(@RequestParam("id") String id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request) && customerSer.deleteCustomer(id)) {
            resultMap.put("success", "1");
        } else {
            resultMap.put("success", "0");
        }
        return resultMap;
    }
}
