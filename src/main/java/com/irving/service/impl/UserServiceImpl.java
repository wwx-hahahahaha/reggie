package com.irving.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.domain.User;
import com.irving.mapper.UserMapper;
import com.irving.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
