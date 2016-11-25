package com.qhn.bhne.footprinting.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by qhn
 * on 2016/10/26 0026.
 *
 * @Retention: 表示需要在什么级别保存该注解信息。
 * 可用RetentionPolicy枚举类型主要有：
 * SOURCE: 注解将被编译器丢弃。
 * CLASS  :  注解在class文件中可能。但会被VM丢弃。
 * RUNTIME: VM将在运行时也保存注解(如果需要通过反射读取注解，则
 * 使用该值)。
 * @Documented: 将此注解包含在Javadoc中
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)//表示该annotation的具体实现可以在运行时用类反射来实现
public @interface PerActivity {
}
