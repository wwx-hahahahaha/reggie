package com.irving.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.irving.domain.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> getType(Integer type);
}
