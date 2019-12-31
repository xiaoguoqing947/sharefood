package com.example.sharefood.mapping;

import com.example.sharefood.domain.LikeTable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeTableMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LikeTable record);

    int insertSelective(LikeTable record);

    LikeTable selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LikeTable record);

    int updateByPrimaryKey(LikeTable record);

    LikeTable selectByMsId(@Param("msId") String msId);

    List<LikeTable> findList();
}