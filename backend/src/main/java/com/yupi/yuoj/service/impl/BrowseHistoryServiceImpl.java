package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.mapper.BrowseHistoryMapper;
import com.yupi.yuoj.model.dto.browse.BrowseHistoryQueryRequest;
import com.yupi.yuoj.model.entity.BrowseHistory;
import com.yupi.yuoj.service.BrowseHistoryService;
import com.yupi.yuoj.model.vo.BrowseHistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrowseHistoryServiceImpl extends ServiceImpl<BrowseHistoryMapper, BrowseHistory> implements BrowseHistoryService {

    @Override
    public QueryWrapper<BrowseHistory> getQueryWrapper(BrowseHistoryQueryRequest request) {
        if (request == null) {
            return new QueryWrapper<>();
        }
        QueryWrapper<BrowseHistory> wrapper = new QueryWrapper<>();
        wrapper.eq(request.getId() != null, "id", request.getId());
        wrapper.eq(request.getUserId() != null, "user_id", request.getUserId());
        wrapper.eq(request.getTemplateId() != null, "template_id", request.getTemplateId());
        return wrapper;
    }

    @Override
    public BrowseHistoryVO getBrowseHistoryVO(BrowseHistory browseHistory) {
        if (browseHistory == null) {
            return null;
        }
        BrowseHistoryVO vo = new BrowseHistoryVO();
        BeanUtils.copyProperties(browseHistory, vo);
        return vo;
    }

    @Override
    public List<BrowseHistoryVO> getBrowseHistoryVO(List<BrowseHistory> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(this::getBrowseHistoryVO).collect(Collectors.toList());
    }
}
