package com.irving.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.irving.commonly.R;
import com.irving.domain.Employee;
import com.irving.mapper.EmployeeMapper;
import com.irving.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    @Override
    public R<Employee> login(Employee employee) {
        String password = employee.getPassword();
        String Md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> lq=new LambdaQueryWrapper();
        lq.eq(Employee::getUsername,employee.getUsername());
        Employee one = employeeMapper.selectOne(lq);
        if (one==null){
          return R.error("用户名不存在");
        }
        if (!one.getPassword().equals(Md5Password)){
            return R.error("密码错误");
        }

        if (one.getStatus()!=1){
            return R.error("账户已被锁定");
        }
        return R.success(one);
    }

    @Override
    public Page<Employee> getPage(Integer page, Integer size) {
        Page<Employee> page1=new Page<>(page,size);
        employeeMapper.selectPage(page1,new LambdaQueryWrapper<>());
        return page1;

    }
}
