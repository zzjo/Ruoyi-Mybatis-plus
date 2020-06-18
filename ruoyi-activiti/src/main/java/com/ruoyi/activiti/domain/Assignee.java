package com.ruoyi.activiti.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 流程节点设置表 act_assignee
 * 
 * @author wangqi
 * @date 2019-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("act_assignee")
public class Assignee implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	@TableId(type = IdType.AUTO)
	private Long id;
	/** 节点id */
	private String sid;
	/** 流程id */
	private String deploymentId;
	/** 候选组(角色) */
	private String roleId;
	/** 办理人类型1办理人2候选人3组 */
	private Integer assigneeType;
	/** 节点名称 */
	private String activtiName;

    public Assignee() {
    }

    public Assignee(String sid) {
        this.sid = sid;
    }
}
