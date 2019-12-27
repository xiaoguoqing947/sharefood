package com.example.sharefood.controller.admin;

import com.example.sharefood.service.inter.TagSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/tag")
public class TagCtrl {
    @Autowired
    private TagSer tagSer;
}
