package com.example.sharefood.mapping;

import com.example.sharefood.domain.MeiShi;

import java.util.List;
import java.util.Map;

public interface MeiShiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeiShi record);

    int insertSelective(MeiShi record);

    MeiShi selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeiShi record);

    int updateByPrimaryKey(MeiShi record);

    int findCount(Map<String, Object> searchMap);

    List<MeiShi> findList(Map<String, Object> searchMap);

    List<MeiShi> findCurrentMs();
}