package com.irving.service.impl;

import com.irving.service.SetmealDishService;
import com.irving.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class SetmealServiceImplTest {
    @Resource
    private SetmealDishService setmealDishService;

    @Resource
    private SetmealService setmealService;
    @Test
    void demo(){
        log.info(setmealService.getPage(1,2,"1").toString());
    }
}