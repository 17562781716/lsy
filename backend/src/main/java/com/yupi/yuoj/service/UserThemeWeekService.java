package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.dto.theme.UserThemeWeekQueryRequest;
import com.yupi.yuoj.model.entity.UserThemeWeek;
import com.yupi.yuoj.model.vo.UserThemeWeekVO;

import java.util.List;

public interface UserThemeWeekService extends IService<UserThemeWeek> {

    QueryWrapper<UserThemeWeek> getQueryWrapper(UserThemeWeekQueryRequest request);

    UserThemeWeekVO getUserThemeWeekVO(UserThemeWeek week);

    List<UserThemeWeekVO> getUserThemeWeekVO(List<UserThemeWeek> list);
}