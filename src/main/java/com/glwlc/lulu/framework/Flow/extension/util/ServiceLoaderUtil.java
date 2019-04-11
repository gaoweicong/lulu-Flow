package com.glwlc.lulu.framework.Flow.extension.util;

import com.glwlc.lulu.framework.Flow.extension.CommonExtension;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * spi springboot 两种加载模式
 * 默认spi
 * @Author: Gavin
 * @Date: 2019-03-01 10:51
 */
@Component
public class ServiceLoaderUtil implements ApplicationContextAware {

    private static ServiceLoaderUtil serviceLoaderUtil;

    private static ApplicationContext applicationContext;

    @Value("${extension.tool}")
    private static String loadTool;

    public static<T> T load (String type, Class<T> clazz){
        if (!ArrayUtils.contains(clazz.getInterfaces(), CommonExtension.class))
            return null;
        Iterator<T> values = getIterator(clazz);
        T result = null;
        if (!values.hasNext())
            return null;
        else
            do  {
                T loader = values.next();
                if (result==null)
                    result = loader;
                if (((CommonExtension)loader).getType().equals(type))
                    return loader;
            }while ((values.hasNext()));
        return result;
    }

    public static<T> List<T> load (String[] type, Class<T> clazz){
        if (!ArrayUtils.contains(clazz.getInterfaces(), CommonExtension.class))
            return new ArrayList<T>();
        List<T> results = new ArrayList<>();
        Iterator<T> values = getIterator(clazz);
        if (!values.hasNext())
            return results;
        else
            do  {
                T loader = values.next();
                if (ArrayUtils.isEmpty(type) || ArrayUtils.contains(type, ((CommonExtension)loader).getType()))
                    results.add(loader);
            }while ((values.hasNext()));
        return results;
    }

    @Override
    public void setApplicationContext( ApplicationContext applicationContext) throws BeansException {
        if(ServiceLoaderUtil.applicationContext == null)
            ServiceLoaderUtil.applicationContext = applicationContext;
        ServiceLoaderUtil.serviceLoaderUtil = applicationContext.getBean(ServiceLoaderUtil.class);
    }

    private static <T> Iterator<T> getIterator(Class<T> clazz) {
        Iterator<T> values;
        if (StringUtils.isEmpty(loadTool) || "spi".equals(loadTool))
            values = ServiceLoader.load(clazz).iterator();
        else
            values = applicationContext.getBeansOfType(clazz).values().iterator();
        return values;
    }

}
