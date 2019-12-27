package com.example.sharefood.service;

import com.example.sharefood.domain.Dictionary;
import com.example.sharefood.mapping.DictionaryMapper;
import com.example.sharefood.service.inter.DicsSer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DicsSerImpl implements DicsSer {

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Override
    public String findDicsNameByValueAndType(String code, String value) {
        return dictionaryMapper.findDicsNameByValueAndType(code,Integer.parseInt(value));
    }

    @Override
    public List<Dictionary> findList() {
        return dictionaryMapper.findList();
    }
}
