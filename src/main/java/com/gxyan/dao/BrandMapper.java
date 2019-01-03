package com.gxyan.dao;

import com.gxyan.pojo.Brand;

import java.util.List;

public interface BrandMapper {
    int deleteByPrimaryKey(Integer brandId);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Integer brandId);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);

    List<Brand> getBrand();

    Brand selectByBrandName(String brandName);
}