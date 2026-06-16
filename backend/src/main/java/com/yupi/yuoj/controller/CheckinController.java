package com.yupi.yuoj.controller;

import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.model.dto.checkin.CheckinAddRequest;
import com.yupi.yuoj.model.dto.checkin.CheckinQueryRequest;
import com.yupi.yuoj.model.dto.checkin.CheckinStatisticsResponse;
import com.yupi.yuoj.model.entity.CheckinRecord;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.entity.UserThemeWeek;
import com.yupi.yuoj.service.CheckinRecordService;
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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/checkin")
@Slf4j
@Api(tags = "🎯 打卡记录管理")
public class CheckinController {

    @Resource
    private CheckinRecordService checkinRecordService;

    @Resource
    private UserService userService;

    @Resource
    private UserThemeWeekService userThemeWeekService;

    /**
     * 获取指定主题周的所有打卡记录
     *
     * @param weekId 主题周ID
     * @return 打卡记录列表（按打卡日期降序）
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取打卡记录列表", notes = "根据主题周ID获取该周下的所有打卡记录，按打卡日期降序排列")
    public BaseResponse<List<CheckinRecord>> listCheckins(@ApiParam("主题周ID") long weekId) {
        if (weekId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<CheckinRecord> list = checkinRecordService.lambdaQuery()
                .eq(CheckinRecord::getWeekId, weekId)
                .orderByDesc(CheckinRecord::getCheckinDate)
                .list();
        return ResultUtils.success(list);
    }

    /**
     * 用户每日打卡
     *
     * @param request 打卡信息（主题周ID、第几天、打卡内容、打卡时长）
     * @param httpRequest HTTP请求对象
     * @return 新打卡记录ID
     */
    @PostMapping("/add")
    @ApiOperation(value = "每日打卡", notes = "当前登录用户进行每日打卡，自动记录打卡日期")
    public BaseResponse<Long> addCheckin(@RequestBody CheckinAddRequest request, HttpServletRequest httpRequest) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(httpRequest);
        CheckinRecord record = new CheckinRecord();
        BeanUtils.copyProperties(request, record);
        record.setUserId(loginUser.getId());
        if (request.getCheckinDate() == null) {
            record.setCheckinDate(LocalDate.now());
        }
        boolean result = checkinRecordService.save(record);
        return ResultUtils.success(record.getId());
    }

    /**
     * 删除打卡记录
     *
     * @param request 删除请求（包含打卡记录ID）
     * @return 是否成功
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除打卡记录", notes = "删除指定用户的打卡记录")
    public BaseResponse<Boolean> deleteCheckin(@RequestBody DeleteRequest request) {
        if (request == null || request.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = checkinRecordService.removeById(request.getId());
        return ResultUtils.success(result);
    }

    /**
     * 获取打卡统计数据
     *
     * @param weekId 主题周ID
     * @return 打卡统计信息（总天数、已打卡天数、完成率、连续打卡天数）
     */
    @GetMapping("/statistics")
    @ApiOperation(value = "获取打卡统计", notes = "获取指定主题周的打卡统计数据，包括总天数、已打卡天数、完成率和连续打卡天数")
    public BaseResponse<CheckinStatisticsResponse> getStatistics(@ApiParam("主题周ID") long weekId) {
        if (weekId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserThemeWeek week = userThemeWeekService.getById(weekId);
        if (week == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        List<CheckinRecord> records = checkinRecordService.lambdaQuery()
                .eq(CheckinRecord::getWeekId, weekId)
                .list();

        CheckinStatisticsResponse stats = new CheckinStatisticsResponse();
        if (week.getStartDate() != null && week.getEndDate() != null) {
            stats.setTotalDays((int) java.time.temporal.ChronoUnit.DAYS.between(week.getStartDate(), week.getEndDate()) + 1);
        } else {
            stats.setTotalDays(7);
        }
        stats.setCheckinDays(records.size());
        stats.setCompletionRate(stats.getTotalDays() > 0 ? (double) stats.getCheckinDays() / stats.getTotalDays() : 0.0);
        stats.setContinuousDays(calculateContinuousDays(records));

        return ResultUtils.success(stats);
    }

    /**
     * 条件查询打卡记录
     *
     * @param request 查询条件（记录ID、主题周ID、用户ID）
     * @return 符合条件的打卡记录列表
     */
    @PostMapping("/list/condition")
    @ApiOperation(value = "条件查询打卡记录", notes = "根据条件查询打卡记录列表")
    public BaseResponse<List<CheckinRecord>> listCheckinByCondition(@RequestBody CheckinQueryRequest request) {
        List<CheckinRecord> list = checkinRecordService.list(checkinRecordService.getQueryWrapper(request));
        return ResultUtils.success(list);
    }

    /**
     * 计算连续打卡天数
     */
    private int calculateContinuousDays(List<CheckinRecord> records) {
        if (records.isEmpty()) return 0;
        records.sort((a, b) -> b.getCheckinDate().compareTo(a.getCheckinDate()));
        int continuous = 1;
        LocalDate lastDate = records.get(0).getCheckinDate();
        for (int i = 1; i < records.size(); i++) {
            if (lastDate.minusDays(1).equals(records.get(i).getCheckinDate())) {
                continuous++;
                lastDate = records.get(i).getCheckinDate();
            } else {
                break;
            }
        }
        return continuous;
    }
}
