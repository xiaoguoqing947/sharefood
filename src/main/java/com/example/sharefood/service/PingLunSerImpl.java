package com.example.sharefood.service;

import com.example.sharefood.domain.PingLun;
import com.example.sharefood.mapping.PingLunMapper;
import com.example.sharefood.service.inter.PingLunSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PingLunSerImpl implements PingLunSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PingLunSerImpl.class);
    @Resource
    private PingLunMapper pingLunMapper;

    @Override
    public List<PingLun> findList(String id) {
        return pingLunMapper.findList(Integer.parseInt(id));
    }

    @Override
    public boolean addPingLun(PingLun pingLun) {
        int num = 0;
        try {
            num = pingLunMapper.insertSelective(pingLun);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }
}
