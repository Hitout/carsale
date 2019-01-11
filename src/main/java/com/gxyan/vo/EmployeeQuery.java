package com.gxyan.vo;

import lombok.Data;

/**
 * @author gxyan
 * @date 2019/1/6 20:30
 */
@Data
public class EmployeeQuery {
    private int page = 1;

    private int limit = 5;

    private Long id;

    private String name;

    private String phone;

    private String idCard;

    private String status;

    private String orderBy;
}
