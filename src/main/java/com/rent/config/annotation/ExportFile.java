package com.rent.config.annotation;


import com.rent.common.enums.export.ExportFileName;

import java.lang.annotation.*;


/**
 * @author zhaowenchao
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExportFile {

    ExportFileName fileName();

    Class exportDtoClazz();

    String mergeIndex() default "";

}
