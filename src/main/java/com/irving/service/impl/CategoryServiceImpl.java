package com.irving.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.domain.Category;
import com.irving.mapper.CategoryMapper;
import com.irving.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    private final CategoryMapper categoryMapper;
    @Override
    public List<Category> getType(Integer type) {
        LambdaQueryWrapper<Category> lq=new LambdaQueryWrapper<>();
        lq.eq(Category::getType,type);
        List<Category> categories = categoryMapper.selectList(lq);
        return categories;
    }
}
