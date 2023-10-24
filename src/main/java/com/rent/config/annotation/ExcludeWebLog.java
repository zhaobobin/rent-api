package com.rent.config.annotation;


import java.lang.annotation.*;


/**
 * @author zhaowenchao
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcludeWebLog {


}
