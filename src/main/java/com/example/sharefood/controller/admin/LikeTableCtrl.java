package com.example.sharefood.controller.admin;

import com.example.sharefood.domain.LikeTable;
import com.example.sharefood.service.inter.LikeTableSer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/liketable")
public class LikeTableCtrl {

    @Autowired
    private LikeTableSer likeTableSer;

    @ResponseBody
    @PostMapping("/addView")
    public Map<String, Object> add(@RequestParam("msId") String msId,@RequestParam("view") String view,HttpServletRequest request) {
        System.out.println(msId+"-"+view);
        Map<String,Object> resultMap=new HashMap<String, Object>();
        LikeTable likeTable=likeTableSer.findLikeTableByMsId(msId);
        if(likeTable == null){
            LikeTable lTable=new LikeTable();
            lTable.setMsid(Integer.parseInt(msId));
            lTable.setIsview(Integer.parseInt(view));
            if(likeTableSer.addLikeTable(lTable)){
                resultMap.put("status","addSuccess");
            }else{
                resultMap.put("status","addFail");
            }
        }else{
            likeTable.setMsid(Integer.parseInt(msId));
            likeTable.setIsview(likeTable.getIsview()+1);
            if(likeTableSer.updateLikeTable(likeTable)){
                resultMap.put("status","updateSuccess");
            }else{
                resultMap.put("status","updateFail");
            }
        }
        return resultMap;
    }
}
