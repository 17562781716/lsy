package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.mapper.UserMessageMapper;
import com.yupi.yuoj.model.dto.message.UserMessageQueryRequest;
import com.yupi.yuoj.model.entity.UserMessage;
import com.yupi.yuoj.service.UserMessageService;
import com.yupi.yuoj.model.vo.UserMessageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {

    @Override
    public QueryWrapper<UserMessage> getQueryWrapper(UserMessageQueryRequest request) {
        if (request == null) {
            return new QueryWrapper<>();
        }
        QueryWrapper<UserMessage> wrapper = new QueryWrapper<>();
        wrapper.eq(request.getId() != null, "id", request.getId());
        wrapper.eq(request.getUserId() != null, "user_id", request.getUserId());
        wrapper.eq(StringUtils.isNotBlank(request.getMessageType()), "message_type", request.getMessageType());
        wrapper.eq(request.getIsRead() != null, "is_read", request.getIsRead());
        return wrapper;
    }

    @Override
    public UserMessageVO getUserMessageVO(UserMessage userMessage) {
        if (userMessage == null) {
            return null;
        }
        UserMessageVO vo = new UserMessageVO();
        BeanUtils.copyProperties(userMessage, vo);
        return vo;
    }

    @Override
    public List<UserMessageVO> getUserMessageVO(List<UserMessage> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(this::getUserMessageVO).collect(Collectors.toList());
    }
}
