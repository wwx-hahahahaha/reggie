package com.irving.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.irving.commonly.R;
import com.irving.domain.Category;
import com.irving.domain.Dish;
import com.irving.service.CategoryService;
import com.irving.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RequestMapping("/category")
@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final DishService dishService;

    @PostMapping()
    @ResponseBody
    public R<String> SaveCategory(@RequestBody Category category) {
        category.setId(new Random().nextLong());
        boolean save = categoryService.save(category);
        if (save) {
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }


    @ResponseBody
    @GetMapping("/page")
    public R<?> page(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        Page<Category> getPage = categoryService.page(new Page<Category>(page, pageSize));
        if (getPage != null) {
            return R.success(getPage);
        }
        return R.success("查询失败");
    }

    @DeleteMapping()
    @ResponseBody
    public R<?> Delete(@RequestParam("ids") Long ids) {
        boolean remove = categoryService.removeById(ids);
        if (remove) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @PutMapping()
    @ResponseBody
    public R<?> update(@RequestBody Category category) {
        boolean save = categoryService.updateById(category);
        if (save) {
            return R.success("修改成功");
        }

        return R.error("修改失败");
    }

    @GetMapping("/list")
    @ResponseBody
    public R<?> list(@RequestParam(value = "type", required = false) Integer type) {
        if (type == null) {
            LambdaQueryWrapper<Category>lm=new LambdaQueryWrapper<>();
            lm.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
            List<Category> categories = categoryService.getBaseMapper().selectList(lm);
            return R.success(categories);
        }

        List<Category> list = categoryService.getType(type);
        if (list != null) {
            return R.success(list);
        }

        return R.error("错误");
    }
}
