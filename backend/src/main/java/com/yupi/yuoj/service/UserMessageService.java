package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.dto.message.UserMessageQueryRequest;
import com.yupi.yuoj.model.entity.UserMessage;
import com.yupi.yuoj.model.vo.UserMessageVO;

import java.util.List;

public interface UserMessageService extends IService<UserMessage> {

    QueryWrapper<UserMessage> getQueryWrapper(UserMessageQueryRequest request);

    UserMessageVO getUserMessageVO(UserMessage userMessage);

    List<UserMessageVO> getUserMessageVO(List<UserMessage> list);
}
