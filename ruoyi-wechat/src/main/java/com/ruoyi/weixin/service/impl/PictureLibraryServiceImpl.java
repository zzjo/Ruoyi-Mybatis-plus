package com.ruoyi.weixin.service.impl;

import java.sql.Wrapper;
import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.weixin.mapper.PictureLibraryMapper;
import com.ruoyi.weixin.domain.PictureLibrary;
import com.ruoyi.weixin.service.IPictureLibraryService;
import com.ruoyi.common.core.text.Convert;

/**
 * 图片库Service业务层处理
 * 
 * @author lz
 * @date 2021-10-02
 */
@Service
public class PictureLibraryServiceImpl extends ServiceImpl<PictureLibraryMapper,PictureLibrary> implements IPictureLibraryService
{
    @Autowired
    private PictureLibraryMapper pictureLibraryMapper;

    /**
     * 查询图片库列表
     * 
     * @param pictureLibrary 图片库
     * @return 图片库
     */
    @Override
    public List<PictureLibrary> selectPictureLibraryList(PictureLibrary pictureLibrary)
    {
        return pictureLibraryMapper.selectPictureLibraryList(pictureLibrary);
    }

    /**
     * 新增图片库
     * 
     * @param pictureLibrary 图片库
     * @return 结果
     */
    @Override
    public int insertPictureLibrary(PictureLibrary pictureLibrary)
    {
        pictureLibrary.setCreateTime(DateUtils.getNowDate());
        return pictureLibraryMapper.insert(pictureLibrary);
    }

    /**
     * 修改图片库
     * 
     * @param pictureLibrary 图片库
     * @return 结果
     */
    @Override
    public int updatePictureLibrary(PictureLibrary pictureLibrary)
    {
        pictureLibrary.setUpdateTime(DateUtils.getNowDate());
        return pictureLibraryMapper.updateById(pictureLibrary);
    }

    /**
     * 删除图片库对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePictureLibraryByIds(String ids)
    {
        return pictureLibraryMapper.deleteBatchIds(Arrays.asList(Convert.toStrArray(ids)));
    }

    /**
     * 删除图片库信息
     * 
     * @param id 图片库ID
     * @return 结果
     */
    @Override
    public int deletePictureLibraryById(Long id)
    {
        return pictureLibraryMapper.deleteById(id);
    }

    @Override
    public List<PictureLibrary> selectPictureByHolidaysId(String id) {
        return baseMapper.selectList(Wrappers.<PictureLibrary>lambdaQuery().eq(PictureLibrary::getHolidaysMenuId,id));
    }
}
