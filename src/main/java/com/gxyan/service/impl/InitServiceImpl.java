package com.gxyan.service.impl;

import com.gxyan.common.ServerResponse;
import com.gxyan.dao.BrandMapper;
import com.gxyan.dao.SeriesMapper;
import com.gxyan.pojo.Brand;
import com.gxyan.pojo.Series;
import com.gxyan.service.IInitService;
import com.gxyan.vo.SeriesTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/2 21:22
 */
@Service
public class InitServiceImpl implements IInitService {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SeriesMapper seriesMapper;

    @Override
    public ServerResponse <List <SeriesTree>> seriesOpt() {
        List <Brand> brandList = brandMapper.getBrand();

        if (!CollectionUtils.isEmpty(brandList)) {
            List<SeriesTree> treeList = new ArrayList <>();
            for (Brand brandItem : brandList) {
                SeriesTree tree = new SeriesTree();
                tree.setValue(brandItem.getBrandId());
                tree.setLabel(brandItem.getBrandName());

                List <Series> seriesList = seriesMapper.selectSeriesByBrand(brandItem.getBrandId());
                if (!CollectionUtils.isEmpty(seriesList)) {
                    List<SeriesTree> seriesTreeList = new ArrayList <>();
                    for (Series series : seriesList) {
                        SeriesTree seriesTree = new SeriesTree();
                        seriesTree.setValue(series.getSeriesId());
                        seriesTree.setLabel(series.getSeriesName());
                        seriesTreeList.add(seriesTree);
                    }
                    tree.setChildren(seriesTreeList);
                    treeList.add(tree);
                }
            }
            return ServerResponse.createBySuccess(treeList);
        }
        return ServerResponse.createByErrorMessage("未知错误，请联系管理员");
    }

    @Override
    public ServerResponse <List <Brand>> brandOpt() {
        List <Brand> brandList = brandMapper.getBrand();

        if (!CollectionUtils.isEmpty(brandList)) {
            return ServerResponse.createBySuccess(brandList);
        } else {
            return ServerResponse.createByError();
        }
    }
}
