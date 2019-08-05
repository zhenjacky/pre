package com.xd.pre.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.constant.PreConstant;
import com.xd.pre.domain.SysUser;
import com.xd.pre.dto.UserDTO;
import com.xd.pre.exception.BaseException;
import com.xd.pre.log.SysLog;
import com.xd.pre.security.util.SecurityUtil;
import com.xd.pre.service.ISysUserService;
import com.xd.pre.utils.EmailUtil;
import com.xd.pre.utils.PreUtil;
import com.xd.pre.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Api(value="用户模块")
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private EmailUtil emailUtil;

    /**
     * 保存用户包括角色和部门
     *
     * @param userDto
     * @return
     */
    @SysLog(descrption = "保存用户包括角色和部门")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:add')")
    public Response insert(@RequestBody UserDTO userDto) {
        return Response.ok(userService.insertUser(userDto));
    }


    /**
     * 获取用户列表集合
     *
     * @param page
     * @param userDTO
     * @return
     */
    @SysLog(descrption = "查询用户集合")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:user:view')")
    public Response getList(Page page, UserDTO userDTO) {
        return Response.ok(userService.getUsersWithRolePage(page, userDTO));
    }

    /**
     * 更新用户包括角色和部门
     *
     * @param userDto
     * @return
     */
    @SysLog(descrption = "更新用户包括角色和部门")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Response update(@RequestBody UserDTO userDto) {
        return Response.ok(userService.updateUser(userDto));
    }

    /**
     * 删除用户包括角色和部门
     *
     * @param userId
     * @return
     */
    @SysLog(descrption = "根据用户id删除用户包括角色和部门")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Response delete(@PathVariable("userId") Integer userId) {
        return Response.ok(userService.removeUser(userId));
    }


    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    @SysLog(descrption = "重置密码")
    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:rest')")
    public Response restPass(@PathVariable("userId") Integer userId) {
        return Response.ok(userService.restPass(userId));
    }


    /**
     * 获取个人信息
     *
     * @return
     */
    @SysLog(descrption = "获取个人信息")
    @GetMapping("/info")
    public Response getUserInfo() {
        return Response.ok(userService.findByUserInfoName(SecurityUtil.getUser().getUsername()));
    }

    /**
     * 修改密码
     *
     * @return
     */
    @SysLog(descrption = "修改密码")
    @PutMapping("updatePass")
    @PreAuthorize("hasAuthority('sys:user:updatePass')")
    public Response updatePass(@RequestParam String oldPass, @RequestParam String newPass) {
        // 校验密码流程
        SysUser sysUser = userService.findByUserName(SecurityUtil.getUser().getUsername());
        if (!PreUtil.validatePass(oldPass, sysUser.getPassword())) {
            throw new BaseException("原密码错误");
        }
        if (StrUtil.equals(oldPass, newPass)) {
            throw new BaseException("新密码不能与旧密码相同");
        }
        // 修改密码流程
        SysUser user = new SysUser();
        user.setUserId(sysUser.getUserId());
        user.setPassword(PreUtil.encode(newPass));
        return Response.ok(userService.updateUserInfo(user));
    }

    /**
     * 检测用户名是否存在 避免重复
     *
     * @param userName
     * @return
     */
    @PostMapping("/vailUserName")
    public Response vailUserName(@RequestParam String userName) {
        SysUser sysUser = userService.findByUserName(userName);
        return Response.ok(ObjectUtil.isNull(sysUser));
    }

    /**
     * 发送邮箱验证码
     *
     * @param to
     * @param request
     * @return
     */
    @PostMapping("/sendMailCode")
    public Response sendMailCode(@RequestParam String to, HttpServletRequest request) {
        emailUtil.sendSimpleMail(to, request);
        return Response.ok();
    }

    /**
     * 修改密码
     *
     * @return
     */
    @SysLog(descrption = "修改邮箱")
    @PutMapping("updateEmail")
    @PreAuthorize("hasAuthority('sys:user:updateEmail')")
    public Response updateEmail(@RequestParam String mail, @RequestParam String code, @RequestParam String pass, HttpServletRequest request) {
        // 校验验证码流程
        String ccode = (String) request.getSession().getAttribute(PreConstant.RESET_MAIL);
        if (ObjectUtil.isNull(ccode)) {
            throw new BaseException("验证码已过期");
        }
        if (!StrUtil.equals(code.toLowerCase(), ccode)) {
            throw new BaseException("验证码错误");
        }
        // 校验密码流程
        SysUser sysUser = userService.findByUserName(SecurityUtil.getUser().getUsername());
        if (!PreUtil.validatePass(pass, sysUser.getPassword())) {
            throw new BaseException("密码错误");
        }
        // 修改邮箱流程
        SysUser user = new SysUser();
        user.setUserId(sysUser.getUserId());
        user.setEmail(mail);
        return Response.ok(userService.updateUserInfo(user));
    }



}

