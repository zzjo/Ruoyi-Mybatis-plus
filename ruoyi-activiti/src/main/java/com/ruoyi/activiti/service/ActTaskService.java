package com.ruoyi.activiti.service;


import com.ruoyi.activiti.domain.TaskVO;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 */
public interface ActTaskService {

    List<TaskVO> selectTaskList(TaskVO taskVO);

    TaskVO  selectOneTask(String taskId);

    void completeTask(String taskId, Map<String, Object> variables);

    void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars);

    String startProcess(String procDefKey, String businessTable, String businessId, String title, String userId, Map<String, Object> vars);

    String getFormKey(String procDefId, String taskDefKey);

    InputStream traceTaskPhoto(String processDefinitionId, String executionId);
}
