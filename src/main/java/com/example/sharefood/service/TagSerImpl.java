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

    @Override
    public String findTagById(int parseInt) {
        return tagMapper.findTagNameById(parseInt);
    }

    @Override
    public boolean addTag(String tagName) {
        int num = 0;
        try {
            Tag tag=new Tag();
            tag.setTag(tagName);
            num = tagMapper.insertSelective(tag);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean deleteTag(String id) {
        int num = 0;
        try {
            num = tagMapper.deleteByPrimaryKey(Integer.parseInt(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }
}
