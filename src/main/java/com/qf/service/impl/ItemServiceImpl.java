package com.qf.service.impl;

import com.qf.mapper.ItemMapper;
import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * cwy 2019/7/16 11:37
 **/
@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageInfo<Item> findItemByNameLikeAndLimit(String name, Integer page, Integer size) {
        //查询数据总条数
        Long total = itemMapper.findCountByName(name);

        //创建pageInfo对象
        PageInfo<Item> pageInfo = new PageInfo<>(page, size, total);
        //查询商品信息list
        List<Item> list = itemMapper.findItemByNameLikeAndLimit(name, pageInfo.getOffest(), pageInfo.getSize());
        //list集合set到pageInfo中
        pageInfo.setList(list);

        return pageInfo;
    }

    @Override
    @Transactional
    public Integer save(Item item) {
        return itemMapper.save(item);
    }

    @Override
    public Integer del(Long id) {
        return itemMapper.delById(id);
    }

    @Override
    public Item findById(Integer id) {
        return itemMapper.findById(id);
    }

    @Override
    public Integer updateById(Item item) {
        return itemMapper.updateById(item);
    }
}
