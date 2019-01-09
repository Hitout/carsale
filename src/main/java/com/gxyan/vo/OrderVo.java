package com.gxyan.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/8 14:40
 */
@Data
public class OrderVo {
    private Long customerId;

    private Integer employeeId;

    private String status;

    private BigDecimal totalPrice;

    private List<OrderDetailVo> detailVos;
}
