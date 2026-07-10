package com.unifood.controller.admin;

import com.unifood.dto.CategoryDTO;
import com.unifood.dto.CategoryPageQueryDTO;
import com.unifood.entity.Category;
import com.unifood.result.PageResult;
import com.unifood.result.Result;
import com.unifood.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping("/admin/category")
    @ApiOperation("新增分类")
    public Result<String> newAddCategory(@RequestBody CategoryDTO categoryDTO){
    log.info("新增分类:{}",categoryDTO);
    categoryService.newAddCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @ApiOperation("分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO  categoryPageQueryDTO){
        log.info("分类分页查询：{}",categoryPageQueryDTO);
        PageResult pageResult =categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result<Category> deleteById(Long id){
        log.info("删除分类：{}",id);
        categoryService.deleteById(id);
    return Result.success();
    }
}
