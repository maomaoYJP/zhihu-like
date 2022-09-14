package com.maomao.zhihu.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author maomao
 * 2022/9/12 14:54
 */
@Component
@Data
public class ossFileUtil {
    @Value("${aliyun.oss.accessKey}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessSecret}")
    private String accessKeySecret;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String uploadAliyun(MultipartFile file, String fileName) throws IOException {
        // 1 获取上传需要的固定值
        String endpoint ="oss-cn-guangzhou.aliyuncs.com";      //你的站点
        String bucketName = "maomao-image";		//你的bucket_name
        //外面获取文件输入流，最后方便关闭
        InputStream in = file.getInputStream();
        try {
            //2 创建OssClient对象
            OSS ossClient =new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
            //3 获取文件信息，为了上传
            // meta设置请求头
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image/jpg");
            //4 设置知道文件夹
            ossClient.putObject(bucketName,fileName,in, meta);
            //5 关闭ossClient
            ossClient.shutdown();
            //6 返回上传之后地址，拼接地址
            String uploadUrl = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return uploadUrl;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            in.close();
        }
    }
}
