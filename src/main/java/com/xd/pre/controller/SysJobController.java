package com.xd.pre.controller;


import com.xd.pre.domain.SysJob;
import com.xd.pre.log.SysLog;
import com.xd.pre.service.ISysJobService;
import com.xd.pre.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 岗位管理 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-01
 */
@Api(value="岗位模块")
@RestController
@RequestMapping("/job")
public class SysJobController {

    @Autowired
    private ISysJobService jobService;


    /**
     * 获取岗位列表
     * @param page
     * @param pageSize
     * @param jobName
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('sys:job:view')")
    public Response getList(Integer page, Integer pageSize, @RequestParam(defaultValue = "") String jobName) {
        return Response.ok(jobService.selectJobList(page, pageSize, jobName));
    }

    /**
     * 保存岗位
     *
     * @param sysJob
     * @return
     */
    @SysLog(descrption = "保存岗位")
    @PostMapping
    @PreAuthorize("hasAuthority('sys:job:add')")
    public Response save(@RequestBody SysJob sysJob) {
        return Response.ok(jobService.save(sysJob));
    }

    /**
     * 根据id删除岗位
     * @param id
     * @return
     */
    @SysLog(descrption = "根据id删除岗位")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:job:delete')")
    public Response delete(@PathVariable("id") Integer id) {
        return Response.ok(jobService.removeById(id));
    }

    /**
     * 更新岗位
     * @param sysJob
     * @return
     */
    @SysLog(descrption = "更新岗位")
    @PutMapping
    @PreAuthorize("hasAuthority('sys:job:update')")
    public Response update(@RequestBody SysJob sysJob) {
        return Response.ok(jobService.updateById(sysJob));
    }


    @GetMapping("/{id}")
    public Response selectJobListByDeptId(@PathVariable("id") Integer deptId) {
        return Response.ok(jobService.selectJobListByDeptId(deptId));
    }



}

