package com.yupi.yuoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.exception.ThrowUtils;
import com.yupi.yuoj.model.dto.theme.ThemeCategoryAddRequest;
import com.yupi.yuoj.model.dto.theme.ThemeCategoryQueryRequest;
import com.yupi.yuoj.model.dto.theme.ThemeCategoryUpdateRequest;
import com.yupi.yuoj.model.entity.ThemeCategory;
import com.yupi.yuoj.service.ThemeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/theme/category")
@Slf4j
@Api(tags = "📁 主题分类管理")
public class ThemeCategoryController {

    @Resource
    private ThemeCategoryService themeCategoryService;

    /**
     * 获取全部分类列表
     *
     * @return 主题分类列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部分类列表", notes = "获取所有主题分类，用于前端展示")
    public BaseResponse<List<ThemeCategory>> listThemeCategory() {
        List<ThemeCategory> list = themeCategoryService.list();
        return ResultUtils.success(list);
    }

    /**
     * 根据ID获取分类详情
     *
     * @param id 分类ID
     * @return 分类详细信息
     */
    @GetMapping("/get")
    @ApiOperation(value = "根据ID获取分类详情", notes = "通过分类ID获取单个分类的详细信息")
    public BaseResponse<ThemeCategory> getThemeCategory(@ApiParam("分类ID") long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ThemeCategory category = themeCategoryService.getById(id);
        ThrowUtils.throwIf(category == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(category);
    }

    /**
     * 创建主题分类
     *
     * @param request 分类信息（名称、图标、描述、排序）
     * @return 新分类ID
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建主题分类", notes = "创建一个新的主题分类")
    public BaseResponse<Long> addThemeCategory(@RequestBody ThemeCategoryAddRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ThemeCategory category = new ThemeCategory();
        BeanUtils.copyProperties(request, category);
        category.setIsDelete(0);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        boolean result = themeCategoryService.save(category);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(category.getId());
    }

    /**
     * 更新主题分类
     *
     * @param request 更新后的分类信息
     * @return 是否成功
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新主题分类", notes = "更新已有主题分类的信息")
    public BaseResponse<Boolean> updateThemeCategory(@RequestBody ThemeCategoryUpdateRequest request) {
        if (request == null || request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ThemeCategory category = new ThemeCategory();
        BeanUtils.copyProperties(request, category);
        boolean result = themeCategoryService.updateById(category);
        return ResultUtils.success(result);
    }

    /**
     * 删除主题分类
     *
     * @param request 删除请求（包含分类ID）
     * @return 是否成功
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除主题分类", notes = "删除指定的主题分类")
    public BaseResponse<Boolean> deleteThemeCategory(@RequestBody DeleteRequest request) {
        if (request == null || request.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = themeCategoryService.removeById(request.getId());
        return ResultUtils.success(result);
    }

    /**
     * 分页获取分类列表
     *
     * @param page 分页参数（页码、每页数量）
     * @return 分类分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "分页获取分类列表", notes = "分页查询主题分类列表")
    public BaseResponse<Page<ThemeCategory>> listThemeCategoryByPage(@RequestBody Page<ThemeCategory> page) {
        Page<ThemeCategory> result = themeCategoryService.page(page);
        return ResultUtils.success(result);
    }

    /**
     * 条件查询分类列表
     *
     * @param request 查询条件（分类ID、分类名称）
     * @return 符合条件的分类列表
     */
    @PostMapping("/list/condition")
    @ApiOperation(value = "条件查询分类列表", notes = "根据条件查询主题分类列表")
    public BaseResponse<List<ThemeCategory>> listThemeCategoryByCondition(@RequestBody ThemeCategoryQueryRequest request) {
        List<ThemeCategory> list = themeCategoryService.list(themeCategoryService.getQueryWrapper(request));
        return ResultUtils.success(list);
    }
}
