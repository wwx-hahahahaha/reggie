package com.irving.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.irving.domain.Dish;
import com.irving.dto.DishDto;
import com.irving.mapper.DishMapper;

public interface DishService extends IService<Dish> {
    DishDto getDishDto(Long ids);

    boolean updates(DishDto dto);
}
