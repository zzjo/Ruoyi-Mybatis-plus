package com.ruoyi.weixin.service.impl;

import java.util.List;
import java.util.Arrays;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.weixin.mapper.HolidaysMenuMapper;
import com.ruoyi.weixin.domain.HolidaysMenu;
import com.ruoyi.weixin.service.IHolidaysMenuService;
import com.ruoyi.common.core.text.Convert;

/**
 * wx_holidays_menuService业务层处理
 * 
 * @author lz
 * @date 2021-10-02
 */
@Service
public class HolidaysMenuServiceImpl extends ServiceImpl<HolidaysMenuMapper,HolidaysMenu> implements IHolidaysMenuService
{
    @Autowired
    private HolidaysMenuMapper holidaysMenuMapper;

    /**
     * 查询wx_holidays_menu列表
     * 
     * @param holidaysMenu wx_holidays_menu
     * @return wx_holidays_menu
     */
    @Override
    public List<HolidaysMenu> selectHolidaysMenuList(HolidaysMenu holidaysMenu)
    {
        return holidaysMenuMapper.selectHolidaysMenuList(holidaysMenu);
    }

    /**
     * 新增wx_holidays_menu
     * 
     * @param holidaysMenu wx_holidays_menu
     * @return 结果
     */
    @Override
    public int insertHolidaysMenu(HolidaysMenu holidaysMenu)
    {
        holidaysMenu.setCreateTime(DateUtils.getNowDate());
        return holidaysMenuMapper.insert(holidaysMenu);
    }

    /**
     * 修改wx_holidays_menu
     * 
     * @param holidaysMenu wx_holidays_menu
     * @return 结果
     */
    @Override
    public int updateHolidaysMenu(HolidaysMenu holidaysMenu)
    {
        holidaysMenu.setUpdateTime(DateUtils.getNowDate());
        return holidaysMenuMapper.updateById(holidaysMenu);
    }

    /**
     * 删除wx_holidays_menu对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHolidaysMenuByIds(String ids)
    {
        return holidaysMenuMapper.deleteBatchIds(Arrays.asList(Convert.toStrArray(ids)));
    }

    /**
     * 删除wx_holidays_menu信息
     * 
     * @param id wx_holidays_menuID
     * @return 结果
     */
    @Override
    public int deleteHolidaysMenuById(Long id)
    {
        return holidaysMenuMapper.deleteById(id);
    }
}
