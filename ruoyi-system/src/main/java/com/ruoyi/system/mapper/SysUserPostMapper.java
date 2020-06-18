package com.ruoyi.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.SysUserPost;

/**
 * 用户与岗位关联表 数据层
 * 
 * @author ruoyi
 */
public interface SysUserPostMapper extends BaseMapper<SysUserPost>
{
    /**
     * 批量新增用户岗位信息
     * 
     * @param userPostList 用户角色列表
     * @return 结果
     */
    public int batchUserPost(List<SysUserPost> userPostList);
}
