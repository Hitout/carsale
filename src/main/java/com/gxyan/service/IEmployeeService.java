package com.gxyan.service;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Employee;
import com.gxyan.vo.EmployeeList;

/**
 * @author gxyan
 * @date 2019/1/6 16:55
 */
public interface IEmployeeService {
    ServerResponse addEmployee(Employee employee);

    ServerResponse updateEmployee(Employee employee);

    ServerResponse getList(EmployeeList employeeList);
}
