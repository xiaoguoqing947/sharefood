package com.example.sharefood.mapping;

import com.example.sharefood.domain.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tag record);

    int insertSelective(Tag record);

    Tag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tag record);

    int updateByPrimaryKey(Tag record);

    List<Tag> findList();

    String findTagNameById(@Param("id") int parseInt);
}