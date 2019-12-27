package com.example.sharefood.service;

import com.example.sharefood.domain.MeiShi;
import com.example.sharefood.mapping.MeiShiMapper;
import com.example.sharefood.service.inter.MeiShiSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MeiShiSerImpl implements MeiShiSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeiShiSerImpl.class);
    @Resource
    private MeiShiMapper meiShiMapper;

    @Override
    public int findCount(Map<String, Object> searchMap) {
        return meiShiMapper.findCount(searchMap);
    }

    @Override
    public List<MeiShi> listMeiShi(Map<String, Object> searchMap) {
        return meiShiMapper.findList(searchMap);
    }

    @Override
    public boolean addMs(MeiShi meiShi) {
        int num = 0;
        try {
            num = meiShiMapper.insertSelective(meiShi);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }
}
