package com.kjuns.util.qniucloud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * QiniuCloud资源文件属性
 * 在调用 bo 的 save, saveOrUpdate 以及删除一个实体时 自动判断是否需要删除原文件
 * @author  准
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface QiNiuResourceEntity {

	String[] fields();
}
