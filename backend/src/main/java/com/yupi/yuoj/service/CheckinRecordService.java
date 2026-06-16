package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.dto.checkin.CheckinQueryRequest;
import com.yupi.yuoj.model.entity.CheckinRecord;
import com.yupi.yuoj.model.vo.CheckinRecordVO;

import java.util.List;

public interface CheckinRecordService extends IService<CheckinRecord> {

    QueryWrapper<CheckinRecord> getQueryWrapper(CheckinQueryRequest request);

    CheckinRecordVO getCheckinRecordVO(CheckinRecord record);

    List<CheckinRecordVO> getCheckinRecordVO(List<CheckinRecord> list);
}