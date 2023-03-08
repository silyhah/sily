package com.sily.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sily.reggie.common.CustomException;
import com.sily.reggie.entity.Category;
import com.sily.reggie.entity.Dish;
import com.sily.reggie.entity.Setmeal;
import com.sily.reggie.mapper.CategoryMapper;
import com.sily.reggie.service.CategoryService;
import com.sily.reggie.service.DishService;
import com.sily.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;

    @Override
    public void remove(Long id) {
        //检查是否关联菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        Long count1 = dishService.count(dishLambdaQueryWrapper);

        if(count1 > 0){
            throw new CustomException("当前分类关联了菜品信息，不能删除");
        }

        //是否关联了套餐
        LambdaQueryWrapper<Setmeal> setMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setMealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        Long count2 = setMealService.count(setMealLambdaQueryWrapper);

        if(count2 > 0){
            throw new CustomException("当前分类关联了套餐信息，不能删除");
        }

        super.removeById(id);
    }
}
