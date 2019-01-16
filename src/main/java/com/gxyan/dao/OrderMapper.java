package com.gxyan.dao;

import com.gxyan.pojo.Order;
import com.gxyan.vo.EmpChart;
import com.gxyan.vo.OrderQuery;
import com.gxyan.vo.OrderList;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updatePayTimeByPrimaryKey(Long orderId);

    List<OrderList> selectSale(OrderQuery orderQuery);

    int updateStatusByPrimaryKey(@Param("orderId") Long orderId, @Param("status") String status);

    int addTotalPriceByPrimaryKey(@Param("orderId") Long orderId, @Param("totalPrice") BigDecimal totalPrice);

    List<EmpChart> selectChartByDate(String date);

    List<EmpChart> selectYesterdayChart();
}