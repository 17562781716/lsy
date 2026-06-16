package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.mapper.ThemeTemplateMapper;
import com.yupi.yuoj.model.dto.theme.ThemeTemplateQueryRequest;
import com.yupi.yuoj.model.entity.ThemeTemplate;
import com.yupi.yuoj.service.ThemeTemplateService;
import com.yupi.yuoj.model.vo.ThemeTemplateVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeTemplateServiceImpl extends ServiceImpl<ThemeTemplateMapper, ThemeTemplate> implements ThemeTemplateService {

    @Override
    public QueryWrapper<ThemeTemplate> getQueryWrapper(ThemeTemplateQueryRequest request) {
        if (request == null) {
            return new QueryWrapper<>();
        }
        QueryWrapper<ThemeTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq(request.getId() != null, "id", request.getId());
        wrapper.eq(request.getCategoryId() != null, "categoryId", request.getCategoryId());
        wrapper.like(StringUtils.isNotBlank(request.getTemplateName()), "templateName", request.getTemplateName());
        wrapper.eq(StringUtils.isNotBlank(request.getDifficulty()), "difficulty", request.getDifficulty());
        wrapper.eq("isDelete", 0);
        return wrapper;
    }

    @Override
    public ThemeTemplateVO getThemeTemplateVO(ThemeTemplate template) {
        if (template == null) {
            return null;
        }
        ThemeTemplateVO vo = new ThemeTemplateVO();
        BeanUtils.copyProperties(template, vo);
        return vo;
    }

    @Override
    public List<ThemeTemplateVO> getThemeTemplateVO(List<ThemeTemplate> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(this::getThemeTemplateVO).collect(Collectors.toList());
    }
}