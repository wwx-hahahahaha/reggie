package com.irving.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.irving.domain.DishFlavor;

import java.util.List;

public interface DishFlavorService extends IService<DishFlavor> {
    List<DishFlavor> getDishFlavor(Long id);
}
