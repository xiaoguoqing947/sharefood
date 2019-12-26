package com.example.sharefood.mapping;

import com.example.sharefood.domain.Tujian;

import java.util.List;
import java.util.Map;

public interface TujianMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tujian record);

    int insertSelective(Tujian record);

    Tujian selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tujian record);

    int updateByPrimaryKey(Tujian record);

    int findCount(Map<String, Object> searchMap);

    List<Tujian> findList(Map<String, Object> searchMap);
}