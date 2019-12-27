package com.example.sharefood.service;

import com.example.sharefood.domain.Tujian;
import com.example.sharefood.mapping.TujianMapper;
import com.example.sharefood.service.inter.TujianSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TujianSerImpl implements TujianSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TujianSerImpl.class);
    @Resource
    private TujianMapper tujianMapper;

    @Override
    public int findCount(Map<String, Object> searchMap) {
        return tujianMapper.findCount(searchMap);
    }

    @Override
    public List<Tujian> listTujian(Map<String, Object> searchMap) {
        return tujianMapper.findList(searchMap);
    }

    @Override
    public Tujian findTj(String id) {
        return tujianMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

    @Override
    public boolean updateTj(Tujian tujian) {
        int num = 0;
        try {
            num = tujianMapper.updateByPrimaryKeySelective(tujian);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public List<Tujian> findList(Map<String, Object> searchMap) {
        return tujianMapper.findList(searchMap);
    }
}
