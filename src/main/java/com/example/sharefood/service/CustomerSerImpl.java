package com.example.sharefood.service;

import com.example.sharefood.domain.Customer;
import com.example.sharefood.mapping.CustomerMapper;
import com.example.sharefood.service.inter.CustomerSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class CustomerSerImpl implements CustomerSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSerImpl.class);

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public Customer login(String username, String password) {
        return customerMapper.userByLogin(username, password);
    }

    @Override
    public void addSession(HttpServletRequest request, Customer customer) {
        HttpSession session = request.getSession(true);
        session.setAttribute("admin", customer);
        session.setMaxInactiveInterval(1800);
    }

    @Override
    public void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("admin");
        session.removeAttribute("tokenList");
        session.removeAttribute("tip");
    }

    @Override
    public boolean addCustomer(Customer customer) {
        int num = 0;
        try {
            num = customerMapper.insertSelective(customer);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean validateName(String uname) {
        Customer customer = null;
        try {
            customer = customerMapper.queryCustomerByUsername(uname);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return customer == null;
    }
}
