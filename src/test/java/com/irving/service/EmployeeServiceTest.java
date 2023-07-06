package com.irving.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.irving.commonly.R;
import com.irving.domain.Category;
import com.irving.domain.Dish;
import com.irving.domain.DishFlavor;
import com.irving.domain.Employee;
import com.irving.dto.DishDto;
import com.irving.mapper.EmployeeMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class EmployeeServiceTest {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private DishService dishService;

    @Resource
    private  CategoryService categoryService;
    @Resource
    private  DishFlavorService dishFlavorService;

    @Resource
    private EmployeeMapper mapper;
    @Test
    void demo(){
        Long ids=1397849739276890114l;
//        1397849739276890114
//        1397849739276890114
        LambdaQueryWrapper<Dish> lq=new LambdaQueryWrapper<>();
        lq.eq(Dish::getId,ids);
        Dish one = dishService.getOne(lq);
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
            dto.setCategoryName(category.getName());
            dto.setFlavors(list);
//            System.out.println(dto);
//            log.info();
            System.out.println("哈哈哈哈哈"+JSON.toJSONString(R.success(list)));
        }
    }

    @Test
    void Demo2(){
        Long ids=1397849739276890114l;
        System.out.println(dishService.getDishDto(ids));
    }
}