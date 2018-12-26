package com.gxyan.pojo;

public class OrderDetails {
    private Integer id;

    private Integer orderId;

    private Integer carId;

    private Integer carNumber;

    public OrderDetails(Integer id, Integer orderId, Integer carId, Integer carNumber) {
        this.id = id;
        this.orderId = orderId;
        this.carId = carId;
        this.carNumber = carNumber;
    }

    public OrderDetails() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(Integer carNumber) {
        this.carNumber = carNumber;
    }
}