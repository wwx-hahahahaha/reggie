package com.irving.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.irving.commonly.R;
import com.irving.domain.Dish;
import com.irving.domain.Setmeal;
import com.irving.domain.SetmealDish;
import com.irving.dto.SetmealDto;
import com.irving.service.DishService;
import com.irving.service.SetmealDishService;
import com.irving.service.SetmealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("setmeal")
@Controller
public class SetmealController {
    private final SetmealService setmealService;
    private final SetmealDishService setmealDishService;

    @ResponseBody
    @GetMapping("page")
    public R<?> page(@RequestParam("page")Integer page,@RequestParam("pageSize")Integer pageSize,@RequestParam(value = "name",required = false)String name){
        Page<SetmealDto> dto =setmealService.getPage(page,pageSize,name);
        if (dto!=null){
            return R.success(dto);
        }
        return R.error("出现异常");
    }


    @ResponseBody
    @PostMapping("status/{num}")
    public R<?> status(@PathVariable("num")Integer num,@RequestParam("ids")Long[]ids){
        if (num==0){
            List<Setmeal> list = Arrays.stream(ids).map(id -> {
                return Setmeal.builder().id(id).status(0).build();
            }).collect(Collectors.toList());
            boolean b = setmealService.updateBatchById(list);
            if (b){
                return R.success("禁售成功");
            }
            return R.error("禁售失败");
        }
        List<Setmeal> list = Arrays.stream(ids).map(id -> {
            return Setmeal.builder().status(1).id(id).build();
        }).collect(Collectors.toList());

        boolean b = setmealService.updateBatchById(list);
        if (b){
            return R.success("启售成功");
        }
        return R.error("启售失败");
    }

    @DeleteMapping()
    @ResponseBody
    public R<?> delete(@RequestParam("ids")Long[] ids){
        boolean remove=false;
        for (Long id : ids) {
            LambdaQueryWrapper<Setmeal> lm=new LambdaQueryWrapper<>();
            lm.eq(Setmeal::getId,id);
             remove = setmealService.remove(lm);
        }
        if (remove){
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @PostMapping()
    @ResponseBody
    public R<?> save(@RequestBody SetmealDto dto){
        boolean b=setmealService.mySave(dto);
        if (b){
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }

    @GetMapping("{id}")
    @ResponseBody
    public R<?> GetOne(@PathVariable("id") long id){
        SetmealDto dto=setmealService.get(id);
        return R.success(dto);
    }

    @PutMapping
    @ResponseBody
    public R<?> Save(@RequestBody SetmealDto dto){
        boolean b=setmealService.MyUpdate(dto);
        if (b){
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }

    @GetMapping("/list")
    @ResponseBody
    public R<?> list(@RequestParam("categoryId")Long id,@RequestParam("status")Integer status){
        LambdaQueryWrapper<Setmeal> lm=new LambdaQueryWrapper<>();
        lm.eq(id!=null,Setmeal::getCategoryId,id);
        lm.eq(status!=null,Setmeal::getStatus,status);
        lm.orderByDesc(Setmeal::getCreateTime);
        List<Setmeal> list = setmealService.list(lm);
        return R.success(list);
    }

}
