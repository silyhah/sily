package com.sily.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sily.reggie.common.R;
import com.sily.reggie.entity.Category;
import com.sily.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询出全部分类
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> getAll(Integer page, Integer pageSize){
        log.info("page = {},pageSize = {}", page, pageSize);

        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     *
     * @param category
     * @return
     */
    @PostMapping()
    public R<String> add(@RequestBody Category category){
        log.info("category{}",category);
        categoryService.save(category);
        return R.success("添加成功");
    }

    /**
     * 根据id修改信息
     * @param category
     * @return
     */
    @PutMapping()
    public R<String> update(@RequestBody Category category){
        log.info("修改套餐的信息{}",category);
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     *
     * @param ids
     * @return
     */
    @DeleteMapping()
    public R<String> deleteById(Long ids){
        log.info("删除id为{}的菜品",ids);
        categoryService.remove(ids);
        return R.success("分类信息删除成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
