package com.irving.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.irving.domain.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
