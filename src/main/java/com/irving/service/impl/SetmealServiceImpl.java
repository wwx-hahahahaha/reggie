package com.irving.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.domain.Category;
import com.irving.domain.Setmeal;
import com.irving.domain.SetmealDish;
import com.irving.dto.SetmealDto;
import com.irving.mapper.SetmealMapper;
import com.irving.service.CategoryService;
import com.irving.service.SetmealDishService;
import com.irving.service.SetmealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService{

    private final SetmealDishService setmealDishService;
    private final CategoryService categoryService;
    @Override
    public Page<SetmealDto> getPage(Integer page, Integer pageSize, String name) {
        LambdaQueryWrapper<Setmeal> lm=new LambdaQueryWrapper<>();
        lm.like(name!=null,Setmeal::getName,name);
        Page<Setmeal> page1 = this.page(new Page<>(page, pageSize), lm);
        Page<SetmealDto> dtoPage=new Page<>();
        BeanUtils.copyProperties(page1,dtoPage,"records");
        List<SetmealDto> dtos = page1.getRecords().stream().map(item -> {
            Category one = categoryService.getOne(new LambdaQueryWrapper<Category>().eq(Category::getId, item.getCategoryId()));
            SetmealDto dto=new SetmealDto();
            BeanUtils.copyProperties(item, dto);
            dto.setCategoryName(one.getName());
            return dto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(dtos);
        return dtoPage;
    }

    @Transactional
    @Override
    public boolean mySave(SetmealDto dto) {
        boolean b=true;
        boolean save = this.save(dto);
        if (!save){
            b=false;
        }

        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        List<SetmealDish> list = setmealDishes.stream().map(item -> {
            item.setSetmealId(dto.getId());
            return item;
        }).collect(Collectors.toList());

        boolean saveBatch = setmealDishService.saveBatch(list);
        if (!saveBatch){
            b=false;
        }
        return b;
    }

    @Override
    public SetmealDto get(long id) {
        LambdaQueryWrapper<Setmeal> lm=new LambdaQueryWrapper<>();
        lm.eq(Setmeal::getId,id);
        Setmeal one = this.getOne(lm);
        SetmealDto dto=new SetmealDto();
        BeanUtils.copyProperties(one,dto);

        LambdaQueryWrapper<SetmealDish> lq=new LambdaQueryWrapper<>();
        lq.eq(SetmealDish::getSetmealId,one.getId());
        List<SetmealDish> setmealDishes = setmealDishService.getBaseMapper().selectList(lq);
        dto.setSetmealDishes(setmealDishes);

//        LambdaQueryWrapper<Category> lc=new LambdaQueryWrapper<>();
//        lc.eq(Category::getId,one.getCategoryId());
        Category category = categoryService.getById(one.getCategoryId());
        dto.setCategoryName(category.getName());
        return dto;
    }

    @Override
    public boolean MyUpdate(SetmealDto dto) {
        boolean b=true;
        boolean save = this.updateById(dto);
        if (!save){
            b=false;
        }

        LambdaQueryWrapper<SetmealDish> lm=new LambdaQueryWrapper<>();
        lm.eq(SetmealDish::getSetmealId,dto.getId());
        setmealDishService.remove(lm);
        List<SetmealDish> dishes = dto.getSetmealDishes().stream().map(item -> {
            item.setSetmealId(dto.getId());
            item.setId(IdWorker.getId());
            return item;
        }).collect(Collectors.toList());
        boolean saveBatch = setmealDishService.saveBatch(dishes);
        if (!saveBatch) b=false;
        return b;
    }
}
