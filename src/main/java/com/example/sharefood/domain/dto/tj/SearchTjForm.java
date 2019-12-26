package com.example.sharefood.domain.dto.tj;

import lombok.Data;

@Data
public class SearchTjForm {
    private String picName;
    private String pageSize;
    private String currentPage;
    private int indexCount;
}
