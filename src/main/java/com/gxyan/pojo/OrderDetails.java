package com.gxyan.pojo;

import lombok.ToString;

@ToString
public class OrderDetails {
    private String id;

    private Long orderId;

    private Long carId;

    private Integer carNumber;

    public OrderDetails() {
        super();
    }

    public OrderDetails(String id, Long orderId, Long carId, Integer carNumber) {
        this.id = id;
        this.orderId = orderId;
        this.carId = carId;
        this.carNumber = carNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Integer getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Integer carNumber) {
        this.carNumber = carNumber;
    }
}