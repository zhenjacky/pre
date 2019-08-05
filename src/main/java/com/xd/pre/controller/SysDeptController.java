package com.xd.pre.controller;


import com.xd.pre.domain.SysDept;
import com.xd.pre.dto.DeptDTO;
import com.xd.pre.log.SysLog;
import com.xd.pre.service.ISysDeptService;
import com.xd.pre.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Api(value="字典模块")
@RestController
@RequestMapping("/dept")
public class SysDeptController {

    @Autowired
    private ISysDeptService deptService;

    /**
     * 保存部门信息
     *
     * @param sysDept
     * @return
     */
    @SysLog(descrption = "保存部门信息")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:dept:add')")
    public Response save(@RequestBody SysDept sysDept) {
        return Response.ok(deptService.save(sysDept));
    }

    /**
     * 获取部门信息
     *
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('sys:dept:view')")
    public Response getDeptList() {
        return Response.ok(deptService.selectDeptList());
    }

    /**
     * 获取部门树
     * @return
     */
    @GetMapping("/tree")
    public Response getDeptTree() {
        return Response.ok(deptService.getDeptTree());
    }


    /**
     * 更新部门信息
     *
     * @return
     */
    @SysLog(descrption = "更新部门信息")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:dept:update')")
    public Response update(@RequestBody DeptDTO deptDto) {
        return Response.ok(deptService.updateDeptById(deptDto));
    }

    /**
     * 根据id删除部门信息
     *
     * @return
     */
    @SysLog(descrption = "根据id删除部门信息")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    public Response delete(@PathVariable("id") Integer id) {
        return Response.ok(deptService.removeById(id));
    }


}

