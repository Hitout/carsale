package com.gxyan.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author gxyan
 * @date 2019/1/5 9:20
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListVo {

    private List items;

    private Long total;
}
