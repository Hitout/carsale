package com.gxyan.dao;

import com.gxyan.pojo.Order;
import com.gxyan.vo.OrderList;
import com.gxyan.vo.Sale;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updatePayTimeByPrimaryKey(Long orderId);

    List<Sale> selectSale(OrderList orderList);

    int updateStatusByPrimaryKey(@Param("orderId") Long orderId, @Param("status") String status);

    int addTotalPriceByPrimaryKey(@Param("orderId") Long orderId, @Param("totalPrice") BigDecimal totalPrice);
}