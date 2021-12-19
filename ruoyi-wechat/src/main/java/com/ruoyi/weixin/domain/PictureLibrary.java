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
 * 图片库对象 wx_picture_library
 * 
 * @author lz
 * @date 2021-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wx_picture_library")
public class PictureLibrary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 假日外键 */
    @Excel(name = "假日外键")
    private Long holidaysMenuId;

    /** 图片路径 */
    @Excel(name = "图片路径")
    private String picturePath;

}
