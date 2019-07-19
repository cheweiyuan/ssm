package com.qf.mapper;

import com.qf.AcTests;
import com.qf.pojo.Item;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * cwy 2019/7/16 11:54
 **/
public class ItemMapperTest extends AcTests {

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void findCountByName() {
        Long count = itemMapper.findCountByName(null);
        System.out.println(count);
    }

    @Test
    public void findItemByNameLikeAndLimit() {
        List<Item> item = itemMapper.findItemByNameLikeAndLimit(null, 0, 5);
        System.out.println(item);
    }

    @Test
    @Transactional
    public void save(){
        Item item = new Item();
        item.setName("xxxx");
        item.setPrice(new BigDecimal(1111));
        item.setProductionDate(new Date());
        item.setDescription("xxxxxxxxxxxxx");
        item.setPic("zzzzzzzzzzzzzzzzzz");

        Integer count = itemMapper.save(item);
        Assert.assertEquals(new Integer(1),count);

    }

    @Test
    @Transactional
    public void del(){
        Integer count = itemMapper.delById(16L);
        Assert.assertEquals(new Integer(1), count);
    }

    @Test
    @Transactional
    public void updateById(){

        Item item = new Item();
        item.setId(16L);
        item.setName("测试");
        item.setPrice(new BigDecimal(888.88));
        item.setProductionDate(new Date());
        item.setDescription("xxxxxxxxxx");
        item.setPic("yyyyyyyyyyyyyyyyyyy");
        Integer count = itemMapper.updateById(item);
        System.out.println(count);
    }

    @Test
    public void findById(){
        Item item = itemMapper.findById(17);
        System.out.println(item);
    }

}