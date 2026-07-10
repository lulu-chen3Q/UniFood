package com.unifood.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.unifood.constant.MessageConstant;
import com.unifood.constant.StatusConstant;
import com.unifood.context.BaseContext;
import com.unifood.dto.CategoryDTO;
import com.unifood.dto.CategoryPageQueryDTO;
import com.unifood.entity.Category;
import com.unifood.exception.DeletionNotAllowedException;
import com.unifood.mapper.CategoryMapper;
import com.unifood.mapper.DishMapper;
import com.unifood.mapper.SetmealMapper;
import com.unifood.result.PageResult;
import com.unifood.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @Override
    public void newAddCategory(CategoryDTO categoryDTO) {
        //insert into new(new)
        //value (),(),();
        //属性拷贝
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //分类状态默认为禁用状态0
        category.setStatus(StatusConstant.DISABLE);
        //没有的参数 添加
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        //动态
        categoryMapper.insert(category);

    }
    /**
     * 分类分页查询
     *
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        //泛型需要菜品类 得注入
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    //todo 删除分类前的约束
    @Override
    public void deleteById(Long id) {
        //删除前要看删除分类下有无产品关联
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count = dishMapper.countByCategoryId(id);
        if (count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        //查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count =setmealMapper.countByCategoryId(id);
        if (count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);
    }
    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        //xml用的Category 所以得属性拷贝
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.update(category);
    }
}
