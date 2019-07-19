package com.qf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * cwy 2019/7/15 19:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO {

    private Integer code;
    private String msg;
    private Object data;

}
