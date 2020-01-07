package com.example.sharefood.controller.admin;

import com.example.sharefood.domain.Customer;
import com.example.sharefood.domain.PingLun;
import com.example.sharefood.service.inter.MeiShiSer;
import com.example.sharefood.service.inter.PingLunSer;
import com.example.sharefood.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/pinglun")
public class PingLunCtrl {
    @Autowired
    private PingLunSer pingLunSer;
    @Autowired
    private MeiShiSer meiShiSer;

    @ResponseBody
    @PostMapping("/queryList")
    public Map<String, Object> initDetailPage(HttpServletRequest request, @RequestParam("msId") String id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<PingLun> pingLunList = pingLunSer.findList(id);
        resultMap.put("success", 1);
        resultMap.put("pingLunList", pingLunList);
        return resultMap;
    }

    @PostMapping("/add")
    public String addPinglun(@RequestParam("msId") String msId, @RequestParam("context") String context, HttpServletRequest req) {
        PingLun pingLun = new PingLun();
        pingLun.setMsid(Integer.parseInt(msId));
        pingLun.setContent(context);
        Customer customer = (Customer) req.getSession().getAttribute("admin");
        pingLun.setInsertdate(new Date());
        pingLun.setFromid(String.valueOf(customer.getId()));
        pingLun.setFromname(customer.getUsername());
        pingLun.setFromavatar(customer.getHeadpic());
        if (meiShiSer.findIdByMsId(msId) == null) {
            pingLun.setOwnerid("0");//0代表管理员   根据美食id查询值为空的话说明改图的owner是管理员
        } else {
            pingLun.setOwnerid(meiShiSer.findIdByMsId(msId));
        }
        boolean flag = pingLunSer.addPingLun(pingLun);
        if(flag){
            return "redirect:/detailfood?isAdmin=user&detail="+msId;
        }
        return "login";//添加失败跳转到登录界面
    }
}
