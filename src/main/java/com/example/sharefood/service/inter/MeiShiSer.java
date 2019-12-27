package com.example.sharefood.service.inter;

import com.example.sharefood.domain.MeiShi;

import java.util.List;
import java.util.Map;

public interface MeiShiSer {
    int findCount(Map<String, Object> searchMap);

    List<MeiShi> listMeiShi(Map<String, Object> searchMap);

    boolean addMs(MeiShi meiShi);

    MeiShi findMsById(String id);

    boolean updateMs(MeiShi meiShi);

    boolean deleteMs(String id);
}
