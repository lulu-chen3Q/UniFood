package com.unifood.service;

import com.unifood.dto.CategoryDTO;
import com.unifood.dto.CategoryPageQueryDTO;
import com.unifood.result.PageResult;

public interface CategoryService {
    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    void newAddCategory(CategoryDTO categoryDTO);
    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
    /**
     * 删除分类
     * @param id
     * @return
     */
    void deleteById(Long id);
    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    void update(CategoryDTO categoryDTO);
}
