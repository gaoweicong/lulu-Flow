package com.glwlc.lulu.framework.Flow.controller;

import com.glwlc.lulu.framework.Flow.extension.util.ServiceLoaderUtil;
import com.glwlc.lulu.framework.Flow.loader.DefaultLoader;

import java.util.List;

/**
 * @Author: Gavin
 * @Date: 2019-03-06 11:48
 */
public class ControllerEngine {

    private volatile static ControllerEngine controllerEngine = null;

    private DefaultLoader loader;

    private List<FlowTrigger> triggers;

    private List<FlowController> controllers;



    public static ControllerEngine init(DefaultLoader loader){
        synchronized(ControllerEngine.class) {
            //再次检查实例是否存在，如果不存在才真的创建实例
            if (controllerEngine == null)
                controllerEngine = new ControllerEngine();
            controllerEngine.loader = loader;
        }
        return controllerEngine;
    }

    private ControllerEngine() { }

    public void config(String[] triggerTypes, String[] controllerTypes){
        triggers = ServiceLoaderUtil.load(triggerTypes, FlowTrigger.class);
        controllers = ServiceLoaderUtil.load(controllerTypes, FlowController.class);
        for (FlowController controller : controllers) {
            controller.loadFlowLoader(loader);
        }
        // 已有非空判断
        for (FlowTrigger trigger : triggers) {
            // 加载
            trigger.loadController(controllers);
            trigger.loadSearcher(loader);
            // 启动监听
            trigger.startMonitor();
        }
    }
}
