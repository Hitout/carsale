package com.gxyan.service.impl;

import com.gxyan.common.Const;
import com.gxyan.common.ServerResponse;
import com.gxyan.dao.CarMapper;
import com.gxyan.dao.OrderDetailsMapper;
import com.gxyan.dao.OrderMapper;
import com.gxyan.pojo.Order;
import com.gxyan.pojo.OrderDetails;
import com.gxyan.service.IOrderService;
import com.gxyan.vo.OrderDetailVo;
import com.gxyan.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author gxyan
 * @date 2019/1/3 13:05
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailsMapper detailsMapper;

    @Override
    @Transactional
    public ServerResponse addOrder(OrderVo orderVo) {
        log.info(orderVo.toString());
        Order order = new Order();
        Long orderId =  createOrderId();
        order.setId(orderId);
        order.setCustomerId(orderVo.getCustomerId());
        order.setEmployeeId(orderVo.getEmployeeId());
        order.setStatus(orderVo.getStatus());
        order.setTotalPrice(orderVo.getTotalPrice());
        int result = orderMapper.insert(order);
        if (result == 0) {
            return ServerResponse.createByErrorMessage("添加订单失败");
        }
        if (orderVo.getStatus().equals(Const.Number.ONE)) {
            result = orderMapper.updatePayTimeByPrimaryKey(orderId);
            if (result == 0) {
                return ServerResponse.createByErrorMessage("添加订单失败");
            }
        }
        for (OrderDetailVo detailVo: orderVo.getDetailVos()) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setId(createOrderDetailsId());
            orderDetails.setCarId(detailVo.getCarId());
            int num = detailVo.getCarNumber();
            orderDetails.setCarNumber(num);
            orderDetails.setOrderId(orderId);
            result = detailsMapper.insert(orderDetails);
            if (result == 0) {
                return ServerResponse.createByErrorMessage("添加订单失败");
            }
            int repertory = carMapper.selectRepertoryByPrimaryKey(detailVo.getCarId());
            num = repertory - num;
            if (num >= 0){
                result = carMapper.updateRepertoryByPrimaryKey(detailVo.getCarId(), num);
                if (result == 0) {
                    return ServerResponse.createByErrorMessage("添加订单失败");
                }
            } else {
                // 手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.createByErrorMessage("库存不足，添加订单失败");
            }
        }
        return ServerResponse.createBySuccess();
    }

    /**
     * 订单编号
     * 格式为：yyMMdd 加5位递增的数字，数字每天重置为1
     * @return
     */
    private Long createOrderId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String format = dateFormat.format(new Date()) + "00000";
        return Long.valueOf(format) + (num++);
    }

    private int num = 3;

    @Scheduled(cron="0 0 0 * * ?")
    private void clearNum() {
        num = 1;
    }

    private String createOrderDetailsId() {
        int first = new Random(10).nextInt(8) + 1;
        System.out.println(first);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return first + String.format("%015d", hashCodeV);
    }
}
