package com.irving.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.domain.SetmealDish;
import com.irving.mapper.SetmealDishMapper;
import com.irving.service.SetmealDishService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
