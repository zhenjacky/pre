package com.xd.pre.controller;


import com.xd.pre.dto.RoleDTO;
import com.xd.pre.log.SysLog;
import com.xd.pre.service.ISysRoleService;
import com.xd.pre.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Api(value="角色模块")
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private ISysRoleService roleService;

    /**
     * 获取角色列表
     *
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('sys:role:view')")
    public Response getRoleList(@RequestParam String roleName) {
        return Response.ok(roleService.selectRoleList(roleName));
    }

    /**
     * 保存角色以及菜单权限
     *
     * @param roleDto
     * @return
     */
    @SysLog(descrption = "保存角色以及菜单权限")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:add')")
    public Response save(@RequestBody RoleDTO roleDto) {
        return Response.ok(roleService.saveRoleMenu(roleDto));
    }

    /**
     * 根据角色id获取菜单
     *
     * @param roleId
     * @return
     */
    @SysLog(descrption = "据角色id获取菜单")
    @GetMapping("/findRoleMenus/{roleId}")
    public Response findRoleMenus(@PathVariable("roleId") Integer roleId) {
        return Response.ok(roleService.findMenuListByRoleId(roleId));
    }


    /**
     * 更新角色以及菜单权限
     * @param roleDto
     * @return
     */
    @SysLog(descrption = "更新角色以及菜单权限")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Response update(@RequestBody RoleDTO roleDto) {
        return Response.ok(roleService.updateRoleMenu(roleDto));
    }

    /**
     * 删除角色以及权限
     * @param roleId
     * @return
     */
    @SysLog(descrption = "删除角色以及权限")
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Response delete(@PathVariable("roleId") Integer roleId) {
        return Response.ok(roleService.removeById(roleId));
    }



}

