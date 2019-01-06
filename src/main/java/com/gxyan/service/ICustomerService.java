package com.gxyan.service;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Customer;
import com.gxyan.vo.CustomerList;

/**
 * @author gxyan
 * @date 2019/1/6 10:05
 */
public interface ICustomerService {
    ServerResponse addCustomer(Customer customer);

    ServerResponse getList(CustomerList customerList);

    ServerResponse updateCustomer(Customer customer);
}
