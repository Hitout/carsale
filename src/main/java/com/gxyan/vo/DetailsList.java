package com.gxyan.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author gxyan
 * @date 2019/1/11 13:56
 */
@Data
public class DetailsList {
    private String id;

    private Long orderId;

    private Long customerId;

    private Long carId;

    private String employeeName;

    private Integer carNumber;

    private Date createTime;

    private Date updateTime;

    private String status;
}
