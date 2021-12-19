package com.ruoyi.weixin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.weixin.domain.PictureLibrary;
import com.ruoyi.weixin.mapper.PictureLibraryMapper;
import com.ruoyi.weixin.service.IPortraitOutfitService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @Author: lz
 * @DateTime: 2021/10/2 19:51
 */
@Service
public class PortraitOutfitServiceImpl implements IPortraitOutfitService {

    @Autowired
    private PictureLibraryMapper pictureLibraryMapper;

    @Override
    public void pictureSynthesis(HttpServletResponse response,String id, String url) {
        PictureLibrary pictureLibrary = pictureLibraryMapper.selectOne(Wrappers.<PictureLibrary>lambdaQuery().eq(PictureLibrary::getId, id));
        //获取背景图片路径
        String backgroundPicture=pictureLibrary.getPicturePath();
        //读取图片
        addWaterMake(response,backgroundPicture,url);
    }

    public void addWaterMake(HttpServletResponse response, String srcImgPath, String headImgUrl) {
        try {
            // 读取原图片信息
            //得到文件(底图)
            File srcImgFile = new File(srcImgPath);
            //文件转化为图片(底图)
            Image srcImg = ImageIO.read(srcImgFile);
            //得到文件（头像）
//            File headFile = new File(headImg);
            //文件转化为图片(头像)
            Image headSrcImg = ImageIO.read(new URL(headImgUrl));
            //获取图片的宽
            int srcImgWidth = srcImg.getWidth(null);
            //获取图片的高
            int srcImgHeight = srcImg.getHeight(null);
            // 创建在内存里的图像缓冲区对象
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            //在缓冲区创建图形
            Graphics2D g = bufImg.createGraphics();
            //底图生成+位置设置（设置参数图片的原尺寸） 绘制底图模板
            g.drawImage(headSrcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            //头像生成+位置设置     //水平 //图片大小（水平=在底图的某个位置）绘制头像
//            g.drawImage(srcImg, srcImgWidth/4*3, srcImgHeight/4*3, srcImgWidth/4, srcImgHeight/4, null);
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            //对bufImg对象设置的参数进行提交！
            g.dispose();
            // 输出图片
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufImg,"jpg",out);
            String fileName = srcImgPath.substring(srcImgPath.lastIndexOf('/') + 1);
            genCode(response,out.toByteArray(),fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data,  String fileName) throws IOException
    {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/x-msdownload;");
        IOUtils.write(data, response.getOutputStream());
    }
//    public void down(){
//        String imgUrl="";//URL地址
//        String fileName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1);
//        BufferedInputStream is = null;
//        BufferedOutputStream os = null;
//        try {
//            URL url = new URL(imgUrl);
//            this.getServletResponse().setContentType("application/x-msdownload;");
//            this.getServletResponse().setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
//            this.getServletResponse().setHeader("Content-Length", String.valueOf(url.openConnection().getContentLength()));
//            is = new BufferedInputStream(url.openStream());
//            os = new BufferedOutputStream(this.getServletResponse().getOutputStream());
//            byte[] buff = new byte[2048];
//            int bytesRead;
//            while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {
//                os.write(buff, 0, bytesRead);
//            }
//            if (is != null)
//                is.close();
//            if (os != null)
//                os.close();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
