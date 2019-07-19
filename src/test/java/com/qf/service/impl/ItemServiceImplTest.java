package com.qf.service.impl;

import com.qf.AcTests;
import com.qf.pojo.Item;
import com.qf.service.ItemService;
import com.qf.utils.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * cwy 2019/7/16 12:03
 **/
public class ItemServiceImplTest extends AcTests {

    @Autowired
    private ItemService itemService;


    @Test
    public void findItemByNameLikeAndLimit() {
        PageInfo<Item> list = itemService.findItemByNameLikeAndLimit(null, 1, 10);
        System.out.println(list);

    }

    @Test
    @Transactional
    public void testSave(){
        Item item = new Item();
        item.setName("xxxx");
        item.setPrice(new BigDecimal(1111));
        item.setProductionDate(new Date());
        item.setDescription("xxxxxxxxxxxxx");
        item.setPic("zzzzzzzzzzzzzzzzzz");

        Integer count = itemService.save(item);
        Assert.assertEquals(new Integer(1),count);

    }

    @Test
    @Transactional
    public void del(){
        Integer count = itemService.del(15L);

        Assert.assertEquals(new Integer(1), count);
    }

    @Test
    public void findById(){
        Item item = itemService.findById(17);
        System.out.println(item);
    }
}