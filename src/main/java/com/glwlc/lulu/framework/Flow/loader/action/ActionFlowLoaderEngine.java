package com.glwlc.lulu.framework.Flow.loader.action;

import com.glwlc.lulu.framework.Flow.action.model.decorator.EventFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.FlowCommonDecorator;
import com.glwlc.lulu.framework.Flow.loader.action.model.DefaultActionFlowModel;
import com.glwlc.lulu.framework.Flow.extension.util.ServiceLoaderUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流程模块加载器
 *
 * @Author: Gavin
 * @Date: 2019-02-28 19:25
 */
public class ActionFlowLoaderEngine implements DefaultFlowLoader{

    // 单例
    private volatile static ActionFlowLoaderEngine actionFlowLoaderEngine = null;
    // 非单例 reload时改变
    private Map<String, EventFlowDecorator> eventFlowDecoratorMap;
    // TODO 非单例 造成内存泄漏
    private FlowSearcher flowSearcher;

    private ActionFlowLoaderEngine() {
    }

    public static ActionFlowLoaderEngine init() {
        synchronized (ActionFlowLoaderEngine.class) {
            //再次检查实例是否存在，如果不存在才真的创建实例
            if (actionFlowLoaderEngine == null)
                actionFlowLoaderEngine = new ActionFlowLoaderEngine();
        }
        return actionFlowLoaderEngine;
    }


    public void config(String type) {
        flowSearcher = ServiceLoaderUtil.load(type, FlowSearcher.class);
    }

    @Override
    public Map<String, EventFlowDecorator> get(){
        if(eventFlowDecoratorMap==null)
            load();
        return eventFlowDecoratorMap;
    }

    /**
     * 简化处理, 所有flow可分成多个event, event的fatherNode是ROOT
     * Event内部的开始节点和结束节点分别是start end
     * 内部有 dataflow dialogflow controlflow decideflow, 内部节点的next不能指向外部节点, 但决策树可由外部节点的
     * 完成情况做判断
     *
     * @author Gavin
     * @date 2019/3/6 19:04
     * @param []
     */
    @Override
    public Map<String, EventFlowDecorator> load() {
        // TODO 增加排序
        if (flowSearcher ==null)
            config(null);
        // 查询
        List<DefaultActionFlowModel> actionFlowModelList = flowSearcher.search();
        // 加载
        List<FlowCommonDecorator> flowDecoratorList = actionFlowModelList.stream().map(
                DefaultActionFlowModel::transfer).collect(Collectors.toList());

        eventFlowDecoratorMap = flowDecoratorList.stream().
                filter(flowDecorator -> flowDecorator instanceof EventFlowDecorator).
                collect(Collectors.toMap(FlowCommonDecorator::getId,
                        flowDecorator -> (EventFlowDecorator) flowDecorator));
        // 如果node的fatherNode查不到,可能有两种情况:
        // 1 根节点root, 不影响
        // 2 father被删除, 孤立节点不可达, 过滤掉
        // 注意 外界的actionFlow只能指向同级的action, 或者首尾节点, 不可跨级指向
        for (FlowCommonDecorator flowCommonDecorator : flowDecoratorList) {
            EventFlowDecorator eventFlowDecorator = eventFlowDecoratorMap.get(flowCommonDecorator.getFatherNode());
            if (eventFlowDecorator!=null)
                eventFlowDecorator.addNode(flowCommonDecorator);
        }
        //TODO 验证节点可达性, 删除不可达节点
        return eventFlowDecoratorMap;
    }

    @Override
    public Map<String, EventFlowDecorator> reload() {
        if (flowSearcher ==null)
            config(null);
        List<DefaultActionFlowModel> actionFlowModelList = flowSearcher.search();
        List<FlowCommonDecorator> flowDecoratorList = actionFlowModelList.stream().map(
                DefaultActionFlowModel::transfer).collect(Collectors.toList());
        Map<String, EventFlowDecorator> map = flowDecoratorList.stream().
                filter(flowDecorator -> flowDecorator instanceof EventFlowDecorator).
                collect(Collectors.toMap(FlowCommonDecorator::getId,
                        flowDecorator -> (EventFlowDecorator) flowDecorator));
        for (FlowCommonDecorator flowCommonDecorator : flowDecoratorList) {
            EventFlowDecorator eventFlowDecorator = map.get(flowCommonDecorator.getFatherNode());
            if (eventFlowDecorator!=null)
                eventFlowDecorator.addNode(flowCommonDecorator);
        }
        //TODO 验证节点可达性, 删除不可达节点
        if (map!=null){
            eventFlowDecoratorMap.clear();
            eventFlowDecoratorMap.putAll(map);
        }
        // TODO 状态机重置根节点
        // TODO 持续思考有没有更好的方法 , 阶段性重置节点
        return eventFlowDecoratorMap;
    }
}
