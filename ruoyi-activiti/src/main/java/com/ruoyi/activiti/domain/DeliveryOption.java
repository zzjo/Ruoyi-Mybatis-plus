package com.ruoyi.activiti.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by 王琦 on 2019/6/11.<br/>
 */
@Data
public class DeliveryOption implements Serializable {

    private static final long serialVersionUID = 1L;

    //审批人id
    private Long opId;
    //审批人姓名
    private String opName;
    //审批意见
    private String opinion;
    //是否通过
    private boolean  flag;
    //任务
    private String taskId;
    //流程id
    private String processId;
    //创建时间
    private String createTime;
}
