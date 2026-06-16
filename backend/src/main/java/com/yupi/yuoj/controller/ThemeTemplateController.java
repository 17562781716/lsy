package com.yupi.yuoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.exception.ThrowUtils;
import com.yupi.yuoj.model.dto.theme.ThemeTemplateAddRequest;
import com.yupi.yuoj.model.dto.theme.ThemeTemplateQueryRequest;
import com.yupi.yuoj.model.dto.theme.ThemeTemplateUpdateRequest;
import com.yupi.yuoj.model.entity.ThemeTemplate;
import com.yupi.yuoj.service.ThemeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/theme/template")
@Slf4j
@Api(tags = "📋 主题模板管理")
public class ThemeTemplateController {

    @Resource
    private ThemeTemplateService themeTemplateService;

    /**
     * 获取全部模板列表
     *
     * @return 主题模板列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取全部模板列表", notes = "获取所有主题模板，用于前端展示")
    public BaseResponse<List<ThemeTemplate>> listThemeTemplate() {
        List<ThemeTemplate> list = themeTemplateService.list();
        return ResultUtils.success(list);
    }

    /**
     * 根据ID获取模板详情
     *
     * @param id 模板ID
     * @return 模板详细信息
     */
    @GetMapping("/get")
    @ApiOperation(value = "根据ID获取模板详情", notes = "通过模板ID获取单个模板的详细信息")
    public BaseResponse<ThemeTemplate> getThemeTemplate(@ApiParam("模板ID") long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ThemeTemplate template = themeTemplateService.getById(id);
        ThrowUtils.throwIf(template == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(template);
    }

    /**
     * 创建主题模板
     *
     * @param request 模板信息（分类ID、名称、图标、描述、标签、创意想法、难度）
     * @return 新模板ID
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建主题模板", notes = "创建一个新的主题模板")
    public BaseResponse<Long> addThemeTemplate(@RequestBody ThemeTemplateAddRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ThemeTemplate template = new ThemeTemplate();
        BeanUtils.copyProperties(request, template);
        boolean result = themeTemplateService.save(template);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(template.getId());
    }

    /**
     * 更新主题模板
     *
     * @param request 更新后的模板信息
     * @return 是否成功
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新主题模板", notes = "更新已有主题模板的信息")
    public BaseResponse<Boolean> updateThemeTemplate(@RequestBody ThemeTemplateUpdateRequest request) {
        if (request == null || request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ThemeTemplate template = new ThemeTemplate();
        BeanUtils.copyProperties(request, template);
        boolean result = themeTemplateService.updateById(template);
        return ResultUtils.success(result);
    }

    /**
     * 删除主题模板
     *
     * @param request 删除请求（包含模板ID）
     * @return 是否成功
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除主题模板", notes = "删除指定的主题模板")
    public BaseResponse<Boolean> deleteThemeTemplate(@RequestBody DeleteRequest request) {
        if (request == null || request.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = themeTemplateService.removeById(request.getId());
        return ResultUtils.success(result);
    }

    /**
     * 分页获取模板列表
     *
     * @param page 分页参数（页码、每页数量）
     * @return 模板分页列表
     */
    @PostMapping("/list/page")
    @ApiOperation(value = "分页获取模板列表", notes = "分页查询主题模板列表")
    public BaseResponse<Page<ThemeTemplate>> listThemeTemplateByPage(@RequestBody Page<ThemeTemplate> page) {
        Page<ThemeTemplate> result = themeTemplateService.page(page);
        return ResultUtils.success(result);
    }

    /**
     * 条件查询模板列表
     *
     * @param request 查询条件（模板ID、分类ID、模板名称、难度）
     * @return 符合条件的模板列表
     */
    @PostMapping("/list/condition")
    @ApiOperation(value = "条件查询模板列表", notes = "根据条件查询主题模板列表")
    public BaseResponse<List<ThemeTemplate>> listThemeTemplateByCondition(@RequestBody ThemeTemplateQueryRequest request) {
        List<ThemeTemplate> list = themeTemplateService.list(themeTemplateService.getQueryWrapper(request));
        return ResultUtils.success(list);
    }
}
