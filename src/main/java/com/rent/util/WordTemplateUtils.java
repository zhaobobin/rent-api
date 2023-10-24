package com.rent.util;

import com.deepoove.poi.XWPFTemplate;
import com.rent.exception.HzsxBizException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author xiaoyao
 * @version V1.0
 * @since 2020/08/13 9:39
 */
@Slf4j
public class WordTemplateUtils {

    public static byte[] replaceText(byte[] wordTemplateBytes, Object model) throws HzsxBizException {
        InputStream inputStream = new ByteArrayInputStream(wordTemplateBytes);
        try (
            XWPFTemplate template = XWPFTemplate.compile(inputStream).render(model);
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            template.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new HzsxBizException("000800", "替换word模板异常",WordTemplateUtils.class, e);
        }
    }


    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
        File file = new File(filePath + File.separator + fileName);
        try (FileOutputStream fos = new FileOutputStream(file); BufferedOutputStream bos = new BufferedOutputStream(
                fos)) {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
