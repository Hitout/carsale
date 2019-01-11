package com.gxyan.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxyan.common.ServerResponse;
import com.gxyan.dao.CustomerMapper;
import com.gxyan.pojo.Customer;
import com.gxyan.service.ICustomerService;
import com.gxyan.vo.CustomerQuery;
import com.gxyan.vo.ListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/6 10:07
 */
@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public ServerResponse addCustomer(Customer customer) {
        customer.setId(createCustomerId());
        int resultCount = customerMapper.insertSelective(customer);
        if (resultCount != 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse getList(CustomerQuery customerQuery) {
        List<Customer> list = PageHelper.startPage(customerQuery.getPage(), customerQuery.getLimit()).doSelectPage(()-> customerMapper.selectSelective(customerQuery));
        if (list != null) {
            ListVo listVo = new ListVo();
            listVo.setItems(list);
            listVo.setTotal(PageHelper.count(()->customerMapper.selectSelective(customerQuery)));
            return ServerResponse.createBySuccess(listVo);
        }
        return ServerResponse.createByErrorMessage("获取客户列表失败");
    }

    @Override
    public ServerResponse updateCustomer(Customer customer) {
        int resultCount = customerMapper.updateByPrimaryKey(customer);
        if (resultCount != 0) {
            return ServerResponse.createBySuccess();
        }
        log.error(customer.toString());
        return ServerResponse.createByErrorMessage("更新失败");
    }

    /**
     * 客户编号
     * 格式为：yyMMdd 加 五位递增的数字，数字每天重置为1
     * @return
     */
    private Long createCustomerId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String format = dateFormat.format(new Date()) + "10000";
        return Long.valueOf(format) + (num++);
    }

    private int num = 1;

    @Scheduled(cron="0 0 0 * * ?")
    private void clearNum() {
        num = 1;
    }
}
