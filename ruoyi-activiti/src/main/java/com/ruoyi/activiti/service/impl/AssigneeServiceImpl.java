package com.ruoyi.activiti.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.activiti.domain.Assignee;
import com.ruoyi.activiti.mapper.AssigneeMapper;
import com.ruoyi.activiti.service.IAssigneeService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 流程节点设置 服务层实现
 * 
 * @author wangqi
 * @date 2019-06-04
 */
@Service
public class AssigneeServiceImpl extends ServiceImpl<AssigneeMapper,Assignee> implements IAssigneeService
{
	@Autowired
	private AssigneeMapper assigneeMapper;

	/**
     * 查询流程节点设置列表
     * 
     * @param assignee 流程节点设置信息
     * @return 流程节点设置集合
     */
	@Override
	public List<Assignee> selectAssigneeList(Assignee assignee)
	{
	    return assigneeMapper.selectAssigneeList(assignee);
	}
	
    /**
     * 新增流程节点设置
     * 
     * @param assignee 流程节点设置信息
     * @return 结果
     */
	@Override
	public int insertAssignee(Assignee assignee)
	{
	    return assigneeMapper.insert(assignee);
	}

    /**
     * 删除流程节点设置对象
     *
     * @param deploymentId 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAssigneeById(String deploymentId){
	    return  assigneeMapper.deleteById(deploymentId);
    }

	/**
     * 删除流程节点设置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteAssigneeByIds(String ids)
	{
		return assigneeMapper.deleteBatchIds(Arrays.asList(Convert.toStrArray(ids)));
	}
	
}
