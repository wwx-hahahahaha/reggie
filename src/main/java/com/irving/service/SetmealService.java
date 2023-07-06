package com.irving.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.irving.domain.Setmeal;
import com.irving.dto.SetmealDto;

public interface SetmealService extends IService<Setmeal> {
    Page<SetmealDto> getPage(Integer page, Integer pageSize, String name);

    boolean mySave(SetmealDto dto);

    SetmealDto get(long id);

    boolean MyUpdate(SetmealDto dto);
}
