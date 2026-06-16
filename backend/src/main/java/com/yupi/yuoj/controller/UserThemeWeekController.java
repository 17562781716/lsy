package com.yupi.yuoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.exception.ThrowUtils;
import com.yupi.yuoj.model.dto.theme.UserThemeWeekAddRequest;
import com.yupi.yuoj.model.dto.theme.UserThemeWeekQueryRequest;
import com.yupi.yuoj.model.dto.theme.UserThemeWeekUpdateRequest;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.entity.UserThemeWeek;
import com.yupi.yuoj.service.UserService;
import com.yupi.yuoj.service.UserThemeWeekService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user/theme/week")
@Slf4j
@Api(tags = "📅 用户主题周管理")
public class UserThemeWeekController {

    @Resource
    private UserThemeWeekService userThemeWeekService;

    @Resource
    private UserService userService;

    /**
     * 获取我的所有主题周
     *
     * @param request HTTP请求对象
     * @return 当前用户的所有主题周列表
     */
    @GetMapping("/my")
    @ApiOperation(value = "获取我的主题周", notes = "获取当前登录用户的所有主题周列表")
    public BaseResponse<List<UserThemeWeek>> getMyThemeWeeks(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<UserThemeWeek> list = userThemeWeekService.lambdaQuery()
                .eq(UserThemeWeek::getUserId, loginUser.getId())
                .list();
        return ResultUtils.success(list);
    }

    /**
     * 根据ID获取主题周详情
     *
     * @param id 主题周ID
     * @return 主题周详细信息
     */
    @GetMapping("/get")
    @ApiOperation(value = "根据ID获取主题周详情", notes = "通过主题周ID获取单个主题周的详细信息")
    public BaseResponse<UserThemeWeek> getThemeWeek(@ApiParam("主题周ID") long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserThemeWeek week = userThemeWeekService.getById(id);
        ThrowUtils.throwIf(week == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(week);
    }

    /**
     * 创建用户主题周
     *
     * @param request 主题周信息（模板ID、名称、图标、描述、难度、开始/结束日期）
     * @param httpRequest HTTP请求对象
     * @return 新主题周ID
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建用户主题周", notes = "为当前登录用户创建一个新的主题周")
    public BaseResponse<Long> addThemeWeek(@RequestBody UserThemeWeekAddRequest request, HttpServletRequest httpRequest) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(httpRequest);
        UserThemeWeek week = new UserThemeWeek();
        BeanUtils.copyProperties(request, week);
        week.setUserId(loginUser.getId());
        boolean result = userThemeWeekService.save(week);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(week.getId());
    }

    /**
     * 更新用户主题周
     *
     * @param request 更新后的主题周信息
     * @return 是否成功
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新用户主题周", notes = "更新已有主题周的信息（名称、描述、难度、状态、进度）")
    public BaseResponse<Boolean> updateThemeWeek(@RequestBody UserThemeWeekUpdateRequest request) {
        if (request == null || request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserThemeWeek week = new UserThemeWeek();
        BeanUtils.copyProperties(request, week);
        boolean result = userThemeWeekService.updateById(week);
        return ResultUtils.success(result);
    }

    /**
     * 删除用户主题周
     *
     * @param request 删除请求（包含主题周ID）
     * @return 是否成功
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除用户主题周", notes = "删除指定的主题周")
    public BaseResponse<Boolean> deleteThemeWeek(@RequestBody DeleteRequest request) {
        if (request == null || request.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userThemeWeekService.removeById(request.getId());
        return ResultUtils.success(result);
    }

    /**
     * 分页获取主题周列表
     *
     * @param page 分页参数（页码、每页数量）
     * @return 主题周分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "分页获取主题周列表", notes = "分页查询所有用户的主题周列表（管理员）")
    public BaseResponse<Page<UserThemeWeek>> listThemeWeekByPage(@RequestBody Page<UserThemeWeek> page) {
        Page<UserThemeWeek> result = userThemeWeekService.page(page);
        return ResultUtils.success(result);
    }

    /**
     * 条件查询主题周列表
     *
     * @param request 查询条件（主题周ID、用户ID、模板ID、周名称、状态）
     * @return 符合条件的主题周列表
     */
    @PostMapping("/list/condition")
    @ApiOperation(value = "条件查询主题周列表", notes = "根据条件查询主题周列表")
    public BaseResponse<List<UserThemeWeek>> listThemeWeekByCondition(@RequestBody UserThemeWeekQueryRequest request) {
        List<UserThemeWeek> list = userThemeWeekService.list(userThemeWeekService.getQueryWrapper(request));
        return ResultUtils.success(list);
    }
}
