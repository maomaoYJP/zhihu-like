package com.maomao.zhihu.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author maomao
 * 2022/9/12 14:54
 */
public class ossFileUtil {
    public static String uploadAliyun(MultipartFile file, String fileName) throws IOException {
        // 1 获取上传需要的固定值
        String endpoint ="oss-cn-guangzhou.aliyuncs.com";      //你的站点
        String accessKeyId = "LTAI5tA7EDQXcfZjQdaE8bcG";  //你的acess_key_id
        String accessKeySecret = "xkfXhQllxXfkkd1lOlSFvARRI8pzjc"; //你的acess_key_secret
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
