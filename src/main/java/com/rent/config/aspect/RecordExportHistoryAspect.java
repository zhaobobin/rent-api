package com.rent.config.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.util.BytePictureUtils;
import com.rent.common.constant.RedisKey;
import com.rent.common.dto.backstage.ExportHistory;
import com.rent.common.enums.export.ExportStatus;
import com.rent.common.util.OSSFileUtils;
import com.rent.config.annotation.ExportFile;
import com.rent.config.annotation.ExportWordFile;
import com.rent.config.outside.OutsideConfig;
import com.rent.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 记录导出历史
 *
 * @author zhaowenchao
 */
@Order(3000)
@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class RecordExportHistoryAspect {

    private final OSSFileUtils ossFileUtils;

    public static final String PREFIX = "export";

    @Around("@annotation(com.rent.config.annotation.ExportFile)")
    public void aroundMethod(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature ms = (MethodSignature) jp.getSignature();
        Method method = ms.getMethod();
        ExportFile exportFile = method.getAnnotation(ExportFile.class);

        String fileName = RandomUtil.randomString(36);
        ThreadContextStoreUtil.getInstance().set(fileName, fileName);
        ExportHistory exportHistory = new ExportHistory();
        exportHistory.setFileName(exportFile.fileName().getCode());
        exportHistory.setExportTime(new Date());
        exportHistory.setStatus(ExportStatus.PROCESSING);
        RedisUtil.llSet(RedisKey.EXPORT_HISTORY_PREFIX + LoginUserUtil.getLoginUser().getId(), fileName);
        RedisUtil.hset(RedisKey.EXPORT_HISTORY_KEY, fileName, JSON.toJSONString(exportHistory));
        log.info("【导出文件名称】======>>>>>>" + fileName);

        try {
            List list = (List) jp.proceed();
            if (CollectionUtil.isEmpty(list)) {
                exportHistory.setStatus(ExportStatus.EMPTY);
            } else {
                String savePath = OutsideConfig.TEMP_FILE_DIR+ File.separator + fileName + ".xls";

                if(StringUtil.isNotEmpty(exportFile.mergeIndex())){
                    EasyExcel.write(savePath, exportFile.exportDtoClazz())
                            .sheet()
                            .doWrite(list);
                }else {
                    EasyExcel.write(savePath, exportFile.exportDtoClazz()).sheet().doWrite(list);
                }
                String time = DateUtil.getHyphenDate();
                String oosFileName = UUID.randomUUID().toString().replaceAll("-", "") + savePath.substring(savePath.lastIndexOf("."));
                String url = ossFileUtils.uploadExportFile(PREFIX + "/" + time, savePath,oosFileName);
                log.info("【导出文件URL】======>>>>>>" + fileName);
                exportHistory.setStatus(ExportStatus.SUCCESS);
                exportHistory.setUrl(url);
            }
        } catch (Exception e) {
            exportHistory.setStatus(ExportStatus.FAIL);
            log.error("【导出异常】", e);
            throw e;
        } finally {
            RedisUtil.hset(RedisKey.EXPORT_HISTORY_KEY, fileName, JSON.toJSONString(exportHistory));
        }
    }


    @Around("@annotation(com.rent.config.annotation.ExportWordFile)")
    public void aroundWordMethod(ProceedingJoinPoint jp) throws Throwable {
        MethodSignature ms = (MethodSignature) jp.getSignature();
        Method method = ms.getMethod();
        ExportWordFile exportWordFile = method.getAnnotation(ExportWordFile.class);
        StringBuffer fileName = new StringBuffer();
        fileName.append(exportWordFile.fileName().getCode());
        Object[] methodArgs = jp.getArgs();
        String orderId = null != methodArgs[0] ? String.valueOf(methodArgs[0]) : null;
        if (StringUtil.isNotEmpty(orderId)) {
            int stringlength = orderId.length();
            String newstring = orderId.substring(stringlength - 6, stringlength);
            fileName.append("-").append(newstring);
        }
        String fileNames = fileName.toString();
        ThreadContextStoreUtil.getInstance().set(fileNames, fileNames);
        ExportHistory exportHistory = new ExportHistory();
        exportWordFile.fileType();
        exportHistory.setExportTime(new Date());
        exportHistory.setStatus(ExportStatus.PROCESSING);
        log.info("【导出文件名称】======>>>>>>" + fileNames);
        try {
            Object object = jp.proceed();
            if (null == object) {
                exportHistory.setStatus(ExportStatus.EMPTY);
            } else {
                JSONObject json = (JSONObject) JSON.toJSON(object);
                if (json.containsKey("userName")) {
                    String userName = json.getString("userName");
                    fileNames = fileNames+"-"+userName;
                }
                exportHistory.setFileName(fileNames);
                byte[] data = BytePictureUtils.toByteArray(RecordExportHistoryAspect.class.getClassLoader()
                        .getResourceAsStream(exportWordFile.method().getCode()));

                String savePath = OutsideConfig.TEMP_FILE_DIR+ File.separator;
                byte[] checkReportWord = WordTemplateUtils.replaceText(data, object);
                WordTemplateUtils.getFileByBytes(checkReportWord, savePath, fileNames + ".docx");
                savePath = savePath + fileNames + ".docx";
                String time = DateUtil.getHyphenDate();
                String url = ossFileUtils.uploadExportFile(PREFIX + "/" + time, savePath,fileNames);
                log.info("【导出文件URL】======>>>>>>" + url);
                exportHistory.setStatus(ExportStatus.SUCCESS);
                exportHistory.setUrl(url);
                RedisUtil.llSet(RedisKey.EXPORT_HISTORY_PREFIX + LoginUserUtil.getLoginUser().getId(), fileNames);
                RedisUtil.hset(RedisKey.EXPORT_HISTORY_KEY, fileNames, JSON.toJSONString(exportHistory));
            }
        } catch (Exception e) {
            exportHistory.setStatus(ExportStatus.FAIL);
            log.error("【导出异常】", e);
            throw e;
        }
    }


}
