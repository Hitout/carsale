package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Car;
import com.gxyan.pojo.Customer;
import com.gxyan.service.ICustomerService;
import com.gxyan.vo.CustomerList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gxyan
 * @date 2019/1/6 10:03
 */
@Slf4j
@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "addCustomer", method = RequestMethod.GET)
    public ServerResponse addCustomer(Customer customer) {
        return customerService.addCustomer(customer);
    }

    @RequestMapping(value = "getList", method = RequestMethod.GET)
    public ServerResponse getList(CustomerList customerList) {
        return customerService.getList(customerList);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ServerResponse update(Customer customer) {
        return customerService.updateCustomer(customer);
    }
}
