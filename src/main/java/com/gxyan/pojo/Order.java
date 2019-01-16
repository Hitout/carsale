package com.gxyan.pojo;

import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@ToString
public class Order {
    private Long id;

    private Long customerId;

    private Integer employeeId;

    private BigDecimal totalPrice;

    private String status;

    private Date createTime;

    private Date payTime;

    private Date updateTime;

    public Order(Long id, Long customerId, Integer employeeId, BigDecimal totalPrice, String status, Date createTime, Date payTime, Date updateTime) {
        this.id = id;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createTime = createTime;
        this.payTime = payTime;
        this.updateTime = updateTime;
    }

    public Order() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}