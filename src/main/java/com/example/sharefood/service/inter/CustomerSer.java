package com.example.sharefood.service.inter;

import com.example.sharefood.domain.Customer;

import javax.servlet.http.HttpServletRequest;

public interface CustomerSer {
    public Customer login(String username, String password);

    void addSession(HttpServletRequest request, Customer customer);//登录成功后添加session

    void destroySession(HttpServletRequest request);//退出登录或者超时之后销毁session
}
