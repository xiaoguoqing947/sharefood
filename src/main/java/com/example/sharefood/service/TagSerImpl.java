package com.example.sharefood.service;

import com.example.sharefood.domain.Tag;
import com.example.sharefood.mapping.TagMapper;
import com.example.sharefood.service.inter.TagSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagSerImpl implements TagSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeiShiSerImpl.class);
    @Resource
    private TagMapper tagMapper;

    @Override
    public List<Tag> findList() {
        return tagMapper.findList();
    }
}
