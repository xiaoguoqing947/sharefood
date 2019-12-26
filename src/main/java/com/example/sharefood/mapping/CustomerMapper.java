package com.example.sharefood.mapping;

import com.example.sharefood.domain.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    Customer userByLogin(@Param("un") String username, @Param("pwd") String password);

    Customer queryCustomerByUsername(@Param("un")String uname);

    List<Customer> findList(Map<String, Object> searchMap);
}