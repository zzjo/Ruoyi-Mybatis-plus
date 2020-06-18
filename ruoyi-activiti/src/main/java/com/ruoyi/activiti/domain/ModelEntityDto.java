package com.ruoyi.activiti.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 模型实体数据对象
 * 
 * @author ruoyi
 */
@Data
public class ModelEntityDto implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 模型编号
     */
    protected String id;

    /**
     * 模型名称
     */
    protected String name;

    /**
     * 模型标识
     */
    protected String key;

    /**
     * 流程命名空间（该编号就是流程文件targetNamespace的属性值）
     */
    protected String category;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 最后更新时间
     */
    protected Date lastUpdateTime;

    /**
     * 版本号
     */
    protected Integer version = 1;

    /**
     * 标签信息
     */
    protected String metaInfo;

    /**
     * 部署编号
     */
    protected String deploymentId;

    /**
     * ID：act_ge_bytearray表
     */
    protected String editorSourceValueId;

    /**
     * ID：act_ge_bytearray表，编辑后保存的
     */
    protected String editorSourceExtraValueId;

    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 总记录数 */
    private Integer total;

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

    public Object getPersistentState()
    {
        Map<String, Object> persistentState = new HashMap<String, Object>();
        persistentState.put("name", this.name);
        persistentState.put("key", key);
        persistentState.put("category", this.category);
        persistentState.put("createTime", this.createTime);
        persistentState.put("lastUpdateTime", lastUpdateTime);
        persistentState.put("version", this.version);
        persistentState.put("metaInfo", this.metaInfo);
        persistentState.put("deploymentId", deploymentId);
        persistentState.put("editorSourceValueId", this.editorSourceValueId);
        persistentState.put("editorSourceExtraValueId", this.editorSourceExtraValueId);
        return persistentState;
    }
}
