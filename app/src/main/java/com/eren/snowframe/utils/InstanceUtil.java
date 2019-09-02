package com.eren.snowframe.utils;

import java.lang.reflect.ParameterizedType;

/**
 * @author Eren
 * <p>
 * 实例化相应类
 */
public class InstanceUtil {

    /**
     * @param <T> 返回实例的泛型类型
     */
    public static <T> T getInstance(Object o, int i) {
        try {
            return (T) ((Class) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
