package org.lynxz.third_generation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 部分第三方库所需特定activity自动生成注解
 * activity绝对路径分成三部分: {applicationId}.{subPackageName}.{targetActivityName}
 * 并且需要继承指定的父类,记为: {superClass}
 * <p>
 * 比如微信登录功能:
 * 1. subPackageName 为: wxapi
 * 2. targetActivityName 为: WXEntryActivity
 * 3. superClass 为: WXCallbackActivity
 */
@Target(ElementType.TYPE) // 声明注解作用范围是作用在类,接口，注解，枚举上
@Retention(RetentionPolicy.SOURCE) //声明注解的有效期为源码期
public @interface ThirdActivityAutoGenerator {
    /**
     * @return app包名
     */
    String getApplicationId();

    /**
     * @return 子包名, 位于 applicationId 包的下一级
     */
    String getSubPackageName();

    /**
     * @return 要生成的activity名称
     */
    String getTargetActivityName();

    /**
     * @return 生成类的父类
     */
    Class<?> getSupperClass() default Object.class;
}
