package com.irving.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.irving.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
