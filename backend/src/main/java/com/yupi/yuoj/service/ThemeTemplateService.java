package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.dto.theme.ThemeTemplateQueryRequest;
import com.yupi.yuoj.model.entity.ThemeTemplate;
import com.yupi.yuoj.model.vo.ThemeTemplateVO;

import java.util.List;

public interface ThemeTemplateService extends IService<ThemeTemplate> {

    QueryWrapper<ThemeTemplate> getQueryWrapper(ThemeTemplateQueryRequest request);

    ThemeTemplateVO getThemeTemplateVO(ThemeTemplate template);

    List<ThemeTemplateVO> getThemeTemplateVO(List<ThemeTemplate> list);
}