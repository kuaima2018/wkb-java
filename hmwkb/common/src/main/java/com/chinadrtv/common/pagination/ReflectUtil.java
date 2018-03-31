package com.chinadrtv.common.pagination;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类名称
 * 功能描述：
 * User: leo
 * Date: 13-10-9
 * Time: 下午9:54
 */
public class ReflectUtil {

    private static final Logger logger =LoggerFactory.getLogger("ReflectUtil");


    public static void setFieldValue(Object target, String fname, Class<?> ftype,
                                     Object fvalue) {
        if (target == null
                || fname == null
                || "".equals(fname)
                || (fvalue != null && !ftype
                .isAssignableFrom(fvalue.getClass()))) {
            return;
        }
        Class<? extends Object> clazz = target.getClass();
        try {
            Method method = clazz.getDeclaredMethod("set"
                    + Character.toUpperCase(fname.charAt(0))
                    + fname.substring(1), ftype);
            if (!Modifier.isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }
            method.invoke(target, fvalue);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            try {
                Field field = clazz.getDeclaredField(fname);
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                }
                field.set(target, fvalue);
            } catch (Exception fe) {
                logger.error(fe.getMessage(),fe);
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Class<T> getSuperClassGenericType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if ((index >= params.length) || (index < 0)) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
                    + "'s Parameterized Type: " + params.length);

            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName()
                    + " not set the actual class on superclass generic parameter");

            return Object.class;
        }

        return ((Class) params[index]);
    }
}
