package com.ruoyi.activiti.domain;

import lombok.Data;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.Serializable;

/**
 * 流程实体数据对象
 * 
 * @author ruoyi
 */
@Data
public class ProcessDefinitionDto implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 流程编号
     */
    private String processId;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 部署编号
     */
    private String deploymentId;

    /**
     * 流程命名空间（该编号就是流程文件targetNamespace的属性值）
     */
    private String category;

    /**
     * 流程编号（该编号就是流程文件process元素的id属性值）
     */
    private String key;

    /**
     * 资源文件名称
     */
    private String resourceName;

    /**
     * 图片资源文件名称
     */
    private String diagramResourceName;

    /**
     * 版本号
     */
    private int version;

    private Boolean suspended;
    private Boolean latest;
    private String tenantId;
    private String startableByUser;

    /**
     * 描述信息
     */
    private String description;

    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 总记录数 */
    private Integer total;

    public void setProcessId(String processId)
    {
        this.processId = processId;
    }

    public Integer getPageNum()
    {
        if (pageNum == null)
        {
            pageNum = 0;
        }
        pageNum = (pageNum - 1) * getPageSize();
        if (pageNum < 0)
        {
            pageNum = 0;
        }
        return pageNum;
    }

    public Integer getPageSize()
    {
        if (pageSize == null)
        {
            pageSize = 10;
        }
        return pageSize;
    }

    public ProcessDefinitionDto(Deployment processDefinition)
    {
        this.setProcessId(processDefinition.getId());
        this.name = processDefinition.getName();
    }

    public ProcessDefinitionDto(ProcessDefinition processDefinition)
    {
        this.setProcessId(processDefinition.getId());
        this.name = processDefinition.getName();
        this.deploymentId = processDefinition.getDeploymentId();
        this.category = processDefinition.getCategory();
        this.key = processDefinition.getKey();
        this.resourceName = processDefinition.getResourceName();
        this.diagramResourceName = processDefinition.getDiagramResourceName();
        this.version = processDefinition.getVersion();
        this.description = processDefinition.getDescription();
        this.suspended = processDefinition.isSuspended();
        this.tenantId = processDefinition.getTenantId();
        this.description = processDefinition.getDescription();
    }

    public ProcessDefinitionDto()
    {

    }
}
