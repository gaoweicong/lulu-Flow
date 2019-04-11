package com.glwlc.lulu.framework.Flow.loader;

import com.glwlc.lulu.framework.Flow.action.model.decorator.EventFlowDecorator;
import com.glwlc.lulu.framework.Flow.loader.action.ActionFlowLoaderEngine;
import com.glwlc.lulu.framework.Flow.loader.user.UserStateSearcher;
import com.glwlc.lulu.framework.Flow.extension.util.ServiceLoaderUtil;
import com.glwlc.lulu.framework.Flow.state.UserState;

import java.util.Map;

/**
 * 加载器(单例模式)
 * 装饰器
 * @Author: Gavin
 * @Date: 2019-02-28 19:17
 */
public class LoaderEngine implements DefaultLoader {

    private volatile static LoaderEngine loaderEngine = null;

    private UserStateSearcher userStateSearcher;

    private ActionFlowLoaderEngine actionFlowLoaderEngine;


    public static LoaderEngine initLoader(){
        synchronized(LoaderEngine.class) {
            //再次检查实例是否存在，如果不存在才真的创建实例
            if (loaderEngine == null)
                loaderEngine = new LoaderEngine();
        }
        return loaderEngine;
    }

    private LoaderEngine() { }

    private void init(){
        actionFlowLoaderEngine = ActionFlowLoaderEngine.init();
    }

    public LoaderEngine config(String actionFlowSearchType, String userStateSearchType){
        init();
        actionFlowLoaderEngine.config(actionFlowSearchType);
        userStateSearcher = ServiceLoaderUtil.load(userStateSearchType, UserStateSearcher.class);
        return this;
    }
    public LoaderEngine config(){
        // 默认
        return config(null, null);
    }

    @Override
    public Map<String, EventFlowDecorator> load() {
        return actionFlowLoaderEngine.load();
    }

    @Override
    public Map<String, EventFlowDecorator> reload() {
        return actionFlowLoaderEngine.reload();
    }

    @Override
    public Map<String, EventFlowDecorator> get(){
        return actionFlowLoaderEngine.get();
    }

    @Override
    // 装饰方法
    public UserState find(Long id){

        Map<String, EventFlowDecorator> stringEventFlowDecoratorMap = get();
        //TODO 动态更新 userState的 流程状态

        if (userStateSearcher==null)
            config();
        return userStateSearcher.find(id);
    }

    @Override
    // 装饰方法
    public UserState update(UserState userState){

        Map<String, EventFlowDecorator> stringEventFlowDecoratorMap = get();
        // TODO 1 基于最新的eventmap, 增加userstate中没有的, 删除过期的
        // TODO 2 更新只更新eventstate 不更新用户状态

        if (userStateSearcher==null)
            config();
        return userStateSearcher.update(userState);
    }
}
