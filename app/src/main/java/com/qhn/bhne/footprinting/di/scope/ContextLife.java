package com.qhn.bhne.footprinting.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by qhn
 * on 2016/10/27 0027.
 * 该注释的含义为 所注释的对象必须Application
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextLife {
    String value() default  "Application";
}
