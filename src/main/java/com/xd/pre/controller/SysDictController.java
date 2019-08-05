package com.xd.pre.controller;


import com.xd.pre.domain.SysDict;
import com.xd.pre.dto.DictDTO;
import com.xd.pre.log.SysLog;
import com.xd.pre.service.ISysDictService;
import com.xd.pre.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author lihaodong
 * @since 2019-05-17
 */
@Api(value="字典模块")
@RestController
@RequestMapping("/dict")
public class SysDictController {

    @Autowired
    private ISysDictService dictService;

    /**
     * 保存字典信息
     *
     * @param sysDict
     * @return
     */
    @SysLog(descrption = "保存字典信息")
    @PostMapping
    public Response save(@RequestBody SysDict sysDict) {
        return Response.ok(dictService.save(sysDict));
    }

    /**
     * 获取字典列表集合
     *
     * @param page
     * @param pageSize
     * @return
     */
    @SysLog(descrption = "查询字典集合")
    @GetMapping
    @PreAuthorize("hasAuthority('sys:dipt:view')")
    public Response getList(Integer page, Integer pageSize) {
        return Response.ok(dictService.selectDictList(page, pageSize));
    }


    /**
     * @Author 李号东
     * @Description 根据名称获取字典值详情
     * @Date 15:20 2019-05-26
     **/
    @GetMapping("/getDictDetailList")
    public Response selectDictDetailList(@RequestParam String name) {
        return Response.ok(dictService.selectDictDetailList(name));
    }

    /**
     * 更新字典
     *
     * @param dictDto
     * @return
     */
    @SysLog(descrption = "更新字典")
    @PutMapping
    public Response update(@RequestBody DictDTO dictDto) {
        return Response.ok(dictService.updateDict(dictDto));
    }


    /**
     * 根据id删除字典
     * @param id
     * @return
     */
    @SysLog(descrption = "根据id删除字典")
    @DeleteMapping("{id}")
    public Response delete(@PathVariable("id") int id) {
        return Response.ok(dictService.removeById(id));
    }

    /**
     * 根据id删除字典
     * @param name
     * @return
     */
    @SysLog(descrption = "根据name删除字典")
    @DeleteMapping("/delete")
    public Response deleteName(@RequestParam String name) {
        return Response.ok(dictService.deleteDictByName(name));
    }

}

