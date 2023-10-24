package com.rent.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.rent.common.properties.OssProperties;
import com.rent.config.outside.OutsideConfig;
import com.rent.exception.HzsxBizException;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OSSFileUtils {

    private final OssProperties ossProperties;

    /**
     * 上传用户上传的文件
     * @param file
     * @param prefix
     * @return
     */
    public String uploadByMultipartFile(MultipartFile file,String prefix) {
        if (file == null) {
            throw new HzsxBizException("-1","上传文件不能为空");
        }
        try {
            String fileName = file.getOriginalFilename();
            String time = DateUtil.getHyphenDate();
            OSSClient oss = getClient();
            String separator = "/";
            String oosFileName = prefix+separator+time+separator+UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));
            oss.putObject(ossProperties.getBucketName(), oosFileName, file.getInputStream());
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
            oss.generatePresignedUrl(ossProperties.getBucketName(), oosFileName, expiration);
            oss.shutdown();
            return ossProperties.getFileUrlPrefix() + oosFileName;
        } catch (IOException e) {
            throw new HzsxBizException("-1","获取文件流异常~");
        }
    }

    /**
     * 上传文件
     * @param prefix 上传到OSS的目录
     * @param filePath 本地文件地址
     * @param fileName 上传到OSS的文件名称
     * @return
     */
    public String uploadFile(String prefix,String filePath,String fileName) {
        return uploadFile(prefix,filePath,fileName,3600L * 1000 * 24 * 365 * 100);
    }

    /**
     * 上传导出的文件
     * @param prefix 上传到OSS的目录
     * @param filePath 本地文件地址
     * @param fileName 上传到OSS的文件名称
     * @return
     */
    public String uploadExportFile(String prefix,String filePath,String fileName) {
        return uploadFile(prefix,filePath,fileName,3600L * 1000 * 24 * 3);
    }

    /**
     * 下载文件
     * @param url 文件访问路径
     * @return
     * @throws IOException
     */
    public File downImgUrl(String url) throws IOException {
        String objectKey = getObjectKey(url);
        OSSClient ossClient = getClient();
        String date = DateUtil.getNowDay();
        File file = new File(OutsideConfig.TEMP_FILE_DIR + File.separator + date + File.separator + objectKey);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        ossClient.getObject(new GetObjectRequest(ossProperties.getBucketName(), objectKey), file);
        return file;
    }


    private OSSClient getClient(){
        return new OSSClient(ossProperties.getEndpoint(), ossProperties.getAccessId(),ossProperties.getAccessKey());
    }


    public String getObjectKey(String url) {
        return url.replaceAll(ossProperties.getFileUrlPrefix(), "");
    }

    public String getPrefix() {
        return ossProperties.getFileUrlPrefix();
    }

    private String uploadFile(String prefix,String filePath,String fileName,Long expireTime) {
        OSSClient ossClient = getClient();
        File file = new File(filePath);
        fileName = prefix + File.separator +fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), fileName,file);
        ossClient.putObject(putObjectRequest);
        Date expiration = new Date(System.currentTimeMillis() + expireTime);
        ossClient.generatePresignedUrl(ossProperties.getBucketName(), fileName, expiration);
        ossClient.shutdown();
        file.delete();
        return ossProperties.getFileUrlPrefix() + fileName;
    }
}
