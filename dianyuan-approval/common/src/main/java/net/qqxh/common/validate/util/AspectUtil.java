package net.qqxh.common.validate.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author jason
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class AspectUtil {
    private AspectUtil() {
    }

    public static Object[] getArgValues(JoinPoint joinPoint) {
        //参数值
        Object[] argValues = joinPoint.getArgs();
        return argValues;
    }

    public static Object getArgValue(JoinPoint joinPoint, String name) {
        //参数值
        Object[] argValues = getArgValues(joinPoint);
        String[] argNames = getArgNames(joinPoint);
        for (int i = 0; i < argNames.length; i++) {
            String an = argNames[i];
            if (StringUtils.equals(an, name)) {
                return argValues[i];
            }
        }
        return null;
    }

    public static String[] getArgNames(JoinPoint joinPoint) {
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        return argNames;
    }

    /**
     * 判断方法是否包含注解
     *
     * @return
     */
    public static boolean methodHasAnnotation(JoinPoint joinPoint, Class annotationClass) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(annotationClass) != null;
        }
        return false;
    }

    /**
     * 判断类是否包含注解
     *
     * @return
     */
    public static boolean calssHasAnnotation(JoinPoint joinPoint, Class annotationClass) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Method method = methodSignature.getMethod();
        if (method != null) {
            Class clazz = method.getDeclaringClass();
            return clazz.getAnnotation(annotationClass) != null;
        }
        return false;
    }
    /**
     * 判断切点类是否是某个类的子类
     *
     * @return
     */
    public static boolean calssIsExtendsFrom(JoinPoint joinPoint, Class parentClass) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            Class clazz = method.getDeclaringClass();
            return parentClass.isAssignableFrom(clazz);
        }
        return false;
    }
    /**
     * 判断方法是否包含注解
     *
     * @return
     */
    public static boolean isOneMapParam(JoinPoint joinPoint) throws Exception {
        Object[] args = getArgValues(joinPoint);
        if (args.length == 1) {
            return args[1] instanceof Map;
        }
        return false;
    }

    /**
     * 判断方法是否包含注解
     *
     * @return
     */
    public static boolean isOneJsonParam(JoinPoint joinPoint) throws Exception {
        Object[] args = getArgValues(joinPoint);
        if (args.length == 1) {
            return args[1] instanceof JSONObject;
        }
        return false;
    }

}