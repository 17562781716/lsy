package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.mapper.ThemeCategoryMapper;
import com.yupi.yuoj.model.dto.theme.ThemeCategoryQueryRequest;
import com.yupi.yuoj.model.entity.ThemeCategory;
import com.yupi.yuoj.service.ThemeCategoryService;
import com.yupi.yuoj.model.vo.ThemeCategoryVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeCategoryServiceImpl extends ServiceImpl<ThemeCategoryMapper, ThemeCategory> implements ThemeCategoryService {

    @Override
    public QueryWrapper<ThemeCategory> getQueryWrapper(ThemeCategoryQueryRequest request) {
        if (request == null) {
            return new QueryWrapper<>();
        }
        QueryWrapper<ThemeCategory> wrapper = new QueryWrapper<>();
        wrapper.eq(request.getId() != null, "id", request.getId());
        wrapper.like(StringUtils.isNotBlank(request.getCategoryName()), "categoryName", request.getCategoryName());
        wrapper.eq("isDelete", 0);
        return wrapper;
    }

    @Override
    public ThemeCategoryVO getThemeCategoryVO(ThemeCategory category) {
        if (category == null) {
            return null;
        }
        ThemeCategoryVO vo = new ThemeCategoryVO();
        BeanUtils.copyProperties(category, vo);
        return vo;
    }

    @Override
    public List<ThemeCategoryVO> getThemeCategoryVO(List<ThemeCategory> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(this::getThemeCategoryVO).collect(Collectors.toList());
    }
}