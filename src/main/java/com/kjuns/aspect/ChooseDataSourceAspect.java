package com.kjuns.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.kjuns.annotation.ChooseDataSource;
import com.kjuns.util.DataSourceKeyHolder;
import com.kjuns.util.ReflectUtil;


/**
 * <li>类描述：完成数据源的切换，抽类切面，具体项目继承一下，不需要重写即可使用</li>
 */
@Aspect
@Component
public class ChooseDataSourceAspect {

    protected static final ThreadLocal<String> preDatasourceHolder = new ThreadLocal<String>();

//    @Pointcut("@annotation(com.kjuns.annotation.ChooseDataSource)")
//    public void methodWithChooseAnnotation() {
//    	
//    }
//    	
//    @Before("execution(* com.kjuns.service.impl.*ServiceImpl.*(..)) || execution(* com.kjuns.mapper.*Mapper.*(..)) ")
    @Before("execution(* com.kjuns.mapper.*Mapper.*(..))")
    public void chooseAnnotation(JoinPoint joinPoint) throws Exception {
	    //拿到anotation中配置的数据源
	    String resultDS = determineDatasource(joinPoint);
	    //没有配置实用默认数据源
	    if (resultDS == null) {
		    DataSourceKeyHolder.setDataSourceKey(null);
		    return;
	    }
	    preDatasourceHolder.set(DataSourceKeyHolder.getDataSourceKey());
	    //将数据源设置到数据源持有者
	    DataSourceKeyHolder.setDataSourceKey(resultDS);
    }

//    /**
//     * 根据@ChooseDataSource的属性值设置不同的dataSourceKey,以供DynamicDataSource
//     */
//    @Before("methodWithChooseAnnotation() && @annotation(chooseDataSource)")
//    public void changeDataSourceBeforeMethodExecution(JoinPoint jp, ChooseDataSource chooseDataSource) {
//        //拿到anotation中配置的数据源
//        String resultDS = chooseDataSource.value();
//        //没有配置实用默认数据源
//        if (resultDS == null) {
//            DataSourceKeyHolder.setDataSourceKey(null);
//            return;
//        }
//        preDatasourceHolder.set(DataSourceKeyHolder.getDataSourceKey());
//        //将数据源设置到数据源持有者
//        DataSourceKeyHolder.setDataSourceKey(resultDS);
//    }
//
//    /**
//     * 如果需要修改获取数据源的逻辑，请重写此方法
//     */
    @SuppressWarnings("rawtypes")
    protected String determineDatasource(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Class targetClass = jp.getSignature().getDeclaringType();
        String dataSourceForTargetClass = resolveDataSourceFromClass(targetClass);
        String dataSourceForTargetMethod = resolveDataSourceFromMethod(targetClass, methodName);
        String resultDS = determinateDataSource(dataSourceForTargetClass, dataSourceForTargetMethod);
        return resultDS;
    }

    /**
     * 方法执行完毕以后，数据源切换回之前的数据源。
     */
    @After("execution(* com.kjuns.mapper.*Mapper.*(..))")
    public void restoreDataSourceAfterMethodExecution() {
        DataSourceKeyHolder.setDataSourceKey(preDatasourceHolder.get());
        preDatasourceHolder.remove();
    }

    @SuppressWarnings("rawtypes")
    private String resolveDataSourceFromMethod(Class targetClass, String methodName) {
        Method m = ReflectUtil.findUniqueMethod(targetClass, methodName);
        if (m != null) {
            ChooseDataSource choDs = m.getAnnotation(ChooseDataSource.class);
            return resolveDataSourcename(choDs);
        }
        return null;
    }

    private String determinateDataSource(String classDS, String methodDS) {
        // 两者必有一个不为null,如果两者都为Null，也会返回Null
        return methodDS == null ? classDS : methodDS;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private String resolveDataSourceFromClass(Class targetClass) {
        ChooseDataSource classAnnotation = (ChooseDataSource) targetClass.getAnnotation(ChooseDataSource.class);
        // 直接为整个类进行设置
        return null != classAnnotation ? resolveDataSourcename(classAnnotation): null;
    }

    private String resolveDataSourcename(ChooseDataSource ds) {
        return ds == null ? null : ds.value();
    }

}
