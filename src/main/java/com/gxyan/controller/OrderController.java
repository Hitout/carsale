package com.gxyan.controller;

import com.gxyan.common.ServerResponse;
import com.gxyan.service.IOrderService;
import com.gxyan.vo.SeriesTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gxyan
 * @date 2018/12/28 17:00
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ServerResponse create() {
        return null;
    }

    @RequestMapping(value = "seriesOpt", method = RequestMethod.POST)
    public ServerResponse seriesOpt() {
        return orderService.seriesOpt();
    }
}
