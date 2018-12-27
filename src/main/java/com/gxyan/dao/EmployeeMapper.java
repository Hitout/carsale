package com.gxyan.dao;

import com.gxyan.pojo.Employee;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    int checkUserId(Integer id);

    Employee selectLogin(@Param("id") Integer id, @Param("password") String password);
}