package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.dto.theme.ThemeCategoryQueryRequest;
import com.yupi.yuoj.model.entity.ThemeCategory;
import com.yupi.yuoj.model.vo.ThemeCategoryVO;

import java.util.List;

public interface ThemeCategoryService extends IService<ThemeCategory> {

    QueryWrapper<ThemeCategory> getQueryWrapper(ThemeCategoryQueryRequest request);

    ThemeCategoryVO getThemeCategoryVO(ThemeCategory category);

    List<ThemeCategoryVO> getThemeCategoryVO(List<ThemeCategory> list);
}