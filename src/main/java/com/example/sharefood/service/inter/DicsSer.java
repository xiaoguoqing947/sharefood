package com.example.sharefood.service.inter;

import com.example.sharefood.domain.Dictionary;

import java.util.List;

public interface DicsSer {
    String findDicsNameByValueAndType(String code,String value);

    List<Dictionary> findList();
}
