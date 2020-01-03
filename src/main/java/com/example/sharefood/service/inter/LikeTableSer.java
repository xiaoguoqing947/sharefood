package com.example.sharefood.service.inter;

import com.example.sharefood.domain.LikeTable;

import java.util.List;

public interface LikeTableSer {
    LikeTable findLikeTableByMsId(String msId);

    boolean addLikeTable(LikeTable lTable);

    boolean updateLikeTable(LikeTable likeTable);

    List<LikeTable> findList();

    List<LikeTable> findCustomerMsList(String username);
}
