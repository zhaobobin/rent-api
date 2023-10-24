package com.rent.config.annotation;

import com.rent.common.enums.export.ExportFileName;
import com.rent.common.enums.export.TypeFileName;
import com.rent.common.enums.export.WordExportMethods;

import java.lang.annotation.*;


/**
 * @author zhaowenchao
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExportWordFile {

    ExportFileName fileName();

    Class exportDtoClazz();

    TypeFileName fileType();

    WordExportMethods method();








}
