package com.sily.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sily.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
