package com.example.sharefood.service.inter;

import com.example.sharefood.domain.PingLun;

import java.util.List;

public interface PingLunSer {
    List<PingLun> findList(String id);

    boolean addPingLun(PingLun pingLun);
}
