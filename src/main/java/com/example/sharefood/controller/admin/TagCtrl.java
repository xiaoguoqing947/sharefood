package com.example.sharefood.controller.admin;

import com.example.sharefood.domain.Tag;
import com.example.sharefood.service.inter.TagSer;
import com.example.sharefood.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/tag")
public class TagCtrl {
    @Autowired
    private TagSer tagSer;
    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String,Object> queryListUrl(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            List<Tag> tagList = tagSer.findList();
            resultMap.put("data", tagList);
            resultMap.put("status", "success");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/add")
    public boolean addTag(@RequestParam("tagName") String tagName, HttpServletRequest req){
        boolean flag=tagSer.addTag(tagName);
        return flag;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String,Object> deleteTag(@RequestParam("id") String id,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && tagSer.deleteTag(id)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }
}
