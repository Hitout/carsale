package com.gxyan.vo;

import lombok.*;

/**
 * 接收库存的getList()请求
 * @author gxyan
 * @date 2019/1/4 20:07
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreQuery {
    private Long id;

    private Integer brandId;

    private Integer seriesId;

    private String status;

    private int page = 1;

    private int limit = 5;

    private String orderBy;
}
