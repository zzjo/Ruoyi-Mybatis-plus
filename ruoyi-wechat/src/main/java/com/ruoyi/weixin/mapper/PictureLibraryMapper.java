package com.ruoyi.weixin.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.weixin.domain.PictureLibrary;

/**
 * 图片库Mapper接口
 * 
 * @author lz
 * @date 2021-10-02
 */
public interface PictureLibraryMapper extends BaseMapper<PictureLibrary>
{
    /**
     * 查询图片库列表
     * 
     * @param pictureLibrary 图片库
     * @return 图片库集合
     */
    public List<PictureLibrary> selectPictureLibraryList(PictureLibrary pictureLibrary);

}
