package com.example.sharefood.domain.dto.ms;

import lombok.Data;

@Data
public class SearchMsForm {
    private String foodName;
    private String pageSize;
    private String currentPage;
    private int indexCount;
}
