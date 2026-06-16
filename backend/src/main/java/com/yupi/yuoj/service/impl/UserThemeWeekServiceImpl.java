package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.mapper.UserThemeWeekMapper;
import com.yupi.yuoj.model.dto.theme.UserThemeWeekQueryRequest;
import com.yupi.yuoj.model.entity.UserThemeWeek;
import com.yupi.yuoj.service.UserThemeWeekService;
import com.yupi.yuoj.model.vo.UserThemeWeekVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserThemeWeekServiceImpl extends ServiceImpl<UserThemeWeekMapper, UserThemeWeek> implements UserThemeWeekService {

    @Override
    public QueryWrapper<UserThemeWeek> getQueryWrapper(UserThemeWeekQueryRequest request) {
        if (request == null) {
            return new QueryWrapper<>();
        }
        QueryWrapper<UserThemeWeek> wrapper = new QueryWrapper<>();
        wrapper.eq(request.getId() != null, "id", request.getId());
        wrapper.eq(request.getUserId() != null, "userId", request.getUserId());
        wrapper.eq(request.getTemplateId() != null, "templateId", request.getTemplateId());
        wrapper.like(StringUtils.isNotBlank(request.getWeekName()), "weekName", request.getWeekName());
        wrapper.eq(StringUtils.isNotBlank(request.getWeekStatus()), "weekStatus", request.getWeekStatus());
        wrapper.eq("isDelete", 0);
        return wrapper;
    }

    @Override
    public UserThemeWeekVO getUserThemeWeekVO(UserThemeWeek week) {
        if (week == null) {
            return null;
        }
        UserThemeWeekVO vo = new UserThemeWeekVO();
        BeanUtils.copyProperties(week, vo);
        return vo;
    }

    @Override
    public List<UserThemeWeekVO> getUserThemeWeekVO(List<UserThemeWeek> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(this::getUserThemeWeekVO).collect(Collectors.toList());
    }
}