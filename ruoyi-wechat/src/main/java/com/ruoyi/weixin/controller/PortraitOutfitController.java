package com.ruoyi.weixin.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.weixin.domain.PictureLibrary;
import com.ruoyi.weixin.service.IHolidaysMenuService;
import com.ruoyi.weixin.service.IPictureLibraryService;
import com.ruoyi.weixin.service.IPortraitOutfitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: lz
 * @DateTime: 2021/10/2 17:08
 */
@Api(tags = "头像变装")
@RestController
@RequestMapping("/portrait")
public class PortraitOutfitController extends BaseController {
    @Autowired
    private IHolidaysMenuService holidaysMenuService;

    @Autowired
    private IPictureLibraryService pictureLibraryService;

    @Autowired
    private IPortraitOutfitService portraitOutfitService;

    @ApiOperation("获取节假日")
    @GetMapping("/getHolidays")
    public AjaxResult getHolidays(){
        return AjaxResult.success(holidaysMenuService.selectHolidaysMenuList(null));
    }

    @ApiOperation("获取节假日对应底层图片")
    @PostMapping("/getBottomImage")
    public AjaxResult getBottomImage(String id){
        return AjaxResult.success(pictureLibraryService.selectPictureByHolidaysId(id));
    }

    @ApiOperation("图片合成")
    @GetMapping("/pictureSynthesis")
    public void pictureSynthesis(HttpServletResponse response,String id, String url){
        portraitOutfitService.pictureSynthesis(response,id,url);
    }

    @ApiOperation("获取指数数据")
    @GetMapping("/getIndexData")
    public AjaxResult getIndexData(){
       return AjaxResult.success(holidaysMenuService.getIndexData());
    }
}
