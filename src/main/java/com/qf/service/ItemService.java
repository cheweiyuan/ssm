package com.qf.service;

import com.qf.pojo.Item;
import com.qf.utils.PageInfo;

/**
 * cwy 2019/7/16 11:37
 **/
public interface ItemService {

    //分页查找商品
    PageInfo<Item> findItemByNameLikeAndLimit(String name,Integer page,Integer size);

    //添加商品
    Integer save(Item item);

    //删除商品
    Integer del(Long id);

    Item findById(Integer id);

    Integer updateById(Item item);
}
