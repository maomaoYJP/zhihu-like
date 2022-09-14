package com.maomao.zhihu.controller;

import com.maomao.zhihu.utils.JsonResult;
import com.maomao.zhihu.utils.ossFileUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author maomao
 * 2022/9/12 14:49
 */
@Controller
public class UploadController {

    @Resource
    ossFileUtil ossFileUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/upload")
    @ResponseBody
    public JsonResult upload(MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                // 生成文件名称
                String nameSuffix = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."))
                        .replaceAll(" ", "_").replaceAll(",", "") + format.format(new Date())
                        + new Random().nextInt(1000);
                //上传原始图片到阿里云
                String uploadPath = ossFileUtil.uploadAliyun(file,nameSuffix);
                return new JsonResult(true, "上传成功",uploadPath);
            } catch (Exception e) {
                logger.error("上传附件错误" + e.getMessage());
                return new JsonResult(false, "系统未知错误");
            }
        } else {
            return new JsonResult(false, "文件不能为空");
        }
    }
}
