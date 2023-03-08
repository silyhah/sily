package com.sily.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sily.reggie.dto.DishDto;
import com.sily.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    public void saveWithFlavour(DishDto dishDto);

    public DishDto getByIdWithFlavour(Long id);

    public void updateWithFlavour(DishDto dishDto);
}
