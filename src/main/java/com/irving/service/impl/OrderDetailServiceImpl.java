package com.irving.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.domain.OrderDetail;
import com.irving.mapper.OrderDetailMapper;
import com.irving.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
