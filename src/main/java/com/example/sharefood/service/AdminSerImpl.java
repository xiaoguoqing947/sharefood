package com.example.sharefood.service;

import com.example.sharefood.mapping.AdminMapper;
import com.example.sharefood.service.inter.AdminSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
@Service
public class AdminSerImpl implements AdminSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminSerImpl.class);
    @Resource
    private AdminMapper adminMapper;

    @Override
    public boolean login(String username, String password) {
        int num = 0;
        try {
            num = adminMapper.adminByLogin(username, password);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }
}
