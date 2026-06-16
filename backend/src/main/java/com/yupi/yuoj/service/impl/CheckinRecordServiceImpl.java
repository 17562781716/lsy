package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.mapper.CheckinRecordMapper;
import com.yupi.yuoj.model.dto.checkin.CheckinQueryRequest;
import com.yupi.yuoj.model.entity.CheckinRecord;
import com.yupi.yuoj.service.CheckinRecordService;
import com.yupi.yuoj.model.vo.CheckinRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckinRecordServiceImpl extends ServiceImpl<CheckinRecordMapper, CheckinRecord> implements CheckinRecordService {

    @Override
    public QueryWrapper<CheckinRecord> getQueryWrapper(CheckinQueryRequest request) {
        if (request == null) {
            return new QueryWrapper<>();
        }
        QueryWrapper<CheckinRecord> wrapper = new QueryWrapper<>();
        wrapper.eq(request.getId() != null, "id", request.getId());
        wrapper.eq(request.getWeekId() != null, "weekId", request.getWeekId());
        wrapper.eq(request.getUserId() != null, "userId", request.getUserId());
        return wrapper;
    }

    @Override
    public CheckinRecordVO getCheckinRecordVO(CheckinRecord record) {
        if (record == null) {
            return null;
        }
        CheckinRecordVO vo = new CheckinRecordVO();
        BeanUtils.copyProperties(record, vo);
        return vo;
    }

    @Override
    public List<CheckinRecordVO> getCheckinRecordVO(List<CheckinRecord> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(this::getCheckinRecordVO).collect(Collectors.toList());
    }
}