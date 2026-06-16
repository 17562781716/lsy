package com.yupi.yuoj.controller;

import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.model.dto.message.UserMessageQueryRequest;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.entity.UserMessage;
import com.yupi.yuoj.service.UserMessageService;
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
@RequestMapping("/user/message")
@Slf4j
@Api(tags = "💬 用户消息管理")
public class UserMessageController {

    @Resource
    private UserMessageService userMessageService;

    @Resource
    private UserService userService;

    /**
     * 获取我的消息列表
     *
     * @param request HTTP请求对象
     * @return 当前登录用户的消息列表（按创建时间降序）
     */
    @GetMapping("/my")
    @ApiOperation(value = "获取我的消息列表", notes = "获取当前登录用户的所有消息记录，按创建时间降序排列")
    public BaseResponse<List<UserMessage>> getMyMessages(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        List<UserMessage> list = userMessageService.lambdaQuery()
                .eq(UserMessage::getUserId, loginUser.getId())
                .orderByDesc(UserMessage::getCreateTime)
                .list();
        return ResultUtils.success(list);
    }

    /**
     * 获取未读消息数量
     *
     * @param request HTTP请求对象
     * @return 未读消息数量
     */
    @GetMapping("/unread/count")
    @ApiOperation(value = "获取未读消息数量", notes = "获取当前登录用户的未读消息数量")
    public BaseResponse<Long> getUnreadCount(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Long count = userMessageService.lambdaQuery()
                .eq(UserMessage::getUserId, loginUser.getId())
                .eq(UserMessage::getIsRead, 0)
                .count();
        return ResultUtils.success(count);
    }

    /**
     * 标记消息为已读
     *
     * @param messageId 消息ID
     * @param request HTTP请求对象
     * @return 是否成功
     */
    @PostMapping("/mark/read")
    @ApiOperation(value = "标记消息为已读", notes = "将指定消息标记为已读状态")
    public BaseResponse<Boolean> markAsRead(
            @ApiParam("消息ID") @RequestParam Long messageId,
            HttpServletRequest request) {
        if (messageId == null || messageId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserMessage message = userMessageService.getById(messageId);
        if (message != null) {
            message.setIsRead(1);
            userMessageService.updateById(message);
        }
        return ResultUtils.success(true);
    }

    /**
     * 删除消息
     *
     * @param request 删除请求（包含消息ID）
     * @return 是否成功
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除消息", notes = "删除指定的消息记录")
    public BaseResponse<Boolean> deleteMessage(@RequestBody DeleteRequest request) {
        if (request == null || request.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userMessageService.removeById(request.getId());
        return ResultUtils.success(result);
    }

    /**
     * 条件查询消息列表
     *
     * @param request 查询条件（消息ID、用户ID、消息类型、已读状态）
     * @return 符合条件的消息列表
     */
    @PostMapping("/list/condition")
    @ApiOperation(value = "条件查询消息列表", notes = "根据条件查询消息列表")
    public BaseResponse<List<UserMessage>> listMessageByCondition(@RequestBody UserMessageQueryRequest request) {
        List<UserMessage> list = userMessageService.list(userMessageService.getQueryWrapper(request));
        return ResultUtils.success(list);
    }
}
