package com.huawei.nlz.snippets.cbb.map2bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于Java8的内省机制实现的一个Bean与Map互转的工具类。
 */
public class ConvertUtils {

    /**
     * Map转bean
     *
     * @param params 属性值Map
     * @param clz    bean类型
     * @param <T>    bean类型
     * @return bean对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws IntrospectionException
     * @throws InvocationTargetException
     */
    public static <T> T map2Bean(Map<String, Object> params, Class<T> clz)
            throws IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {
        T obj = clz.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(clz, Object.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propName = propertyDescriptor.getName();
            if (params.containsKey(propName)) {
                Object propVal = params.get(propName);
                Object[] args = {propVal};
                propertyDescriptor.getWriteMethod().invoke(obj, args);
            }
        }

        return obj;
    }

    /**
     * bean转Map
     *
     * @param bean bean
     * @param <T>  bean类型
     * @return Map
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> Map<String, Object> bean2Map(T bean) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> resultMap = new HashMap<>(16);

        Class clz = bean.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(clz, Object.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String key = propertyDescriptor.getName();
            Method readMethod = propertyDescriptor.getReadMethod();
            Object value = readMethod.invoke(bean, new Object[0]);
            resultMap.put(key, value);
        }

        return resultMap;
    }

}
