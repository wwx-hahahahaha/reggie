package com.irving.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.irving.domain.AddressBook;

public interface AddressService extends IService<AddressBook> {
    boolean updateDefault(AddressBook addressBook);
}
