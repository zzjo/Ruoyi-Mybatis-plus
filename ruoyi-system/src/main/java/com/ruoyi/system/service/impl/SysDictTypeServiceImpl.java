package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysDictData;
import com.ruoyi.system.domain.SysDictType;
import com.ruoyi.system.mapper.SysDictDataMapper;
import com.ruoyi.system.mapper.SysDictTypeMapper;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.system.utils.DictUtils;

/**
 * 字典 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper,SysDictType> implements ISysDictTypeService
{
    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init()
    {
        List<SysDictType> dictTypeList = dictTypeMapper.selectList(new QueryWrapper<>());
        for (SysDictType dictType : dictTypeList)
        {
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("dict_type",dictType.getDictType());
            List<SysDictData> dictDatas = dictDataMapper.selectList(queryWrapper);
            DictUtils.setDictCache(dictType.getDictType(), dictDatas);
        }
    }

    /**
     * 根据条件分页查询字典类型
     * 
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType)
    {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 根据所有字典类型
     * 
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll()
    {
        return dictTypeMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 根据字典类型查询字典数据
     * 
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType)
    {
        List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
        if (StringUtils.isNotNull(dictDatas))
        {
            return dictDatas;
        }
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dict_type",dictType);
        dictDatas = dictDataMapper.selectList(queryWrapper);
        if (StringUtils.isNotNull(dictDatas))
        {
            DictUtils.setDictCache(dictType, dictDatas);
            return dictDatas;
        }
        return null;
    }

    /**
     * 根据字典类型查询信息
     * 
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeByType(String dictType)
    {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dict_type",dictType);
        return dictTypeMapper.selectOne(queryWrapper);
    }

    /**
     * 批量删除字典类型
     * 
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    public int deleteDictTypeByIds(String ids)
    {
        Long[] dictIds = Convert.toLongArray(ids);
        for (Long dictId : dictIds)
        {
            SysDictType dictType = dictTypeMapper.selectById(dictId);
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("dict_type",dictType.getDictType());
            if (dictDataMapper.selectCount(queryWrapper) > 0)
            {
                throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }
        int count = dictTypeMapper.deleteBatchIds(Arrays.asList(dictIds));
        if (count > 0)
        {
            DictUtils.clearDictCache();
        }
        return count;
    }

    /**
     * 清空缓存数据
     */
    @Override
    public void clearCache()
    {
        DictUtils.clearDictCache();
    }

    /**
     * 新增保存字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(SysDictType dictType)
    {
        int row = dictTypeMapper.insert(dictType);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * 修改保存字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(SysDictType dictType)
    {
        SysDictType oldDict = dictTypeMapper.selectById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        int row = dictTypeMapper.updateById(dictType);
        if (row > 0)
        {
            DictUtils.clearDictCache();
        }
        return row;
    }

    /**
     * 校验字典类型称是否唯一
     * 
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(SysDictType dict)
    {
        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dict_type",dict.getDictType());
        SysDictType dictType = dictTypeMapper.selectOne(queryWrapper);
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue())
        {
            return UserConstants.DICT_TYPE_NOT_UNIQUE;
        }
        return UserConstants.DICT_TYPE_UNIQUE;
    }

    /**
     * 查询字典类型树
     * 
     * @param dictType 字典类型
     * @return 所有字典类型
     */
    @Override
    public List<Ztree> selectDictTree(SysDictType dictType)
    {
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysDictType> dictList = dictTypeMapper.selectDictTypeList(dictType);
        for (SysDictType dict : dictList)
        {
            if (UserConstants.DICT_NORMAL.equals(dict.getStatus()))
            {
                Ztree ztree = new Ztree();
                ztree.setId(dict.getDictId());
                ztree.setName(transDictName(dict));
                ztree.setTitle(dict.getDictType());
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    public String transDictName(SysDictType dictType)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("(" + dictType.getDictName() + ")");
        sb.append("&nbsp;&nbsp;&nbsp;" + dictType.getDictType());
        return sb.toString();
    }
}
