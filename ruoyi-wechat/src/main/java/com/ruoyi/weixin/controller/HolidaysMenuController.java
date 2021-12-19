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
import com.ruoyi.weixin.domain.HolidaysMenu;
import com.ruoyi.weixin.service.IHolidaysMenuService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * wx_holidays_menuController
 *
 * @author lz
 * @date 2021-10-02
 */
@Controller
@RequestMapping("/weixin/menu")
public class HolidaysMenuController extends BaseController
{
    private String prefix = "weixin/menu";

    @Autowired
    private IHolidaysMenuService holidaysMenuService;

    @RequiresPermissions("weixin:menu:view")
    @GetMapping()
    public String menu()
    {
        return prefix + "/menu";
    }

    /**
     * 查询wx_holidays_menu列表
     */
    @RequiresPermissions("weixin:menu:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(HolidaysMenu holidaysMenu)
    {
        startPage();
        List<HolidaysMenu> list = holidaysMenuService.selectHolidaysMenuList(holidaysMenu);
        return getDataTable(list);
    }

    /**
     * 导出wx_holidays_menu列表
     */
    @RequiresPermissions("weixin:menu:export")
    @Log(title = "wx_holidays_menu", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(HolidaysMenu holidaysMenu)
    {
        List<HolidaysMenu> list = holidaysMenuService.selectHolidaysMenuList(holidaysMenu);
        ExcelUtil<HolidaysMenu> util = new ExcelUtil<HolidaysMenu>(HolidaysMenu.class);
        return util.exportExcel(list, "menu");
    }

    /**
     * 新增wx_holidays_menu
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存wx_holidays_menu
     */
    @RequiresPermissions("weixin:menu:add")
    @Log(title = "wx_holidays_menu", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HolidaysMenu holidaysMenu)
    {
        holidaysMenu.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(holidaysMenuService.insertHolidaysMenu(holidaysMenu));
    }

    /**
     * 修改wx_holidays_menu
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        HolidaysMenu holidaysMenu = holidaysMenuService.getById(id);
        mmap.put("holidaysMenu", holidaysMenu);
        return prefix + "/edit";
    }

    /**
     * 修改保存wx_holidays_menu
     */
    @RequiresPermissions("weixin:menu:edit")
    @Log(title = "wx_holidays_menu", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HolidaysMenu holidaysMenu)
    {
        holidaysMenu.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(holidaysMenuService.updateHolidaysMenu(holidaysMenu));
    }

    /**
     * 删除wx_holidays_menu
     */
    @RequiresPermissions("weixin:menu:remove")
    @Log(title = "wx_holidays_menu", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(holidaysMenuService.deleteHolidaysMenuByIds(ids));
    }
}
