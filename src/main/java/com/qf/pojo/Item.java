package com.qf.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * cwy 2019/7/16 11:19
 **/
@Data
public class Item {

    //id
    private  Long id;
    //商品名称
    @NotBlank(message = "商品名称为必填项")
    private String name;
    //商品价格
    @NotNull(message = "商品价格为必填项")
    private BigDecimal price;
    //生产日期
    @NotNull(message = "商品生产日期为必填项")
    @DateTimeFormat(pattern = "yyyy-MM-dd")    //如果不对日期格式进行格式化 报400错误
    private Date productionDate;
    //描述
    @NotBlank(message = "商品描述为必填项")
    private String description;

    @NotNull(message = "商品图片为必填项")
    private MultipartFile picFile;

    //图片
    private String pic;
    //商品的创建时间
    private Date created;
}
