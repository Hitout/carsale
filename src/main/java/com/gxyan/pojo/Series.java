package com.gxyan.pojo;

public class Series {
    private Integer seriesId;

    private Integer brandId;

    private String seriesName;

    public Series(Integer seriesId, Integer brandId, String seriesName) {
        this.seriesId = seriesId;
        this.brandId = brandId;
        this.seriesName = seriesName;
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
}