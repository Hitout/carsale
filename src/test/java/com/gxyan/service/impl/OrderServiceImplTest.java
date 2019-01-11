package com.gxyan.service.impl;


import com.gxyan.service.IOrderService;
import com.gxyan.vo.OrderQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author gxyan
 * @date 2019/1/10 12:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private IOrderService orderService;

    @Test
    public void getList() {
        orderService.getList(new OrderQuery());
    }

}