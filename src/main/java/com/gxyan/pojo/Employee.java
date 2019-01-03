package com.gxyan.pojo;

import lombok.ToString;

import java.util.Date;

@ToString
public class Employee {

    private Integer id;

    private Integer role;

    private String name;

    private String password;

    private String idCard;

    private String phone;

    private String gender;

    private String salary;

    private Date entryTime;

    private String status;

    public Employee(Integer id, Integer role, String name, String password, String idCard, String phone, String gender, String salary, Date entryTime, String status) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.password = password;
        this.idCard = idCard;
        this.phone = phone;
        this.gender = gender;
        this.salary = salary;
        this.entryTime = entryTime;
        this.status = status;
    }

    public Employee() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary == null ? null : salary.trim();
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}