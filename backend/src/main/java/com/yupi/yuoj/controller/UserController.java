package com.yupi.yuoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.annotation.AuthCheck;
import com.yupi.yuoj.common.BaseResponse;
import com.yupi.yuoj.common.DeleteRequest;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.common.ResultUtils;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.exception.ThrowUtils;
import com.yupi.yuoj.model.dto.user.UserAddRequest;
import com.yupi.yuoj.model.dto.user.UserLoginRequest;
import com.yupi.yuoj.model.dto.user.UserQueryRequest;
import com.yupi.yuoj.model.dto.user.UserRegisterRequest;
import com.yupi.yuoj.model.dto.user.UserUpdateMyRequest;
import com.yupi.yuoj.model.dto.user.UserUpdateRequest;
import com.yupi.yuoj.model.entity.User;
import com.yupi.yuoj.model.vo.LoginUserVO;
import com.yupi.yuoj.model.vo.UserVO;
import com.yupi.yuoj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "👤 用户管理")
public class UserController {

    @Resource
    private UserService userService;

    // region 登录相关

    /**
     * 用户注册
     *
     * @param userRegisterRequest 注册信息（账号、密码、确认密码、昵称）
     * @return 新用户ID
     */
    @PostMapping("/register")
    @ApiOperation(value = "用户注册", notes = "创建一个新的用户账号")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userName = userRegisterRequest.getUserName();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 登录信息（账号、密码）
     * @param request HTTP请求对象
     * @return 登录用户信息（包含Token）
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "使用账号密码登录系统，返回登录态信息")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 用户登出
     *
     * @param request HTTP请求对象
     * @return 是否成功
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户登出", notes = "清除登录状态")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @param request HTTP请求对象
     * @return 当前登录用户信息
     */
    @GetMapping("/get/login")
    @ApiOperation(value = "获取当前登录用户", notes = "获取当前登录用户的详细信息")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(loginUser));
    }

    // endregion

    // region 用户管理（管理员）

    /**
     * 创建用户（管理员）
     *
     * @param userAddRequest 用户信息
     * @return 新用户ID
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = "admin")
    @ApiOperation(value = "创建用户", notes = "管理员创建新用户账号")
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    /**
     * 删除用户（管理员）
     *
     * @param deleteRequest 删除请求（包含用户ID）
     * @return 是否成功
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = "admin")
    @ApiOperation(value = "删除用户", notes = "管理员删除用户账号")
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户（管理员）
     *
     * @param userUpdateRequest 更新后的用户信息
     * @return 是否成功
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = "admin")
    @ApiOperation(value = "更新用户", notes = "管理员更新用户信息")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        return ResultUtils.success(result);
    }

    /**
     * 根据ID获取用户（管理员）
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = "admin")
    @ApiOperation(value = "根据ID获取用户", notes = "管理员通过ID查询用户详情")
    public BaseResponse<User> getUserById(@RequestParam @ApiParam("用户ID") long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        return ResultUtils.success(user);
    }

    /**
     * 分页获取用户列表（管理员）
     *
     * @param userQueryRequest 查询条件
     * @return 用户分页列表
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = "admin")
    @ApiOperation(value = "分页获取用户列表", notes = "管理员分页查询用户列表")
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, pageSize),
                userService.getQueryWrapper(userQueryRequest));
        return ResultUtils.success(userPage);
    }

    // endregion

    // region 用户个人信息管理

    /**
     * 更新个人信息
     *
     * @param userUpdateMyRequest 更新后的个人信息
     * @param request HTTP请求对象
     * @return 是否成功
     */
    @PostMapping("/update/my")
    @ApiOperation(value = "更新个人信息", notes = "更新当前登录用户的个人信息")
    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest, HttpServletRequest request) {
        if (userUpdateMyRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        User user = new User();
        BeanUtils.copyProperties(userUpdateMyRequest, user);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        return ResultUtils.success(result);
    }

    // endregion
}
