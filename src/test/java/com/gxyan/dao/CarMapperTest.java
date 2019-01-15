package com.gxyan.dao;

import com.gxyan.pojo.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/9 21:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarMapperTest {
    @Autowired
    private CarMapper carMapper;

    @Test
    public void updateRepertoryByPrimaryKey() {
        carMapper.updateRepertoryByPrimaryKey(19010400001L, 1);
    }

    @Test
    public void selectBySeriesId() {
        List <Car> list = carMapper.selectBySeriesId(9);
        System.out.println(list.toString());
    }
}