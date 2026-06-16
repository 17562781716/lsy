package com.yupi.yuoj.controller;

import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.model.dto.favorite.UserFavoriteQueryRequest;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.entity.UserFavorite;
import com.yupi.yuoj.service.UserFavoriteService;
import com.yupi.yuoj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user/favorite")
@Slf4j
@Api(tags = "⭐ 用户收藏管理")
public class UserFavoriteController {

    @Resource
    private UserFavoriteService userFavoriteService;

    @Resource
    private UserService userService;

    /**
     * 获取我的收藏列表
     *
     * @param request HTTP请求对象
     * @return 当前登录用户的收藏列表（按收藏时间降序）
     */
    @GetMapping("/my")
    @ApiOperation(value = "获取我的收藏列表", notes = "获取当前登录用户的所有收藏记录，按收藏时间降序排列")
    public BaseResponse<List<UserFavorite>> getMyFavorites(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<UserFavorite> list = userFavoriteService.lambdaQuery()
                .eq(UserFavorite::getUserId, loginUser.getId())
                .orderByDesc(UserFavorite::getCreateTime)
                .list();
        return ResultUtils.success(list);
    }

    /**
     * 添加收藏
     *
     * @param templateId 模板ID
     * @param request HTTP请求对象
     * @return 新收藏记录ID
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加收藏", notes = "收藏某个主题模板")
    public BaseResponse<Long> addFavorite(
            @ApiParam("模板ID") @RequestParam Long templateId,
            HttpServletRequest request) {
        if (templateId == null || templateId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        UserFavorite favorite = new UserFavorite();
        favorite.setUserId(loginUser.getId());
        favorite.setTemplateId(templateId);
        boolean result = userFavoriteService.save(favorite);
        return ResultUtils.success(favorite.getId());
    }

    /**
     * 删除收藏
     *
     * @param request 删除请求（包含收藏记录ID）
     * @return 是否成功
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除收藏", notes = "取消收藏指定的记录")
    public BaseResponse<Boolean> deleteFavorite(@RequestBody DeleteRequest request) {
        if (request == null || request.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userFavoriteService.removeById(request.getId());
        return ResultUtils.success(result);
    }

    /**
     * 条件查询收藏列表
     *
     * @param request 查询条件（记录ID、用户ID、模板ID）
     * @return 符合条件的收藏列表
     */
    @PostMapping("/list/condition")
    @ApiOperation(value = "条件查询收藏列表", notes = "根据条件查询收藏列表")
    public BaseResponse<List<UserFavorite>> listFavoriteByCondition(@RequestBody UserFavoriteQueryRequest request) {
        List<UserFavorite> list = userFavoriteService.list(userFavoriteService.getQueryWrapper(request));
        return ResultUtils.success(list);
    }
}
