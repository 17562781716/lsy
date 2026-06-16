package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.mapper.WeekTaskMapper;
import com.yupi.yuoj.model.dto.theme.WeekTaskQueryRequest;
import com.yupi.yuoj.model.entity.WeekTask;
import com.yupi.yuoj.service.WeekTaskService;
import com.yupi.yuoj.model.vo.WeekTaskVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeekTaskServiceImpl extends ServiceImpl<WeekTaskMapper, WeekTask> implements WeekTaskService {

    @Override
    public QueryWrapper<WeekTask> getQueryWrapper(WeekTaskQueryRequest request) {
        if (request == null) {
            return new QueryWrapper<>();
        }
        QueryWrapper<WeekTask> wrapper = new QueryWrapper<>();
        wrapper.eq(request.getId() != null, "id", request.getId());
        wrapper.eq(request.getWeekId() != null, "weekId", request.getWeekId());
        wrapper.like(StringUtils.isNotBlank(request.getTaskName()), "taskName", request.getTaskName());
        wrapper.eq(request.getIsCompleted() != null, "isCompleted", request.getIsCompleted());
        return wrapper;
    }

    @Override
    public WeekTaskVO getWeekTaskVO(WeekTask task) {
        if (task == null) {
            return null;
        }
        WeekTaskVO vo = new WeekTaskVO();
        BeanUtils.copyProperties(task, vo);
        return vo;
    }

    @Override
    public List<WeekTaskVO> getWeekTaskVO(List<WeekTask> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(this::getWeekTaskVO).collect(Collectors.toList());
    }
}