package com.gxyan.vo;

import lombok.*;

/**
 * @author gxyan
 * @date 2019/1/4 20:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreList {
    private Long id;

    private Integer brandId;

    private Integer seriesId;

    private String status;

    private int page = 1;

    private int limit = 10;

    private String orderBy;
}
