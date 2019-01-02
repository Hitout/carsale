package com.gxyan.service;

import com.gxyan.common.ServerResponse;
import com.gxyan.vo.SeriesTree;

import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/2 21:16
 */
public interface IOrderService {

    ServerResponse <List <SeriesTree>> seriesOpt();
}
