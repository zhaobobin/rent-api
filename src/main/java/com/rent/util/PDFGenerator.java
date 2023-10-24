package com.rent.util;


import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.rent.config.outside.OutsideConfig;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * html转pdf
 *
 * @author zhaowenchao
 */
@Slf4j
public class PDFGenerator {

    private static final String templatePrefix = "templates/";
    private static final String templateSuffix = ".html";
    private static final String tempFileDirectory = OutsideConfig.TEMP_FILE_DIR + File.separator;

    /**
     * html转pdf
     *
     * @param pdfFileDirectory 生成的pdf存放的位置
     * @param pdfFileName      生成的pdf存放的位置
     * @param template         模板
     * @param model            模板数据
     * @param objectKey        唯一字符串，存放临时文件，etc 订单id店铺id
     * @throws Exception
     */
    public static String generate(String pdfFileDirectory, String pdfFileName, String template, Map<String, Object> model, String objectKey) throws Exception {
        File tempFileDir = new File(tempFileDirectory);
        if (!tempFileDir.exists()) {
            tempFileDir.setWritable(true, false);
            tempFileDir.mkdirs();
        }

        String tempFilePath = tempFileDirectory + objectKey + ".html";
        File tempFile = new File(tempFilePath);
        if (tempFile.exists()) {
            log.info("临时文件名称重复，请重试：{}", tempFile.getName());
        }
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(templatePrefix);
        resolver.setSuffix(templateSuffix);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        //填充数据
        Context context = new Context();
        for (String key : model.keySet()) {
            context.setVariable(key, model.get(key));
        }
        //渲染模板生成静态
        FileWriter htmlWriter = new FileWriter(tempFilePath);
        templateEngine.process(template, context, htmlWriter);


        //将生成的html文件转换成为pdf文件
        if (tempFile.exists()) {
            Document document = new Document();
            File pdfFileDir = new File(pdfFileDirectory);
            if (!pdfFileDir.exists()) {
                pdfFileDir.setWritable(true, false);
                pdfFileDir.mkdirs();
            }

            XMLWorkerFontProvider provider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            provider.register("fonts/SimHei.ttf");//注册字体
//            SimSun

            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFileDirectory + pdfFileName));
            //打开当前Document
            document.open();
            //为当前Document添加内容：
            XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document,
                    Files.newInputStream(Paths.get(tempFilePath)), null,
                    StandardCharsets.UTF_8, provider);
            document.close();
            tempFile.delete();
            return pdfFileDirectory + pdfFileName;
        } else {
            log.info("临时文件名称重复，请重试");
            return null;
        }
    }

}