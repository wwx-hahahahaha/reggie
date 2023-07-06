package com.irving.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.domain.DishFlavor;
import com.irving.mapper.DishFlavorMapper;
import com.irving.mapper.DishMapper;
import com.irving.service.DishFlavorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
    @Resource
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public List<DishFlavor> getDishFlavor(Long id) {
        LambdaQueryWrapper<DishFlavor> lq=new LambdaQueryWrapper<>();
        lq.eq(DishFlavor::getDishId, id);
        List<DishFlavor> list = dishFlavorMapper.selectList(lq);
        return list;
    }
}
