package com.ruoyi.activiti.controller;

import com.ruoyi.activiti.domain.ProcessDefinitionDto;
import com.ruoyi.activiti.service.ActProcessService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * 流程管理 操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/activiti/process")
public class ActProcessController extends BaseController
{
    private String prefix = "activiti/process";

    @Autowired
    private ActProcessService actProcessService;

    /**
     * 流程列表
     * @return
     */
    @RequiresPermissions("activiti:process:view")
    @GetMapping
    public String process()
    {
        return prefix + "/process";
    }

    /**
     * 流程列表查询
     * @param processDefinitionDto
     * @return
     */
    @RequiresPermissions("activiti:process:list")
    @PostMapping("list")
    @ResponseBody
    public TableDataInfo list(ProcessDefinitionDto processDefinitionDto)
    {
        return actProcessService.selectProcessDefinitionList(processDefinitionDto);
    }

    /**
     * 查看流程定义图片
     * @param imageName
     * @param deploymentId
     * @param response
     */
    @GetMapping(value = "/resource/{imageName}/{deploymentId}")
    public void viewImage(@PathVariable("imageName") String imageName,
            @PathVariable("deploymentId") String deploymentId, HttpServletResponse response)
    {
        try
        {
            InputStream in = actProcessService.findImageStream(deploymentId, imageName);
            for (int bit = -1; (bit = in.read()) != -1;)
            {
                response.getOutputStream().write(bit);
            }
            in.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 删除流程
     * @param ids
     * @return
     */
    @RequiresPermissions("activiti:process:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(actProcessService.deleteProcessDefinitionByDeploymentIds(ids));
    }
}
