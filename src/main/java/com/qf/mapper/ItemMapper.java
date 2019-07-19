package com.qf.mapper;

import com.qf.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * cwy 2019/7/16 11:37
 **/
public interface ItemMapper {

    //1查询商品的总条数
    Long findCountByName(@Param("name") String name);

    //2查询商品的具体信息
    List<Item> findItemByNameLikeAndLimit(@Param("name") String name,
                                          @Param("offset") Integer offset,
                                          @Param("size") Integer size);

    //3 添加商品
    Integer save(Item item);

    //4 删除商品
    Integer delById(@Param("id") Long id);

    Integer updateById(Item item);

    Item findById(Integer id);
}
