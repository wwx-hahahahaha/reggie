package com.irving.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.irving.commonly.R;
import com.irving.domain.Orders;

public interface OrderService extends IService<Orders> {
    R<?> submit(Orders orders);
}
