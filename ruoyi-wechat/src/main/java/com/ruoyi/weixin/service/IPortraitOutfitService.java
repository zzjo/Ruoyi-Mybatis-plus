package com.ruoyi.weixin.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: lz
 * @DateTime: 2021/10/2 19:50
 */
public interface IPortraitOutfitService {
    public  void pictureSynthesis(HttpServletResponse response, String id, String url);

}
