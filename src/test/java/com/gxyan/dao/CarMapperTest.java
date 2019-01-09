package com.gxyan.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}