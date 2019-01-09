package com.gxyan.pojo;

import lombok.Data;

@Data
public class OrderDetails {
    private String id;

    private Long orderId;

    private Long carId;

    private Integer carNumber;
}