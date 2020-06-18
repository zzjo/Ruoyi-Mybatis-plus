package com.ruoyi.activiti.utils;

import lombok.Data;

/**
 * @author zhuxiaomeng
 * @date 2017/12/21.
 * @email 154040976@qq.com
 * 复选框类
 */
@Data
public class Checkbox {

  private Long id;
  private String name;
  /**默认未选择*/
  private boolean check=false;
}
