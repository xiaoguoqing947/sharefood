package com.example.sharefood.service.inter;

import com.example.sharefood.domain.Tujian;

import java.util.List;
import java.util.Map;

public interface TujianSer {
    int findCount(Map<String, Object> searchMap);

    List<Tujian> listTujian(Map<String, Object> searchMap);

    Tujian findTj(String id);

    boolean updateTj(Tujian tujian);
}
