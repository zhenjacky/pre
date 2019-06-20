package com.xd.pre.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xd.pre.config.DataScope;
import com.xd.pre.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xd.pre.dto.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lihaodong
 * @since 2019-04-21
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Insert("insert into sys_user (username,password,dept_id,phone,email,avatar,lock_flag) values (#{username},#{password},#{deptId},#{phone},#{email},#{avatar},#{lockFlag})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    boolean insertUser(SysUser sysUser);

    /**
     * 分页查询用户信息（含角色）
     *
     * @param page      分页
     * @param userDTO   查询参数
     * @param dataScope
     * @return list
     */
    IPage<SysUser> getUserVosPage(Page page, @Param("query") UserDTO userDTO, DataScope dataScope);

}