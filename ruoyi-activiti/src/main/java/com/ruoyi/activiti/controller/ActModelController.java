package com.ruoyi.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruoyi.activiti.domain.ModelEntityDto;
import com.ruoyi.activiti.service.ActModelService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_DESCRIPTION;
import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_NAME;

/**
 * 模型管理 操作处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/activiti/model")
public class ActModelController extends BaseController
{
    private String prefix = "activiti/model";

    @Autowired
    private ActModelService actModelService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 流程列表
     * @return
     */
    @RequiresPermissions("activiti:model:view")
    @GetMapping
    public String model()
    {
        return prefix + "/model";
    }

    /**
     * 流程列表查询
     * @param modelEntityDto
     * @return
     */
    @RequiresPermissions("activiti:model:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ModelEntityDto modelEntityDto)
    {
        return actModelService.selectModelList(modelEntityDto);
    }

    /**
     * 新增模型
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 流程新增
     * @param modelid
     * @param modelname
     * @param modeldes
     * @return
     */
    @RequiresPermissions("activiti:model:add")
    @Log(title = "流程模型管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addModle(@RequestParam("modelid") String modelid, @RequestParam("modelname") String modelname,
                               @RequestParam("modeldes") String modeldes)
    {
        ModelEntity model = new ModelEntity();
        //流程ID
        String key = modelid;
        //流程名字
        String name = modelname;
        //流程描述
        String description = modeldes;
        //流程版本
        int revision = 1;
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
        String modelId = actModelService.createModel(model);
        int response=0;
        if(!modelId.isEmpty()){
            response=1;
        }
        return toAjax(response);
    }

    /**
     * 获取流程定义
     * @param modelId
     * @return
     */
    @GetMapping("/{modelId}/json")
    @ResponseBody
    public ObjectNode getEditorJson(@PathVariable String modelId)
    {
        ObjectNode modelNode = actModelService.selectWrapModelById(modelId);
        return modelNode;
    }

    /**
     * 修改
     * @param modelId
     * @return
     */
    @GetMapping("/edit/{modelId}")
    public String edit(@PathVariable("modelId") String modelId)
    {
        return redirect("/modeler.html?modelId=" + modelId);
    }

    /**
     * 导入stencil配置文件
     * @return
     */
    @GetMapping(value = "/editor/stencilset", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getStencilset() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
        } catch (Exception e) {
            throw new ActivitiException("导入 stencil 文件出现错误", e);
        }
    }

    @Log(title = "删除模型", businessType = BusinessType.DELETE)
    @RequiresPermissions("activiti:model:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(actModelService.deleteModelIds(ids));
    }

    /**
     * 发布流程
     *
     * @param modelId
     * @return
     * @throws Exception
     */
    @Log(title = "发布流程", businessType = BusinessType.UPDATE)
    @RequiresPermissions("activiti:model:deploy")
    @GetMapping("/deploy/{modelId}")
    @ResponseBody
    public AjaxResult deploy(@PathVariable("modelId") String modelId)
    {
        return actModelService.deployProcess(modelId);
    }

    @Log(title = "模型管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/{modelId}/save")
    @ResponseBody
    public void save(@PathVariable String modelId, String name, String description, String json_xml,
            String svg_xml) throws IOException
    {
        Model model = actModelService.selectModelById(modelId);
        ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
        modelJson.put(MODEL_NAME, name);
        modelJson.put(MODEL_DESCRIPTION, description);
        model.setMetaInfo(modelJson.toString());
        model.setName(name);
        actModelService.update(model, json_xml, svg_xml);

    }
}
