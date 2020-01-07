package com.example.sharefood.service.inter;

import com.example.sharefood.domain.Tag;

import java.util.List;

public interface TagSer {
    List<Tag> findList();

    String findTagById(int parseInt);

    boolean addTag(String tagName);

    boolean deleteTag(String id);
}
