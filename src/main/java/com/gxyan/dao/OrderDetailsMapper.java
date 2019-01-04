package com.gxyan.dao;

import com.gxyan.pojo.OrderDetails;

public interface OrderDetailsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetails record);

    int insertSelective(OrderDetails record);

    OrderDetails selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetails record);

    int updateByPrimaryKey(OrderDetails record);
}