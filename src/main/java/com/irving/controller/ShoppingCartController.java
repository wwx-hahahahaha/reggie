package com.irving.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.irving.commonly.R;
import com.irving.utlis.ThreadLocalUtils;
import com.irving.domain.ShoppingCart;
import com.irving.domain.User;
import com.irving.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/shoppingCart")
@Controller
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    @ResponseBody
    public R<?> list(){
        User user = (User) ThreadLocalUtils.getObjects();
        LambdaQueryWrapper<ShoppingCart> lm=new LambdaQueryWrapper<>();
        lm.eq(ShoppingCart::getUserId,user.getId());
        lm.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(lm);
        return R.success(list);
    }

    @PostMapping("/add")
    @ResponseBody
    public R<?> add(@RequestBody ShoppingCart shoppingCart, HttpSession session){
        User user = (User) session.getAttribute("user");
        shoppingCart.setUserId(user.getId());
        LambdaQueryWrapper<ShoppingCart>lm=new LambdaQueryWrapper<>();
        lm.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        lm.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        ShoppingCart one = shoppingCartService.getOne(lm);
        if (one==null){
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            return R.success("添加成功");
        }
        Integer number = one.getNumber();
        one.setNumber(++number);
        shoppingCartService.updateById(one);
        return R.success("添加成功");
    }

    @PostMapping("/sub")
    @ResponseBody
    public R<?> sub(@RequestBody ShoppingCart shoppingCart){
        LambdaQueryWrapper<ShoppingCart> lm=new LambdaQueryWrapper<>();
        lm.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        lm.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        ShoppingCart one = shoppingCartService.getOne(lm);
        if (one==null){
            return R.error("当前未添加");
        }
        Integer number = one.getNumber();
        if (number>0){
            one.setNumber(--number);
            shoppingCartService.updateById(one);
        }
        return R.success("成功");
    }

    @DeleteMapping("/clean")
    @ResponseBody
    public R<?> clear(){
        LambdaQueryWrapper<ShoppingCart>lm=new LambdaQueryWrapper<>();
        User user = (User) ThreadLocalUtils.getObjects();
        lm.eq(ShoppingCart::getUserId,user.getId());
        shoppingCartService.remove(lm);
        return R.success("删除成功");
    }
}
