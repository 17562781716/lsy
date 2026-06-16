package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.dto.browse.BrowseHistoryQueryRequest;
import com.yupi.yuoj.model.entity.BrowseHistory;
import com.yupi.yuoj.model.vo.BrowseHistoryVO;

import java.util.List;

public interface BrowseHistoryService extends IService<BrowseHistory> {

    QueryWrapper<BrowseHistory> getQueryWrapper(BrowseHistoryQueryRequest request);

    BrowseHistoryVO getBrowseHistoryVO(BrowseHistory browseHistory);

    List<BrowseHistoryVO> getBrowseHistoryVO(List<BrowseHistory> list);
}
