package com.gxyan.dao;

import com.gxyan.common.Const;
import com.gxyan.pojo.Order;
import com.gxyan.vo.OrderList;
import com.gxyan.vo.Sale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author gxyan
 * @date 2019/1/9 19:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void insert() {
        Long lo = 123L;
        Order order = new Order();
        order.setId(lo);
        order.setStatus(Const.Number.ZERO);
        order.setTotalPrice(BigDecimal.valueOf(234566));
        order.setEmployeeId(123456);
        lo = 19010610005L;
        order.setCustomerId(lo);
        System.out.println(order.toString());
//        System.out.println(orderMapper.selectByPrimaryKey(lo).toString());
        orderMapper.insert(order);
    }

    @Test
    public void selectSale() {
        OrderList orderList = new OrderList();
        List <Sale> sales = orderMapper.selectSale(orderList);
        System.out.println(sales.toString());
    }
}