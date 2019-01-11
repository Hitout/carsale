package com.gxyan.service.impl;

import com.github.pagehelper.PageHelper;
import com.gxyan.common.Const;
import com.gxyan.common.ServerResponse;
import com.gxyan.dao.BrandMapper;
import com.gxyan.dao.CarMapper;
import com.gxyan.dao.SeriesMapper;
import com.gxyan.pojo.Brand;
import com.gxyan.pojo.Car;
import com.gxyan.pojo.Series;
import com.gxyan.service.IStoreService;
import com.gxyan.vo.ListVo;
import com.gxyan.vo.StoreList;
import com.gxyan.vo.StoreQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/3 10:08
 */
@Slf4j
@Service
public class StoreServiceImpl implements IStoreService {
    
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SeriesMapper seriesMapper;
    @Autowired
    private CarMapper carMapper;

    @Override
    public ServerResponse addBrand(String brandName) {
        // 验证brandName是否存在
        Brand result = brandMapper.selectByBrandName(brandName);
        if (result != null) {
            if (result.getStatus().equals(Const.Number.ONE)) {
                return ServerResponse.createByErrorMessage(brandName + "已存在");
            } else if (result.getStatus().equals(Const.Number.ZERO)) {
                // brand已存在，但处于删除状态
                result.setStatus(Const.Number.ONE);
                int updateResult = brandMapper.updateByPrimaryKey(result);
                if (updateResult != 0) {
                    return ServerResponse.createBySuccess();
                }
            }
        }

        Brand brand = new Brand();
        brand.setBrandName(brandName);
        int resultCount = brandMapper.insertSelective(brand);
        if (resultCount != 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse delBrand(Integer brandId) {
        List <Series> seriesList = seriesMapper.selectSeriesByBrand(brandId);
        if (CollectionUtils.isEmpty(seriesList)) {
            // 品牌没有对应车系，可直接删除
            int result = brandMapper.deleteByPrimaryKey(brandId);
            if (result != 0) {
                return ServerResponse.createBySuccess();
            }
        } else {
            // 品牌有对应车系，把该品牌status置为0
            Brand brand = new Brand();
            brand.setBrandId(brandId);
            brand.setStatus(Const.Number.ZERO);
            int result = brandMapper.updateByPrimaryKeySelective(brand);
            if (result != 0) {
                return ServerResponse.createBySuccess();
            }
        }
        return ServerResponse.createByErrorMessage("未知错误");
    }

    @Override
    public ServerResponse addSeries(Integer brandId, String seriesName) {
        Series result = seriesMapper.selectSeriesByBrandIdAndSeriesName(brandId, seriesName);
        if (result != null) {
            if (result.getStatus().equals(Const.Number.ONE)) {
                return ServerResponse.createByErrorMessage(seriesName + "已存在");
            } else if (result.getStatus().equals(Const.Number.ZERO)) {
                // series已存在，但处于删除状态
                Series series = new Series();
                series.setSeriesId(result.getSeriesId());
                series.setStatus(Const.Number.ONE);
                int updateResult = seriesMapper.updateByPrimaryKeySelective(series);
                if (updateResult != 0) {
                    return ServerResponse.createBySuccess();
                }
            }
        }

        Series series = new Series();
        series.setBrandId(brandId);
        series.setSeriesName(seriesName);
        int resultCount = seriesMapper.insertSelective(series);
        if (resultCount != 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse delSeries(Integer seriesId) {
        Series series = new Series();
        series.setSeriesId(seriesId);
        series.setStatus(Const.Number.ZERO);
        int resultCount = seriesMapper.updateByPrimaryKeySelective(series);
        if (resultCount != 0) {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse addStore(Car car) {
        car.setId(createCarId());
        car.setStatus(Const.Number.ONE);
        int resultCount = carMapper.insertSelective(car);
        if (resultCount != 0) {
            return ServerResponse.createBySuccess();
        }
        log.error(car.toString());
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse getList(StoreQuery storeQuery) {
        List<StoreList> list = PageHelper.startPage(storeQuery.getPage(), storeQuery.getLimit()).doSelectPage(()-> carMapper.selectSelective(storeQuery));
        if (list != null) {
            ListVo listVo = new ListVo();
            listVo.setItems(list);
            listVo.setTotal(PageHelper.count(()->carMapper.selectSelective(storeQuery)));
            return ServerResponse.createBySuccess(listVo);
        }
        return ServerResponse.createByErrorMessage("获取库存列表失败");
    }

    @Override
    public ServerResponse updateStore(Car car) {
        log.info(car.toString());
        int resultCount = carMapper.updateByPrimaryKeySelective(car);
        if (resultCount != 0) {
            return ServerResponse.createBySuccess();
        }
        log.error(car.toString());
        return ServerResponse.createByErrorMessage("更新失败");
    }

    /**
     * 车辆编号
     * 格式为：yyMMdd 加 五位递增的数字，数字每天重置为1
     * @return
     */
    private Long createCarId() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        String format = dateFormat.format(new Date()) + "00000";
        return Long.valueOf(format) + (num++);
    }

    private int num = 1;

    @Scheduled(cron="0 0 0 * * ?")
    private void clearNum() {
        num = 1;
    }
}
