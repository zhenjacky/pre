package com.xd.pre.controller;


import com.xd.pre.domain.SysMenu;
import com.xd.pre.dto.MenuDTO;
import com.xd.pre.log.SysLog;
import com.xd.pre.security.PreUser;
import com.xd.pre.security.util.SecurityUtil;
import com.xd.pre.service.ISysMenuService;
import com.xd.pre.utils.PreUtil;
import com.xd.pre.utils.Response;
import com.xd.pre.vo.MenuVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Api(value="菜单模块")
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private ISysMenuService menuService;

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @PreAuthorize("hasAuthority('sys:menu:add')")
    @SysLog(descrption = "添加菜单")
    @PostMapping
    public Response save(@RequestBody SysMenu menu) {
        return Response.ok(menuService.save(menu));
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @GetMapping
    public Response getMenuTree() {
        PreUser securityUser = SecurityUtil.getUser();
        return Response.ok(menuService.selectMenuTree(securityUser.getUserId()));
    }


    /**
     * 获取所有菜单
     *
     * @return
     */
    @GetMapping("/getMenus")
    public Response getMenus() {
        return Response.ok(menuService.selectMenuTree(0));
    }

    /**
     * 修改菜单
     *
     * @param menuDto
     * @return
     */
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @SysLog(descrption = "修改菜单")
    @PutMapping
    public Response updateMenu(@RequestBody MenuDTO menuDto) {
        return Response.ok(menuService.updateMenuById(menuDto));
    }

    /**
     * 根据id删除菜单
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @SysLog(descrption = "删除菜单")
    @DeleteMapping("/{id}")
    public Response deleteMenu(@PathVariable("id") Integer id) {
        return menuService.removeMenuById(id);
    }

    /**
     * 获取路由
     *
     * @return
     */
    @GetMapping("/getRouters")
    public Response getRouters() {
        PreUser securityUser = SecurityUtil.getUser();
        List<MenuVo> menuVos = PreUtil.buildMenus(menuService.selectMenuTree(securityUser.getUserId()));
        return Response.ok(PreUtil.buildMenus(menuService.selectMenuTree(securityUser.getUserId())));
    }

}

