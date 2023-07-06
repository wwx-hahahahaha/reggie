package com.irving.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.domain.ShoppingCart;
import com.irving.mapper.ShoppingCartMapper;
import com.irving.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
