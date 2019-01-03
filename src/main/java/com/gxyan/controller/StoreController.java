package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.pojo.Car;
import com.gxyan.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gxyan
 * @date 2019/1/3 10:04
 */
@RestController
@RequestMapping("store")
public class StoreController {

    @Autowired
    private IStoreService storeService;

    @RequestMapping(value = "addBrand", method = RequestMethod.GET)
    public ServerResponse addBrand(String brand) {
        return storeService.addBrand(brand);
    }

    @RequestMapping(value = "delBrand", method = RequestMethod.GET)
    public ServerResponse delBrand(Integer brandId) {
        return storeService.delBrand(brandId);
    }

    @RequestMapping(value = "addSeries", method = RequestMethod.GET)
    public ServerResponse addSeries(Integer brandId, String seriesName) {
        return storeService.addSeries(brandId, seriesName);
    }

    @RequestMapping(value = "delSeries", method = RequestMethod.GET)
    public ServerResponse delSeries(Integer seriesId) {
        return storeService.delSeries(seriesId);
    }

    @RequestMapping(value = "addStore", method = RequestMethod.GET)
    public ServerResponse addStore(Car car) {
        return storeService.addStore(car);
    }
}
