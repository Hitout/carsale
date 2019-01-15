package com.gxyan.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gxyan
 * @date 2019/1/10 11:54
 */
@Data
public class Details {
    private String id;

    private Long carId;

    private Integer brandId;

    private Integer seriesId;

    private String brandName;

    private String seriesName;

    private String type;

    private String color;

    private BigDecimal salePrice;

    private Integer carNumber;
}
