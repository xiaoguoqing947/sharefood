package com.example.sharefood.service;

import com.example.sharefood.domain.LikeTable;
import com.example.sharefood.mapping.LikeTableMapper;
import com.example.sharefood.service.inter.LikeTableSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class LikeTableSerImpl implements LikeTableSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LikeTableSerImpl.class);

    @Resource
    private LikeTableMapper likeTableMapper;

    @Override
    public LikeTable findLikeTableByMsId(String msId) {
        return likeTableMapper.selectByMsId(msId);
    }

    @Override
    public boolean addLikeTable(LikeTable lTable) {
        int num = 0;
        try {
            num = likeTableMapper.insertSelective(lTable);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean updateLikeTable(LikeTable likeTable) {
        int num = 0;
        try {
            num = likeTableMapper.updateByPrimaryKeySelective(likeTable);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public List<LikeTable> findList() {
        return likeTableMapper.findList();
    }
}
