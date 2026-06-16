package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.dto.theme.WeekTaskQueryRequest;
import com.yupi.yuoj.model.entity.WeekTask;
import com.yupi.yuoj.model.vo.WeekTaskVO;

import java.util.List;

public interface WeekTaskService extends IService<WeekTask> {

    QueryWrapper<WeekTask> getQueryWrapper(WeekTaskQueryRequest request);

    WeekTaskVO getWeekTaskVO(WeekTask task);

    List<WeekTaskVO> getWeekTaskVO(List<WeekTask> list);
}