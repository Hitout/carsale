package com.gxyan.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/2 21:18
 */
@Getter
@Setter
public class SeriesTree {
    private Integer value;
    private String label;
    private List<SeriesTree> children;
}
