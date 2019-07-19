package com.qf.utils;

import lombok.Data;

import java.util.List;

/**
 * cwy 2019/7/16 11:34
 **/
@Data
public class PageInfo<T> {

    //商品信息和分页信息


    //limit  ?,?
    //当前页
    private Integer page;   //已知

    //        每页显示条数
    private Integer size;   //已知
    //查询总条数
    private Long total;     //已知
    //        计算总页数
    private Integer pages;      //  total%size == 0 ? total/size :total/size +1  ;
    //计算起始索引
    private Integer offest;     //（page - 1）* size

    //       商品信息
    private List<T> list;       //查询得到

    public PageInfo(Integer page, Integer size, Long total) {
        this.page = page <= 0 ? 1 : page;
        this.size = size <= 0 ? 5 : size;
        this.total = total < 0 ? 0 : total;

        //计算出pages 和offest
        this.pages = (int) (this.total % this.size == 0 ? this.total / this.size : this.total / this.size + 1);
        this.offest = (page - 1) * size;
    }
}
