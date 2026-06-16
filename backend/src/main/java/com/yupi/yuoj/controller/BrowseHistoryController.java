package com.yupi.yuoj.controller;

import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.model.dto.browse.BrowseHistoryQueryRequest;
import com.yupi.yuoj.model.entity.BrowseHistory;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.service.BrowseHistoryService;
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
@RequestMapping("/user/browse")
@Slf4j
@Api(tags = "👁️ 浏览历史管理")
public class BrowseHistoryController {

    @Resource
    private BrowseHistoryService browseHistoryService;

    @Resource
    private UserService userService;

    /**
     * 获取我的浏览历史
     *
     * @param request HTTP请求对象
     * @return 当前用户的浏览历史列表（按浏览时间降序）
     */
    @GetMapping("/my")
    @ApiOperation(value = "获取我的浏览历史", notes = "获取当前登录用户的所有浏览历史记录，按浏览时间降序排列")
    public BaseResponse<List<BrowseHistory>> getMyBrowseHistory(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<BrowseHistory> list = browseHistoryService.lambdaQuery()
                .eq(BrowseHistory::getUserId, loginUser.getId())
                .orderByDesc(BrowseHistory::getCreateTime)
                .list();
        return ResultUtils.success(list);
    }

    /**
     * 添加浏览历史
     *
     * @param templateId 模板ID
     * @param request HTTP请求对象
     * @return 新浏览记录ID
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加浏览历史", notes = "记录用户浏览某个主题模板的行为")
    public BaseResponse<Long> addBrowseHistory(
            @ApiParam("模板ID") @RequestParam Long templateId,
            HttpServletRequest request) {
        if (templateId == null || templateId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        BrowseHistory history = new BrowseHistory();
        history.setUserId(loginUser.getId());
        history.setTemplateId(templateId);
        boolean result = browseHistoryService.save(history);
        return ResultUtils.success(history.getId());
    }

    /**
     * 删除浏览历史
     *
     * @param request 删除请求（包含浏览记录ID）
     * @return 是否成功
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除浏览历史", notes = "删除指定的浏览历史记录")
    public BaseResponse<Boolean> deleteBrowseHistory(@RequestBody DeleteRequest request) {
        if (request == null || request.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = browseHistoryService.removeById(request.getId());
        return ResultUtils.success(result);
    }

    /**
     * 条件查询浏览历史
     *
     * @param request 查询条件（记录ID、用户ID、模板ID）
     * @return 符合条件的浏览历史列表
     */
    @PostMapping("/list/condition")
    @ApiOperation(value = "条件查询浏览历史", notes = "根据条件查询浏览历史列表")
    public BaseResponse<List<BrowseHistory>> listBrowseHistoryByCondition(@RequestBody BrowseHistoryQueryRequest request) {
        List<BrowseHistory> list = browseHistoryService.list(browseHistoryService.getQueryWrapper(request));
        return ResultUtils.success(list);
    }
}
