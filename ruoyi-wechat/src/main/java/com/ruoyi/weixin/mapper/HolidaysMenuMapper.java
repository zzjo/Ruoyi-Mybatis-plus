package com.ruoyi.weixin.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.weixin.domain.HolidaysMenu;

/**
 * wx_holidays_menuMapper接口
 * 
 * @author lz
 * @date 2021-10-02
 */
public interface HolidaysMenuMapper extends BaseMapper<HolidaysMenu>
{
    /**
     * 查询wx_holidays_menu列表
     * 
     * @param holidaysMenu wx_holidays_menu
     * @return wx_holidays_menu集合
     */
    public List<HolidaysMenu> selectHolidaysMenuList(HolidaysMenu holidaysMenu);

}
