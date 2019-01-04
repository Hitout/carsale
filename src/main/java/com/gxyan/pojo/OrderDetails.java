package com.gxyan.pojo;

public class OrderDetails {
    private Long id;

    private Long orderId;

    private Long carId;

    private Integer carNumber;

    public OrderDetails(Long id, Long orderId, Long carId, Integer carNumber) {
        this.id = id;
        this.orderId = orderId;
        this.carId = carId;
        this.carNumber = carNumber;
    }

    public OrderDetails() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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