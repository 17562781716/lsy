package com.yupi.yuoj.controller;

import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.model.dto.theme.WeekTaskAddRequest;
import com.yupi.yuoj.model.dto.theme.WeekTaskQueryRequest;
import com.yupi.yuoj.model.dto.theme.WeekTaskUpdateRequest;
import com.yupi.yuoj.model.entity.WeekTask;
import com.yupi.yuoj.service.WeekTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/week/task")
@Slf4j
@Api(tags = "✅ 周任务管理")
public class WeekTaskController {

    @Resource
    private WeekTaskService weekTaskService;

    /**
     * 获取指定主题周的所有任务
     *
     * @param weekId 主题周ID
     * @return 任务列表（按排序字段升序）
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取指定主题周的所有任务", notes = "根据主题周ID获取该周下的所有任务列表，按排序字段升序排列")
    public BaseResponse<List<WeekTask>> listWeekTasks(@ApiParam("主题周ID") long weekId) {
        if (weekId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<WeekTask> list = weekTaskService.lambdaQuery()
                .eq(WeekTask::getWeekId, weekId)
                .orderByAsc(WeekTask::getTaskOrder)
                .list();
        return ResultUtils.success(list);
    }

    /**
     * 创建周任务
     *
     * @param request 任务信息（所属主题周ID、任务名称、排序序号）
     * @return 新任务ID
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建周任务", notes = "为指定主题周创建一个新的任务")
    public BaseResponse<Long> addWeekTask(@RequestBody WeekTaskAddRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        WeekTask task = new WeekTask();
        BeanUtils.copyProperties(request, task);
        boolean result = weekTaskService.save(task);
        return ResultUtils.success(task.getId());
    }

    /**
     * 更新周任务
     *
     * @param request 更新后的任务信息
     * @return 是否成功
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新周任务", notes = "更新已有任务的信息（名称、排序、完成状态）")
    public BaseResponse<Boolean> updateWeekTask(@RequestBody WeekTaskUpdateRequest request) {
        if (request == null || request.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        WeekTask task = new WeekTask();
        BeanUtils.copyProperties(request, task);
        boolean result = weekTaskService.updateById(task);
        return ResultUtils.success(result);
    }

    /**
     * 删除周任务
     *
     * @param request 删除请求（包含任务ID）
     * @return 是否成功
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除周任务", notes = "删除指定的任务")
    public BaseResponse<Boolean> deleteWeekTask(@RequestBody DeleteRequest request) {
        if (request == null || request.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = weekTaskService.removeById(request.getId());
        return ResultUtils.success(result);
    }

    /**
     * 条件查询任务列表
     *
     * @param request 查询条件（任务ID、所属主题周ID、任务名称、完成状态）
     * @return 符合条件的任务列表
     */
    @PostMapping("/list/condition")
    @ApiOperation(value = "条件查询任务列表", notes = "根据条件查询周任务列表")
    public BaseResponse<List<WeekTask>> listTaskByCondition(@RequestBody WeekTaskQueryRequest request) {
        List<WeekTask> list = weekTaskService.list(weekTaskService.getQueryWrapper(request));
        return ResultUtils.success(list);
    }
}
