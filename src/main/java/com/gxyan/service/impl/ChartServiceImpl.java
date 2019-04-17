package com.gxyan.service.impl;

import com.gxyan.common.ServerResponse;
import com.gxyan.dao.OrderDetailsMapper;
import com.gxyan.dao.OrderMapper;
import com.gxyan.service.IChartService;
import com.gxyan.vo.EmpChart;
import com.gxyan.vo.IndexSales;
import com.gxyan.vo.SalesChart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/13 9:19
 */
@Slf4j
@Service
public class ChartServiceImpl implements IChartService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailsMapper detailsMapper;

    @Override
    public ServerResponse getEmpChart(String date) {
        List <EmpChart> chartData = orderMapper.selectChartByDate(date);
        if (chartData != null) {
            return ServerResponse.createBySuccess(chartData);
        }
        return ServerResponse.createByErrorMessage("木有数据哦");
    }

    @Override
    public ServerResponse getIndexChart() {
        List <EmpChart> chartData = orderMapper.selectYesterdayChart();
        if (chartData != null) {
            return ServerResponse.createBySuccess(chartData);
        }
        return ServerResponse.createByErrorMessage("木有数据哦");
    }

    @Override
    public ServerResponse getSaleNum() {
        int num = detailsMapper.selectYesterdayNum();
        return ServerResponse.createBySuccess(num);
    }

    @Override
    public ServerResponse getSalesChart(String start, String end) {
        List<SalesChart> salesCharts =  detailsMapper.selectSalesChart(start, end);
        if (salesCharts != null) {
            return ServerResponse.createBySuccess(salesCharts);
        }
        return ServerResponse.createByErrorMessage("木有数据哦");
    }

    @Override
    public ServerResponse getIndexSales(Integer id) {
        IndexSales indexSales = detailsMapper.selectIndexSales(id);
        if (indexSales != null) {
            return ServerResponse.createBySuccess(indexSales);
        }
        return ServerResponse.createByErrorMessage("木有数据哦");
    }

    @Override
    public ServerResponse getEmpSalesChart(Integer id, String date) {
        List <EmpChart> chartData = detailsMapper.selectEmpChart(id, date);
        if (chartData != null) {
            return ServerResponse.createBySuccess(chartData);
        }
        return ServerResponse.createByErrorMessage("木有数据哦");
    }
}
