package com.gxyan.service;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Car;

/**
 * @author gxyan
 * @date 2019/1/3 10:07
 */
public interface IStoreService {

    ServerResponse addBrand(String brandName);

    ServerResponse delBrand(Integer brandId);

    ServerResponse addSeries(Integer brandId, String seriesName);

    ServerResponse delSeries(Integer seriesId);

    ServerResponse addStore(Car car);
}
