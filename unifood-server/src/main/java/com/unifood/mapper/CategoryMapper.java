package com.unifood.mapper;

import com.github.pagehelper.Page;
import com.unifood.dto.CategoryPageQueryDTO;
import com.unifood.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    /**
     * 新增分类
     * @param category
     * @return
     */
    @Insert("insert into category (type, name, sort, status, create_time, create_user, update_time, update_user)\n" +
            "values (#{type},#{name},#{sort},#{status},#{createTime},#{createUser},#{updateTime},#{updateUser})")
    void insert(Category category);
    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
    /**
     * 删除分类
     * @param id
     * @return
     */
    @Delete("delete from category where id=#{id};")
    void deleteById(Long id);
}
