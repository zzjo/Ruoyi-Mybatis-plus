package com.ruoyi.activiti.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.activiti.domain.Assignee;

import java.util.List;	

/**
 * 流程节点设置 数据层
 * 
 * @author wangqi
 * @date 2019-06-04
 */
public interface AssigneeMapper extends BaseMapper<Assignee>
{
	/**
     * 查询流程节点设置列表
     * 
     * @param assignee 流程节点设置信息
     * @return 流程节点设置集合
     */
	public List<Assignee> selectAssigneeList(Assignee assignee);
	
}