package com.irving.controller;

import com.irving.commonly.R;
import com.irving.domain.Orders;
import com.irving.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@RequestMapping("/order")
@Controller
public class OrdersController {
    private final OrderService orderService;

    @PostMapping("/submit")
    @ResponseBody
    public R<?> submit(@RequestBody Orders orders){
        R<?> r=orderService.submit(orders);
        return r;
    }
}
