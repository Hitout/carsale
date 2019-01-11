package com.gxyan.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxyan.common.Const;
import com.gxyan.common.ServerResponse;
import com.gxyan.dao.CarMapper;
import com.gxyan.dao.OrderDetailsMapper;
import com.gxyan.dao.OrderMapper;
import com.gxyan.pojo.Order;
import com.gxyan.pojo.OrderDetails;
import com.gxyan.service.IOrderService;
import com.gxyan.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    @Transactional(rollbackFor = Exception.class)
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
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("添加订单失败");
        }
        if (orderVo.getStatus().equals(Const.Number.ONE)) {
            result = orderMapper.updatePayTimeByPrimaryKey(orderId);
            if (result == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.createByErrorMessage("添加订单失败");
            }
            int repertory = carMapper.selectRepertoryByPrimaryKey(detailVo.getCarId());
            num = repertory - num;
            if (num >= 0){
                result = carMapper.updateRepertoryByPrimaryKey(detailVo.getCarId(), num);
                if (result == 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ServerResponse.createByErrorMessage("添加订单失败");
                }
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.createByErrorMessage("库存不足，添加订单失败");
            }
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse getList(OrderQuery orderQuery) {
        List<OrderList> list = PageHelper.startPage(orderQuery.getPage(), orderQuery.getLimit()).doSelectPage(()-> orderMapper.selectSale(orderQuery));
        if (list != null) {
            for (OrderList orderList: list) {
                List<Details> details = detailsMapper.selectDetailsByOrderId(orderList.getOrderId());
                orderList.setDetails(details);
            }
            ListVo listVo = new ListVo();
            listVo.setItems(list);
            listVo.setTotal(PageHelper.count(()->orderMapper.selectSale(orderQuery)));
            return ServerResponse.createBySuccess(listVo);
        }
        return ServerResponse.createByErrorMessage("获取订单列表失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse update(Long orderId, String status) {
        int result = orderMapper.updateStatusByPrimaryKey(orderId, status);
        if (result == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("更新订单失败");
        }
        if (status.equals(Const.Number.ONE)) {
            // status=1，支付订单，更新支付时间
            result = orderMapper.updatePayTimeByPrimaryKey(orderId);
            if (result == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.createByErrorMessage("更新订单失败");
            }
        } else if (status.equals(Const.Number.TWO)) {
            // status=2，取消订单，车辆回库
            List <OrderDetails> details = detailsMapper.selectByOrderId(orderId);
            for (OrderDetails orderDetails: details) {
                result = carMapper.addRepertoryByPrimaryKey(orderDetails.getCarId(), orderDetails.getCarNumber());
                if (result == 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ServerResponse.createByErrorMessage("更新订单失败");
                }
            }
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse updateDetail(OrderDetails orderDetails) {
        OrderDetails oldDetails = detailsMapper.selectByPrimaryKey(orderDetails.getId());
        int result = carMapper.addRepertoryByPrimaryKey(oldDetails.getCarId(), oldDetails.getCarNumber());
        if (result == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        int num = carMapper.selectRepertoryByPrimaryKey(orderDetails.getCarId()) - orderDetails.getCarNumber();
        if (num >= 0){
            result = carMapper.updateRepertoryByPrimaryKey(orderDetails.getCarId(), num);
            if (result == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.createByErrorMessage("添加订单失败");
            }
        } else {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("库存不足，添加订单失败");
        }
        result = detailsMapper.updateByPrimaryKey(orderDetails);
        if (result == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        BigDecimal oldPrice = carMapper.selectSalePriceByPrimaryKey(oldDetails.getCarId()).multiply(BigDecimal.valueOf(oldDetails.getCarNumber()));
        BigDecimal newPrice = carMapper.selectSalePriceByPrimaryKey(orderDetails.getCarId()).multiply(BigDecimal.valueOf(orderDetails.getCarNumber()));
        result = orderMapper.addTotalPriceByPrimaryKey(orderDetails.getOrderId(), newPrice.subtract(oldPrice));
        if (result == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServerResponse deleteDetail(String id) {
        OrderDetails orderDetails = detailsMapper.selectByPrimaryKey(id);
        BigDecimal totalPrice = carMapper.selectSalePriceByPrimaryKey(orderDetails.getCarId()).negate();
        int result = orderMapper.addTotalPriceByPrimaryKey(orderDetails.getOrderId(), totalPrice);
        if (result == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("删除失败");
        }
        result = detailsMapper.deleteByPrimaryKey(id);
        if (result == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse getDetailsList(DetailsQuery detailsQuery) {
        List<DetailsList> list = PageHelper.startPage(detailsQuery.getPage(), detailsQuery.getLimit()).doSelectPage(()-> detailsMapper.selectSelective(detailsQuery));
        if (list != null) {
            ListVo listVo = new ListVo();
            listVo.setItems(list);
            listVo.setTotal(PageHelper.count(()->detailsMapper.selectSelective(detailsQuery)));
            return ServerResponse.createBySuccess(listVo);
        }
        return ServerResponse.createByErrorMessage("获取订单详情列表失败");
    }

    /**
     * 订单编号
     * 格式为：yyMMdd 加6位递增的数字，数字每天重置为1
     * @return
     */
    private Long createOrderId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String format = dateFormat.format(new Date()) + "000000";
        return Long.valueOf(format) + (num++);
    }

    private int num = 1;

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
