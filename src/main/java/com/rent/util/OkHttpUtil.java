package com.rent.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.collections.MapUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhaowenchao
 */
@Slf4j
public class OkHttpUtil {

    private static OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).build();
    private static final MediaType APP_JSON = MediaType.parse("application/json;charset=utf-8");


    public static JSONObject postXFormData(String url, Map<String, String> params) {
        try {
            // 构建请求的Form
            FormBody.Builder builder = new FormBody.Builder();
            // 加入其他参数
            if (MapUtils.isNotEmpty(params)) {
                params.forEach(builder::addEncoded);
            }
            FormBody formBody = builder.build();
            Request request = new Request.Builder().url(url).post(formBody).build();
            Response response = client.newCall(request).execute();
            return JSON.parseObject(response.body().string());
        }catch (Exception e){
            log.error("【请求异常】,url={},e={}",url,e);
            return null;
        }
    }

    /**
     * post请求，发送文件 + form表单
     * @param url
     * @param params
     * @param fileParams
     * @return
     * @throws IOException
     */
    public static JSONObject postFormData(String url, Map<String, String> params, List<FileParam> fileParams) {
        try {
            // 构建请求的Form
            MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            // 加入文件
            if(CollectionUtil.isNotEmpty(fileParams)){
                Map<FileParam, RequestBody> fileBodyMap = fileParams.stream()
                        .collect(Collectors.toMap(fileParam -> fileParam,fileParam -> RequestBody.create(MediaType.parse(fileParam.getMediaType()),fileParam.getFileBytes())));
                // 加入文件body
                fileBodyMap.forEach((fileParam, requestBody) ->
                        formBodyBuilder.addFormDataPart(fileParam.getName(), fileParam.getFilename(), requestBody));
            }
            // 加入其他参数
            if (MapUtils.isNotEmpty(params)) {
                params.forEach(formBodyBuilder::addFormDataPart);
            }
            MultipartBody formBody = formBodyBuilder.build();
            Request request = new Request.Builder().url(url).post(formBody).build();
            Response response = client.newCall(request).execute();
            return JSON.parseObject(response.body().string());
        }catch (Exception e){
            log.error("【请求异常】,url={},e={}",url,e);
            return null;
        }
    }

    /**
     * post请求，发送JSON格式表单
     *
     * @param url
     * @param json 参数的JSON字符串
     * @return
     * @throws IOException
     */
    public static JSONObject postJson(String url, String json) throws IOException{
        RequestBody jsonBody = RequestBody.create(APP_JSON,json);
        Request.Builder builder = new Request.Builder().url(url).post(jsonBody);
        Response response = client.newCall(builder.build()).execute();
        return JSON.parseObject(response.body().string());
    }


    /**
     * 下载文件
     * @param url
     * @param path
     * @throws IOException
     */
    public static void downloadFile(String url,String path){
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            Request request = new Request.Builder().get().url(url).build();
            Response response = client.newCall(request).execute();
            is = response.body().byteStream();
            int len = 0;
            File file  = new File(path);
            fos = new FileOutputStream(file);
            byte[] buf = new byte[128];
            while ((len = is.read(buf)) != -1){
                fos.write(buf, 0, len);
            }
            fos.flush();
        }catch (Exception e){
            log.error("文件下载失败：url：{}",url,e);
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @Data
    @Accessors(chain = true)
    public static class FileParam {
        /**
         * 文件对应的字段名
         */
        private String name;
        /**
         * 文件输入流
         */
        private byte[] fileBytes;
        /**
         * 文件名
         */
        private String filename;
        /**
         * 文件类型
         * 可以参考{@link org.springframework.http.MediaType}
         */
        private String mediaType;
    }
}
