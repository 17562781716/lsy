package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.dto.favorite.UserFavoriteQueryRequest;
import com.yupi.yuoj.model.entity.UserFavorite;
import com.yupi.yuoj.model.vo.UserFavoriteVO;

import java.util.List;

public interface UserFavoriteService extends IService<UserFavorite> {

    QueryWrapper<UserFavorite> getQueryWrapper(UserFavoriteQueryRequest request);

    UserFavoriteVO getUserFavoriteVO(UserFavorite userFavorite);

    List<UserFavoriteVO> getUserFavoriteVO(List<UserFavorite> list);
}
