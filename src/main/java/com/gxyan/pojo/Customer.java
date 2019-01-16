package com.gxyan.pojo;

import lombok.ToString;

import java.util.Date;

@ToString
public class Customer {
    private Long id;

    private String name;

    private String phone;

    private String idCard;

    private Date createTime;

    public Customer(Long id, String name, String phone, String idCard, Date createTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.idCard = idCard;
        this.createTime = createTime;
    }

    public Customer() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}