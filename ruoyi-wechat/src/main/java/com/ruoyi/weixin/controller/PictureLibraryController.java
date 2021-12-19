package com.ruoyi.weixin.controller;

import java.util.List;
import com.ruoyi.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.weixin.domain.PictureLibrary;
import com.ruoyi.weixin.service.IPictureLibraryService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图片库Controller
 * 
 * @author lz
 * @date 2021-10-02
 */
@Controller
@RequestMapping("/weixin/library")
public class PictureLibraryController extends BaseController
{
    private String prefix = "weixin/library";

    @Autowired
    private IPictureLibraryService pictureLibraryService;

    @RequiresPermissions("weixin:library:view")
    @GetMapping()
    public String library()
    {
        return prefix + "/library";
    }

    /**
     * 查询图片库列表
     */
    @RequiresPermissions("weixin:library:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PictureLibrary pictureLibrary)
    {
        startPage();
        List<PictureLibrary> list = pictureLibraryService.selectPictureLibraryList(pictureLibrary);
        return getDataTable(list);
    }

    /**
     * 导出图片库列表
     */
    @RequiresPermissions("weixin:library:export")
    @Log(title = "图片库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PictureLibrary pictureLibrary)
    {
        List<PictureLibrary> list = pictureLibraryService.selectPictureLibraryList(pictureLibrary);
        ExcelUtil<PictureLibrary> util = new ExcelUtil<PictureLibrary>(PictureLibrary.class);
        return util.exportExcel(list, "library");
    }

    /**
     * 新增图片库
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存图片库
     */
    @RequiresPermissions("weixin:library:add")
    @Log(title = "图片库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(PictureLibrary pictureLibrary)
    {
        pictureLibrary.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(pictureLibraryService.insertPictureLibrary(pictureLibrary));
    }

    /**
     * 修改图片库
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        PictureLibrary pictureLibrary = pictureLibraryService.getById(id);
        mmap.put("pictureLibrary", pictureLibrary);
        return prefix + "/edit";
    }

    /**
     * 修改保存图片库
     */
    @RequiresPermissions("weixin:library:edit")
    @Log(title = "图片库", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(PictureLibrary pictureLibrary)
    {
        pictureLibrary.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(pictureLibraryService.updatePictureLibrary(pictureLibrary));
    }

    /**
     * 删除图片库
     */
    @RequiresPermissions("weixin:library:remove")
    @Log(title = "图片库", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(pictureLibraryService.deletePictureLibraryByIds(ids));
    }
}
