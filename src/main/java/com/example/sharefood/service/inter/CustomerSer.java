package com.example.sharefood.service.inter;

import com.example.sharefood.domain.Customer;
import com.example.sharefood.domain.dto.customer.UpdateCustomerForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CustomerSer {
    public Customer login(String username, String password);

    void addSession(HttpServletRequest request, Customer customer);//登录成功后添加session

    void destroySession(HttpServletRequest request);//退出登录或者超时之后销毁session

    boolean addCustomer(Customer customer);

    boolean validateName(String uname);

    Customer findCustomerByUName(String username);

    boolean updateCustomer(UpdateCustomerForm form, HttpServletRequest request);

    boolean updateCustomerHeadPic(Customer customer);

    List<Customer> findCustomerList(Map<String, Object> searchMap);

    boolean deleteCustomer(String id);
}
