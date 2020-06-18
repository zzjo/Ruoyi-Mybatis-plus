package com.ruoyi.activiti.controller;

import com.ruoyi.activiti.domain.Assignee;
import com.ruoyi.activiti.service.IAssigneeService;
import com.ruoyi.activiti.utils.Checkbox;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysRole;
import com.ruoyi.system.service.ISysRoleService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程节点设置 信息操作处理
 * 
 * @author wangqi
 * @date 2019-06-04
 */
@Controller
@RequestMapping("/activiti/assignee")
public class AssigneeController extends BaseController
{
    private String prefix = "activiti/assignee";
	
	@Autowired
	private IAssigneeService assigneeService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    RepositoryService repositoryService;

    /**
     * 查询流程所有节点信息
     * @param deploymentId
     * @param mmap
     * @return
     */
	@RequiresPermissions("activiti:assignee:view")
	@GetMapping("/assignee/{deploymentId}")
	public String assignee(@PathVariable("deploymentId") String deploymentId, ModelMap mmap)
	{
        /**根据流程实例id查询出所有流程节点*/
        List<ActivityImpl> activityList = this.getActivityList(deploymentId);

        /**角色和节点关系封装成list*/
        List<SysRole> roleList = roleService.selectRoleList(new SysRole());
        List<Checkbox> checkboxes = new ArrayList<>();
        Checkbox checkbox = null;
        Map<String, Object> map = null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Assignee> assigneeList = null;
        List<Checkbox> checkboxList = null;
        for (ActivityImpl activiti : activityList) {
            map = new HashMap<>();
            String name = (String) activiti.getProperty("name");
            if (StringUtils.isEmpty(name) || "start".equals(name) || "end".equals(name)) {
                continue;
            }
            //节点id 、name、节点目前关联的角色 封装成进map
            String nodeId = activiti.getId();
            assigneeList = assigneeService.selectAssigneeList(new Assignee(nodeId));
            List<String> strings = new ArrayList<>();
            assigneeList.forEach(actAssignee1 -> strings.add(actAssignee1.getRoleId()));
            map.put("id", nodeId);
            map.put("name", name);
            checkboxList = new ArrayList<>();
            for (SysRole role : roleList) {
                checkbox = new Checkbox();
                checkbox.setId(role.getRoleId());
                checkbox.setName(role.getRoleName());
                if (strings.contains(role.getRoleId())) {
                    checkbox.setCheck(true);
                }
                checkboxList.add(checkbox);
            }
            map.put("boxJson", checkboxList);
            mapList.add(map);
        }
        mmap.put("actList", mapList);
        mmap.put("deploymentId",deploymentId);

        return prefix + "/assignee";
	}

    /**
     * 根据流程id查询所有流程节点
     * @param deploymentId
     * @return
     */
    private List<ActivityImpl> getActivityList(String deploymentId) {
        org.activiti.engine.repository.ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processDefinition.getId());
        return processDefinitionEntity.getActivities();

    }
	
	/**
	 * 修改保存流程节点设置
	 */
	@Log(title = "流程节点设置", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(HttpServletRequest request)
	{
	    String deploymentId = "";
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            if(entry.getKey().equals("deploymentId")) {
                deploymentId = entry.getValue()[0];
                //根据部署id删除分配
                assigneeService.deleteAssigneeById(deploymentId);
            }else{
                Assignee assignee = new Assignee();
                int sub = entry.getKey().lastIndexOf("_");
                String nodeId = entry.getKey().substring(0, sub);
                nodeId = nodeId.substring(nodeId.lastIndexOf("_") + 1, nodeId.length());
                String nodeName = entry.getKey().substring(entry.getKey().lastIndexOf("_") + 1, entry.getKey().length());
                //更新进list
                assignee.setAssigneeType(3);
                assignee.setDeploymentId(deploymentId);
                assignee.setRoleId(entry.getValue()[0]);
                assignee.setSid(nodeId);
                assignee.setActivtiName(nodeName);
                assigneeService.insertAssignee(assignee);
            }
        }
        return success();
	}
}
