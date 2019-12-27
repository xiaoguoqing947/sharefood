package com.example.sharefood.mapping;

import com.example.sharefood.domain.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

    String findDicsNameByValueAndType(@Param("code")String typeName,@Param("value")int keyValue);

    List<Dictionary> findList();
}