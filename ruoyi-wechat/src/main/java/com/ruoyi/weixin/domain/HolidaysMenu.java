package com.ruoyi.weixin.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import javax.validation.constraints.NotBlank;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * wx_holidays_menu对象 wx_holidays_menu
 * 
 * @author lz
 * @date 2021-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wx_holidays_menu")
public class HolidaysMenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 假日名称 */
    @NotBlank(message = "假日名称不能为空")
    @Excel(name = "假日名称")
    private String name;


}
