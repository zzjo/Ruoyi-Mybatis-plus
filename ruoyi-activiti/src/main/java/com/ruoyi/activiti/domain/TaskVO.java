package com.ruoyi.activiti.domain;

import lombok.Data;
import org.activiti.engine.task.Task;

import java.io.Serializable;
import java.util.List;

/**
 * 任务封装
 */
@Data
public class TaskVO  implements Serializable {
    public TaskVO() {

    }

    public TaskVO(Task task) {
        this.setId(task.getId());
        this.setKey(task.getTaskDefinitionKey());
        this.setName(task.getName());
        this.setDescription(task.getDescription());
        this.setAssignee(task.getAssignee());
        this.setFormKey(task.getFormKey());
        this.setProcessId(task.getProcessInstanceId());
        this.setProcessDefinitionId(task.getProcessDefinitionId());
        this.setExecutionId(task.getExecutionId());
    }

    /**
     * 任务编号
     */
    private String id;
    /**
     * 任务名称
     */
    private String name;

    private Boolean active;

    private String key;

    private String description;

    private String formKey;

    private String assignee;

    private String processId;

    private String processDefinitionId;

    private String executionId;

    private Integer priority;

    private String owner;

    private Boolean unassigned;

    private String delegationState;

    private String candidateUser;

    private String candidateGroup;

    private List<String> candidateGroupIn;

    private String involvedUser;

    private String processInstanceId;

    private String processInstanceBusinessKey;

    private List<String> processInstanceIdIn;

    private String processDefinitionKey;
}
