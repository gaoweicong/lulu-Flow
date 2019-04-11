package com.glwlc.lulu.framework.Flow.executor;


import com.glwlc.lulu.framework.Flow.action.ControlFlow;
import com.glwlc.lulu.framework.Flow.action.DataFlow;
import com.glwlc.lulu.framework.Flow.action.DialogFlow;
import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.dialog.DictateData;
import com.glwlc.lulu.framework.Flow.dialog.SegmentData;
import com.glwlc.lulu.framework.Flow.state.UserState;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * action的代理对象
 * 一个事件一个对象, 这样可以带属性
 * 此类可作为action对象的加载器, 内部包含执行各个方法的方法体
 *
 * @Author: Gavin
 * @Date: 2019-02-26 18:45
 */
public class FlowProxyExecutor<T> implements InvocationHandler {
    // action 对象
    private Class<T> clazz;

    private DefaultExecutor executorEngine;

    public FlowProxyExecutor(Class<T> clazz, ExecutorEngine executorEngine) {
        this.clazz = clazz;
        this.executorEngine = executorEngine;
    }

    @SuppressWarnings({"unchecked"})
    public T getProxyObject() {
        Class<?>[] interfaces = clazz.getInterfaces();
        if (ArrayUtils.isEmpty(interfaces))
            interfaces = new Class<?>[]{clazz};
        else
            interfaces = ArrayUtils.add(interfaces, clazz);
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        if (name.equals("decide"))
            return decisionTreeDecideProxy(args, method.getReturnType());
        if (name.equals("forTrue"))
            return decisionTreeForTrueProxy(args, method.getReturnType());
        if (name.equals("forFalse"))
            return decisionTreeForFalseProxy(args, method.getReturnType());
        if (name.equals("execute") && clazz.equals(ControlFlow.class))
            return controlFlowExecuteProxy(args, method.getReturnType());
        if (name.equals("execute") && clazz.equals(DataFlow.class))
            return dataFlowExecuteProxy(args, method.getReturnType());
        if (name.equals("execute") && clazz.equals(DialogFlow.class))
            return dialogFlowExecuteProxy(args, method.getReturnType());
        if (name.equals("next"))
            return actionNextProxy(args, method.getReturnType());
        // 此处注意不能用else, 因为返回值不一样, 所以可能会出现死循环
        if (name.equals("throwException"))
            return actionThrowExceptionProxy(args, method.getReturnType());

        // TODO 如果都没有 抛异常
        // TODO 思考如何处理finalize方法
        throw new Throwable();
    }

    private Object decisionTreeDecideProxy(Object[] args, Class<?> returnType) throws Throwable {
        String regular = (String) args[0];
        UserState userState = (UserState) args[1];
        System.out.println("执行" + "decisionTreeDecideProxy");
        boolean flag = executorEngine.executeRegular(regular, userState);
        return returnType.cast(flag);
    }

    private Object decisionTreeForTrueProxy(Object[] args, Class<?> returnType) throws Throwable {
        // TODO 此处可以加拦截器扩展点
        System.out.println("执行" + "decisionTreeForTrueProxy");
        return returnType.cast(args[0]);
    }

    private Object decisionTreeForFalseProxy(Object[] args, Class<?> returnType) throws Throwable {
        // TODO 此处可以加拦截器扩展点
        System.out.println("执行" + "decisionTreeForFalseProxy");
        return returnType.cast(args[0]);
    }

    private Object controlFlowExecuteProxy(Object[] args, Class<?> returnType) throws Throwable {
        // TODO 解析指令
        System.out.println("执行" + "controlFlowExecuteProxy");
        DictateData dictateData = new DictateData((String) args[0]);
        DialogData dialogData = new DialogData();
        dialogData.setDictateData(dictateData);
        return dialogData;
    }

    private Object dataFlowExecuteProxy(Object[] args, Class<?> returnType) throws Throwable {
        // TODO 执行接口内部调用方法
        System.out.println("执行" + "dataFlowExecuteProxy");
        String method = (String) args[0];
        UserState userState = (UserState) args[1];
        return executorEngine.executeMethod(method, userState);
    }

    private Object dialogFlowExecuteProxy(Object[] args, Class<?> returnType) throws Throwable {
        // TODO 解析对话 不需要中间状态
        System.out.println("执行" + "dialogFlowExecuteProxy");
        DialogData dialogData = new DialogData();
        dialogData.setSegmentData(new SegmentData());
        return dialogData;
    }

    private Object actionNextProxy(Object[] args, Class<?> returnType) throws Throwable {
        System.out.println("执行" + "actionNextProxy");
        return returnType.cast(args[0]);
    }

    private Object actionThrowExceptionProxy(Object[] args, Class<?> returnType) {
        // 要在此处处理`异常, 避免在catch中仍然有异常
        try {
            System.out.println("执行" + "actionThrowExceptionProxy");
            return returnType.cast(args[0]);
        } catch (Throwable throwable) {
            return null;
        }
    }

}
