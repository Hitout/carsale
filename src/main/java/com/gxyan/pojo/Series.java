package com.gxyan.pojo;

public class Series {
    private Integer seriesId;

    private Integer brandId;

    private String seriesName;

    private String status;

    public Series(Integer seriesId, Integer brandId, String seriesName, String status) {
        this.seriesId = seriesId;
        this.brandId = brandId;
        this.seriesName = seriesName;
        this.status = status;
    }

    public Series() {
        super();
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName == null ? null : seriesName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}