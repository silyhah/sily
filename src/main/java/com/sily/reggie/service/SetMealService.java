package com.sily.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sily.reggie.dto.SetmealDto;
import com.sily.reggie.entity.Setmeal;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {

    /**
     * 新增套餐，保存关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，和关联
     * @param ids
     */
    public void deleteWithDish(List<Long> ids);
}
