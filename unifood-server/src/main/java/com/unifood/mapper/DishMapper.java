package com.unifood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    /**
     * 删除分类
     *
     * @param
     * @param categoryId
     * @return
     */
    @Select("select count(dish.id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);
}
