package com.ruoyi.activiti.controller;

import com.ruoyi.activiti.domain.DeliveryOption;
import com.ruoyi.activiti.domain.TaskVO;
import com.ruoyi.activiti.service.ActTaskService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by 王琦 on 2019/6/5.<br/>
 */
@Controller
@RequestMapping("/activiti/task")
public class TaskController extends BaseController {

    private String prefix = "activiti/task";

    @Autowired
    private ActTaskService actTaskService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    ProcessEngineFactoryBean processEngine;
    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;

    /**
     * 查看我的任务  admin 查看所有任务
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public TableDataInfo list(TaskVO taskVO)
    {
        List list = new ArrayList();

        SysUser sysUser = ShiroUtils.getSysUser();

        //查询当前登陆人的任务列表
        taskVO.setCandidateUser(sysUser.getUserId().toString());
        List<TaskVO> taskList = actTaskService.selectTaskList(taskVO);
        for(TaskVO task : taskList) {
            //任务组装
        }
        return getDataTable(list);
    }

    /**
     * 查看流程图
     * @param processId
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/getProcImage/{processId}")
    public void getProcImage(@PathVariable("processId") String processId,HttpServletResponse response)
            throws IOException {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();
        String processDefinitionId = null;
        List<String> executedActivityIdList = new ArrayList<String>();
        if (processInstance != null) {
            processDefinitionId = processInstance.getProcessDefinitionId();
            executedActivityIdList = this.runtimeService.getActiveActivityIds(processInstance.getId());
        } else if (historicProcessInstance != null) {
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            List<HistoricActivityInstance> historicActivityInstanceList =
                    historyService.createHistoricActivityInstanceQuery().processInstanceId(processId).orderByHistoricActivityInstanceId().asc().list();
            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                executedActivityIdList.add(activityInstance.getActivityId());
            }
        }

        if (StringUtils.isEmpty(processDefinitionId) || executedActivityIdList.isEmpty()) {
            return;
        }
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();

        InputStream imageStream = diagramGenerator.generateDiagram(
                bpmnModel, "png",
                executedActivityIdList, Collections.<String>emptyList(),
                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                processEngine.getProcessEngineConfiguration().getLabelFontName(),
                "宋体",
                null, 1.0);
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 审批详情
     * @param mmp
     * @param processId
     * @return
     */
    @GetMapping("detail/{processId}")
    public String detail(ModelMap mmp, @PathVariable("processId") String processId) {
        mmp.put("processId",processId);
        return prefix + "/task-detail";
    }

    /**
     * @Author 王琦
     * @Description //TODO流程详情
     * @Date 2020/6/18 18:32
     * @Param [processId]
     * @return com.ruoyi.common.core.page.TableDataInfo
     */
    @PostMapping("/detailList/{processId}")
    @ResponseBody
    public TableDataInfo detailList(@PathVariable("processId") String processId)
    {
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processId).singleResult();
        List<DeliveryOption> deliveryOptions = null;
        if(instance!=null){
            Task task = this.taskService.createTaskQuery()
                    .processInstanceId(processId).singleResult();
            Map<String,Object> map = taskService.getVariables(task.getId());
            Object o = map.get("deliveryOpinionList");
            if(o!=null){
                deliveryOptions = (List<DeliveryOption>)o;
            }
        }else{
            deliveryOptions = new ArrayList<>();
            List<HistoricDetail> list = historyService.createHistoricDetailQuery()
                    .processInstanceId(processId).list();
            HistoricVariableUpdate variableUpdate = null;
            for(HistoricDetail historicDetail:list){
                variableUpdate = (HistoricVariableUpdate)historicDetail;
                if ("deliveryOpinionList".equals(variableUpdate.getVariableName())) {
                    deliveryOptions.clear();
                    deliveryOptions.addAll((List<DeliveryOption>) variableUpdate.getValue());
                }
            }

        }
        if(deliveryOptions==null){
            return getDataTable(new ArrayList<>());
        }else{
            return getDataTable(deliveryOptions);
        }
    }

    /**
     * 审核页面
     * @param mmp
     * @param taskId
     * @return
     */
    @GetMapping("agent/{taskId}/{processId}")
    public String agent(ModelMap mmp, @PathVariable("taskId") String taskId, @PathVariable("processId") String processId) {
        Map<String, Object> variables = taskService.getVariables(taskId);
        //获取具体流程细节
        return prefix + "/task-agent";
    }

    /**
     * 审核
     * @param option
     * @return
     */
    @PostMapping("complete")
    @ResponseBody
    public AjaxResult complete(DeliveryOption option) {
        Map<String, Object> variables = taskService.getVariables(option.getTaskId());
        Map<String, Object> map = new HashMap<>();
        map.put("flag", option.isFlag());
        //审批信息叠加
        List<DeliveryOption> optionList = new ArrayList<DeliveryOption>();
        Object o = variables.get("deliveryOpinionList");
        if (o != null) {
            optionList = (List<DeliveryOption>) o;
        }
        optionList.add(option);
        map.put("deliveryOpinionList", optionList);
        taskService.complete(option.getTaskId(), map);
        //判断流程是否走完
        ProcessInstanceQuery createProcessInstanceQuery = runtimeService.createProcessInstanceQuery();
        ProcessInstanceQuery processInstanceId = createProcessInstanceQuery.processInstanceId(option.getProcessId());
        ProcessInstance singleResult = processInstanceId.singleResult();
        if(singleResult==null){
            //流程完成，处理具体业务逻辑
        }
        return AjaxResult.success();
    }
}
