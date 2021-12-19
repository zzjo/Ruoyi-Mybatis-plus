package com.ruoyi.weixin.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.weixin.domain.HolidaysMenu;

/**
 * wx_holidays_menuService接口
 * 
 * @author lz
 * @date 2021-10-02
 */
public interface IHolidaysMenuService extends IService<HolidaysMenu>
{
    /**
     * 查询wx_holidays_menu列表
     * 
     * @param holidaysMenu wx_holidays_menu
     * @return wx_holidays_menu集合
     */
    public List<HolidaysMenu> selectHolidaysMenuList(HolidaysMenu holidaysMenu);

    /**
     * 新增wx_holidays_menu
     * 
     * @param holidaysMenu wx_holidays_menu
     * @return 结果
     */
    public int insertHolidaysMenu(HolidaysMenu holidaysMenu);

    /**
     * 修改wx_holidays_menu
     * 
     * @param holidaysMenu wx_holidays_menu
     * @return 结果
     */
    public int updateHolidaysMenu(HolidaysMenu holidaysMenu);

    /**
     * 批量删除wx_holidays_menu
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHolidaysMenuByIds(String ids);

    /**
     * 删除wx_holidays_menu信息
     * 
     * @param id wx_holidays_menuID
     * @return 结果
     */
    public int deleteHolidaysMenuById(Long id);

    String getIndexData();
}
