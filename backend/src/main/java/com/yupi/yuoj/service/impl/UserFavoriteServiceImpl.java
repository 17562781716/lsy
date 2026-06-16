package com.yupi.yuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuoj.mapper.UserFavoriteMapper;
import com.yupi.yuoj.model.dto.favorite.UserFavoriteQueryRequest;
import com.yupi.yuoj.model.entity.UserFavorite;
import com.yupi.yuoj.service.UserFavoriteService;
import com.yupi.yuoj.model.vo.UserFavoriteVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFavoriteServiceImpl extends ServiceImpl<UserFavoriteMapper, UserFavorite> implements UserFavoriteService {

    @Override
    public QueryWrapper<UserFavorite> getQueryWrapper(UserFavoriteQueryRequest request) {
        if (request == null) {
            return new QueryWrapper<>();
        }
        QueryWrapper<UserFavorite> wrapper = new QueryWrapper<>();
        wrapper.eq(request.getId() != null, "id", request.getId());
        wrapper.eq(request.getUserId() != null, "user_id", request.getUserId());
        wrapper.eq(request.getTemplateId() != null, "template_id", request.getTemplateId());
        return wrapper;
    }

    @Override
    public UserFavoriteVO getUserFavoriteVO(UserFavorite userFavorite) {
        if (userFavorite == null) {
            return null;
        }
        UserFavoriteVO vo = new UserFavoriteVO();
        BeanUtils.copyProperties(userFavorite, vo);
        return vo;
    }

    @Override
    public List<UserFavoriteVO> getUserFavoriteVO(List<UserFavorite> list) {
        if (list == null) {
            return null;
        }
        return list.stream().map(this::getUserFavoriteVO).collect(Collectors.toList());
    }
}
