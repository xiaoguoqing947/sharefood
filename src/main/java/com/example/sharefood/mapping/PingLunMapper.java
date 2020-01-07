package com.example.sharefood.mapping;

import com.example.sharefood.domain.PingLun;

import java.util.List;

public interface PingLunMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PingLun record);

    int insertSelective(PingLun record);

    PingLun selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PingLun record);

    int updateByPrimaryKey(PingLun record);

    List<PingLun> findList(int parseInt);
}