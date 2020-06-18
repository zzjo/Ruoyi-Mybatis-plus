package com.ruoyi.activiti.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.activiti.domain.Assignee;
import java.util.List;

/**
 * 流程节点设置 服务层
 * 
 * @author wangqi
 * @date 2019-06-04
 */
public interface IAssigneeService extends IService<Assignee>
{
	/**
     * 查询流程节点设置列表
     * 
     * @param assignee 流程节点设置信息
     * @return 流程节点设置集合
     */
	public List<Assignee> selectAssigneeList(Assignee assignee);
	
	/**
     * 新增流程节点设置
     *
     * @param assignee 流程节点设置信息
     * @return 结果
     */
	public int insertAssignee(Assignee assignee);

    /**
     * 删除流程节点设置信息
     *
     * @param deploymentId 需要删除的数据ID
     * @return 结果
     */
    public int deleteAssigneeById(String deploymentId);

	/**
     * 删除流程节点设置信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteAssigneeByIds(String ids);
	
}
