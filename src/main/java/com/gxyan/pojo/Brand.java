package com.gxyan.pojo;

public class Brand {
    private Integer brandId;

    private String brandName;

    private String status;

    public Brand(Integer brandId, String brandName, String status) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.status = status;
    }

    public Brand() {
        super();
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}