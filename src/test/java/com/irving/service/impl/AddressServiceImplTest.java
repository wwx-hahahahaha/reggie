package com.irving.service.impl;

import com.irving.domain.AddressBook;
import com.irving.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class AddressServiceImplTest {
    @Resource
    private AddressService addressService;

    @Test
    void demo(){
        List<AddressBook> list = addressService.list();
        log.info(list.toString());
    }
}