package com.irving.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.commonly.R;
import com.irving.domain.Category;
import com.irving.domain.Dish;
import com.irving.domain.DishFlavor;
import com.irving.dto.DishDto;
import com.irving.mapper.DishMapper;
import com.irving.service.CategoryService;
import com.irving.service.DishFlavorService;
import com.irving.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    private final CategoryService categoryService;
    private final DishFlavorService dishFlavorService;
    @Override
    public DishDto getDishDto(Long ids) {
        LambdaQueryWrapper<Dish> lq=new LambdaQueryWrapper<>();
        lq.eq(Dish::getId,ids);
        Dish one = this.getOne(lq);
        LambdaQueryWrapper<Category> lm=new LambdaQueryWrapper<>();
        lm.eq(Category::getId,one.getCategoryId());
        Category category = categoryService.getOne(lm);
        if (one != null) {
            Long id = one.getId();
            LambdaQueryWrapper<DishFlavor> lq1=new LambdaQueryWrapper<>();
//            lq1.eq(DishFlavor::getDishId,id);
            List<DishFlavor> list=dishFlavorService.getDishFlavor(id);
            DishDto dto=new DishDto();
            BeanUtils.copyProperties(one,dto);
//            dto.setCategoryName(category.getName());
            dto.setFlavors(list);
            return dto;
        }
        return null;
    }

    @Transactional
    @Override
    public boolean updates(DishDto dto) {
        boolean b=false;
        Dish dish=new Dish();
        BeanUtils.copyProperties(dto,dish);
        boolean b1 = this.updateById(dish);
        if (b1){
            b=b1;
        }
//        1397849739297861633
//        1397849739276890000
        List<DishFlavor> flavors = dto.getFlavors();
        List<DishFlavor> list = flavors.stream().map(a -> {
            a.setDishId(dish.getId());
            a.setId(IdWorker.getId());
            return a;
        }).collect(Collectors.toList());

        LambdaQueryWrapper<DishFlavor>lm=new LambdaQueryWrapper<>();
        lm.eq(DishFlavor::getDishId,dto.getId());
        dishFlavorService.remove(lm);

        boolean b2 = dishFlavorService.saveBatch(list);
        if (b2){
            b=b2;
        }
        return b;
    }
}
