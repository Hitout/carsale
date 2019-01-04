package com.gxyan.dao;

import com.gxyan.pojo.Car;
import com.gxyan.vo.StoreList;

import java.util.List;

public interface CarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Car record);

    int insertSelective(Car record);

    Car selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);

    List<Car> selectSelective(StoreList storeList);

    List<Car> selectByBrandId(Integer brandId);
}