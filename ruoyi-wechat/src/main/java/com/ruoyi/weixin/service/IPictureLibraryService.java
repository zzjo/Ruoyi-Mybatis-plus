package com.ruoyi.weixin.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.weixin.domain.PictureLibrary;

/**
 * 图片库Service接口
 * 
 * @author lz
 * @date 2021-10-02
 */
public interface IPictureLibraryService extends IService<PictureLibrary>
{
    /**
     * 查询图片库列表
     * 
     * @param pictureLibrary 图片库
     * @return 图片库集合
     */
    public List<PictureLibrary> selectPictureLibraryList(PictureLibrary pictureLibrary);

    /**
     * 新增图片库
     * 
     * @param pictureLibrary 图片库
     * @return 结果
     */
    public int insertPictureLibrary(PictureLibrary pictureLibrary);

    /**
     * 修改图片库
     * 
     * @param pictureLibrary 图片库
     * @return 结果
     */
    public int updatePictureLibrary(PictureLibrary pictureLibrary);

    /**
     * 批量删除图片库
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePictureLibraryByIds(String ids);

    /**
     * 删除图片库信息
     * 
     * @param id 图片库ID
     * @return 结果
     */
    public int deletePictureLibraryById(Long id);

    /**
     * 获取节假日对应的图片
     * @param id
     * @return
     */
    public List<PictureLibrary> selectPictureByHolidaysId(String id);
}
