package com.glwlc.lulu.framework.Flow.controller;

import com.glwlc.lulu.framework.Flow.controller.model.DefaultControllerExecuteResponse;
import com.glwlc.lulu.framework.Flow.extension.CommonExtension;
import com.glwlc.lulu.framework.Flow.loader.action.DefaultFlowLoader;
import com.glwlc.lulu.framework.Flow.state.UserState;

/**
 * 流程控制器
 * 观察者
 * TODO 可基于规则和ai算法
 *
 * @Author: Gavin
 * @Date: 2019-03-06 11:51
 */
public interface FlowController extends CommonExtension {

    /**
     * 对同一对象, 应该串行调用此方法
     *
     * @author Gavin
     * @date 2019/3/6 13:58
     * @param [userState]
     */
    public DefaultControllerExecuteResponse execute(UserState userState);

    /**
     * 加载流程图加载器
     *
     * @author Gavin
     * @date 2019/3/12 10:57
     * @param [defaultFlowLoader]
     */
    public void loadFlowLoader(DefaultFlowLoader defaultFlowLoader);
}
