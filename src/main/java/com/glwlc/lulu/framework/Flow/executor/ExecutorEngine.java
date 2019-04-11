package com.glwlc.lulu.framework.Flow.executor;

import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.extension.util.ServiceLoaderUtil;
import com.glwlc.lulu.framework.Flow.state.UserState;

/**
 * 单例, 装饰器
 * @Author: Gavin
 * @Date: 2019-03-13 11:21
 */
public class ExecutorEngine implements DefaultExecutor {

    private static volatile ExecutorEngine executorEngine;

    private DataExecutor dataExecutor;

    private RegularExecutor regularExecutor;

    public static ExecutorEngine init(){
        synchronized(ExecutorEngine.class) {
            //再次检查实例是否存在，如果不存在才真的创建实例
            if (executorEngine == null)
                executorEngine = new ExecutorEngine();
        }
        return executorEngine;
    }

    private ExecutorEngine() { }

    public void config(String dataExecutorType, String regularExecutorType){
        dataExecutor = ServiceLoaderUtil.load(dataExecutorType, DataExecutor.class);
        regularExecutor = ServiceLoaderUtil.load(regularExecutorType, RegularExecutor.class);
    }

    public static <T> T generateProxyObject(Class<T> clazz){
        if (executorEngine==null)
            init();
        return new FlowProxyExecutor<>(clazz, executorEngine).getProxyObject();
    }

    @Override
    public DialogData executeMethod(String method, UserState userState) {
        // 支持懒加载
        if (dataExecutor==null)
            config(null, null);
        return dataExecutor.executeMethod(method, userState);
    }

    @Override
    public boolean executeRegular(String regular, UserState userState) {
        if (regularExecutor==null)
            config(null, null);
        return regularExecutor.executeRegular(regular, userState);
    }
}
