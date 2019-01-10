package com.gxyan.service;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.OrderDetails;
import com.gxyan.vo.OrderDetailVo;
import com.gxyan.vo.OrderList;
import com.gxyan.vo.OrderVo;

import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/2 21:16
 */
public interface IOrderService {
    ServerResponse addOrder(OrderVo orderVo);

    ServerResponse getList(OrderList orderList);

    ServerResponse update(Long orderId, String status);

    ServerResponse updateDetail(OrderDetails orderDetails);
}
