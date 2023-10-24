package com.rent.util;

import cn.hutool.core.codec.Base64;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author netkiller
 */
public class FileUtils {

    private static final String JPG = "jpg";

    /**
     * 把PDF文件转成JPG图片，再获取JPG图片Base64编码
     */
    public static String image2JpgBase64(File image) {
        // 将pdf装图片 并且自定义图片得格式大小
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            BufferedImage bufferedImage = ImageIO.read(image);
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.white, null);
            ImageIO.write(newBufferedImage, JPG, stream);
            String base64 = Base64.encode(stream.toByteArray());
            return base64;
        } catch (IOException e) {
            return null;
        }
    }

    public static String getFileMD5(File file) {
        try {
            BASE64Encoder be = new BASE64Encoder();
            byte[] bytes = new byte[(int)file.length()];
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            inputStream.close();
            return be.encode(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把PDF文件转成Base64编码
     */
    public static String pdf2Base64(File pafFile) {
        // 将pdf装图片 并且自定义图片得格式大小
        try (InputStream in = new FileInputStream(pafFile)){
            byte[] data = new byte[in.available()];
            in.read(data);
            String base64 = Base64.encode(data);
            return base64;
        } catch (IOException e) {
            return null;
        }
    }

}
