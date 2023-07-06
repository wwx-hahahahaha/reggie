package com.irving.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.irving.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
