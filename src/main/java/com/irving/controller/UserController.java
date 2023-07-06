package com.irving.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.irving.commonly.R;
import com.irving.domain.User;
import com.irving.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("user")
@Controller
public class UserController {
    private final UserService userService;

    @PostMapping("login")
    @ResponseBody
    public R<?> login(@RequestBody User user, HttpSession session){
        LambdaQueryWrapper<User> lm=new LambdaQueryWrapper<>();
        lm.eq(User::getPhone,user.getPhone());
        User serviceOne = userService.getOne(lm);
        if (serviceOne==null){
            User build = User.builder().phone(user.getPhone()).status(1).build();
            userService.save(build);
        }
        session.setAttribute("user",serviceOne);
        return R.success("登录成功");
    }
}
