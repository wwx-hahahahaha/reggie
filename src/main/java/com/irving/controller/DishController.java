package com.irving.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.irving.commonly.R;
import com.irving.domain.Category;
import com.irving.domain.Dish;
import com.irving.domain.DishFlavor;
import com.irving.dto.DishDto;
import com.irving.service.CategoryService;
import com.irving.service.DishFlavorService;
import com.irving.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;
    private final CategoryService categoryService;
    private final DishFlavorService dishFlavorService;

    @GetMapping("/page")
    @ResponseBody
    public R<?> page(@RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,@RequestParam(value = "name",required = false)String name){
        LambdaQueryWrapper<Dish> lq=new LambdaQueryWrapper<>();
        lq.like(name!=null,Dish::getName,name);
        Page<Dish> page1 = dishService.page(new Page<>(page,pageSize),lq);
        Page<DishDto> newPage=new Page<>();
        if (page1 != null) {
            BeanUtils.copyProperties(page1,newPage,"records");
            List<DishDto> list = page1.getRecords().stream().map(a -> {
                DishDto dto=new DishDto();
                BeanUtils.copyProperties(a,dto);
                Long id = dto.getCategoryId();
                LambdaQueryWrapper<Category> lq1=new LambdaQueryWrapper<>();
                lq1.eq(Category::getId,id);
                Category one = categoryService.getOne(lq1);
                dto.setCategoryName(one.getName());
                return dto;
            }).collect(Collectors.toList());
            newPage.setRecords(list);
            return R.success(newPage);
        }
        return R.success("出现异常");
    }

    @ResponseBody
    @PostMapping()
    public R<?> SaveDish(@RequestBody Dish dish){
        boolean save = dishService.save(dish);
        if (save){
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }

    @DeleteMapping()
    @ResponseBody
    public R<?> Delete(@RequestParam("ids") Long[] ids){
        boolean b=false;
        for (Long id : ids) {
             b = dishService.removeById(id);
        }
        if (b){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @ResponseBody
    @PostMapping("/status/{num}")
    public R<?> status(@RequestParam("ids")Long[] ids,@PathVariable("num")Integer num){
        if (num==0){
            List<Dish> list=new ArrayList<>();
            List<Dish> dishes = Arrays.stream(ids).map(id -> {
                Dish dish = Dish.builder().status(0).id(id).build();
                return dish;
            }).collect(Collectors.toList());
            boolean update = dishService.updateBatchById(dishes);
            if (update) {
                return R.success("停售成功");
            }
            return R.error("停售失败");
        }
        List<Dish> dishes = Arrays.stream(ids).map(id -> {
            Dish dish = Dish.builder().status(1).id(id).build();
            return dish;
        }).collect(Collectors.toList());
        boolean update = dishService.updateBatchById(dishes);
        if (update) {
            return R.success("启售成功");
        }
        return R.error("启售失败");
    }

    @ResponseBody
    @GetMapping("/{update}")
    public R<?> update(@PathVariable("update")Long ids, HttpServletResponse response){
        DishDto dto=dishService.getDishDto(ids);
        response.setContentType("application/json;charset=utf-8");
        return R.success(dto);
//        try {
//            PrintWriter writer = response.getWriter();
//            if (dto!=null){
//                String jsonString = JSON.toJSONString(R.success(dto));
//                writer.write(jsonString);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @PutMapping()
    @ResponseBody
    public R<?> put(@RequestBody DishDto dto){
        boolean b=dishService.updates(dto);
        if (b){
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    @GetMapping("/list")
    @ResponseBody
    public R<?> list(@RequestParam("categoryId")Long id ){
        BaseMapper<Dish> mapper = dishService.getBaseMapper();
        LambdaQueryWrapper<Dish> lm=new LambdaQueryWrapper<>();
        lm.eq(Dish::getCategoryId,id);
        List<Dish> list = mapper.selectList(lm);
        List<DishDto> dtoList = list.stream().map(item -> {
            DishDto dto = new DishDto();
            BeanUtils.copyProperties(item, dto);
            LambdaQueryWrapper<DishFlavor> lq = new LambdaQueryWrapper<>();
            lq.eq(DishFlavor::getDishId, item.getId());
            List<DishFlavor> flavors = dishFlavorService.list(lq);
            dto.setFlavors(flavors);
            return dto;
        }).collect(Collectors.toList());

        return R.success(dtoList);
    }
}
