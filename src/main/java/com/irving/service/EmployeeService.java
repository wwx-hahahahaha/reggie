package com.irving.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.commonly.R;
import com.irving.domain.Employee;
import com.irving.mapper.EmployeeMapper;

public interface EmployeeService extends IService<Employee> {
    R<Employee> login(Employee employee);

    Page<Employee> getPage(Integer page, Integer size);
}
