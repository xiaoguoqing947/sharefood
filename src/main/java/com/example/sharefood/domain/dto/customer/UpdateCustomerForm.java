package com.example.sharefood.domain.dto.customer;

import lombok.Data;

@Data
public class UpdateCustomerForm {
    private String name;
    private String email;
    private String sex;
    private String age;
    private String phone;
    private String idcard;
    private String address;
}
