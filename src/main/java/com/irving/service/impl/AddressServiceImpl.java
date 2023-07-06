package com.irving.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.domain.AddressBook;
import com.irving.mapper.AddressMapper;
import com.irving.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressBook> implements AddressService {
    @Transactional
    @Override
    public boolean updateDefault(AddressBook addressBook) {
        boolean b=true;
        List<AddressBook> collect = this.list().stream().map(item -> {
            item.setIsDefault(0);
            return item;
        }).collect(Collectors.toList());
        boolean b1 = this.updateBatchById(collect);
        if (!b1){
            b=false;
        }
        addressBook.setIsDefault(1);
        boolean b2 = this.updateById(addressBook);
        if (!b2){
            b=false;
        }
        return b;
    }
}
