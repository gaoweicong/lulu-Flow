package com.glwlc.lulu.framework.Flow.action.model.decorator;

import com.glwlc.lulu.framework.Flow.action.DecisionTreeFlow;
import com.glwlc.lulu.framework.Flow.action.EventFlow;
import com.glwlc.lulu.framework.Flow.action.model.constant.ActionFlowConstant;
import com.glwlc.lulu.framework.Flow.dialog.FlowExecutorResponse;
import com.glwlc.lulu.framework.Flow.executor.ExecutorEngine;
import com.glwlc.lulu.framework.Flow.executor.FlowProxyExecutor;
import com.glwlc.lulu.framework.Flow.state.UserFlowStateElement;
import com.glwlc.lulu.framework.Flow.state.UserState;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * event 他的execute在外部执行, 不用代理
 * 相当于一个容器
 *
 * @Author: Gavin
 * @Date: 2019-02-26 20:15
 */
public class EventFlowDecorator extends FlowCommonDecorator {

    // 各个action节点
    private Map<String, FlowCommonDecorator> nodeMap;

    private EventFlow eventFlow;

    private String firstNodeId;

    // TODO 此处的nextNodeForException表示内部节点执行出现问题执行的节点, 此节点应保持独立
    public EventFlowDecorator(String id, String nextNode, String nextNodeForException, String fatherNode, String firstNodeId) {
        super(id, nextNode, nextNodeForException, fatherNode);
        this.firstNodeId = firstNodeId;
        this.nodeMap = new HashMap<>();
        init();
    }

    private void init() {
        eventFlow = ExecutorEngine.generateProxyObject(EventFlow.class);
    }

    @Override
    protected String next() {
        return eventFlow.next(getNextNode());
    }

    @Override
    protected String throwException() {
        return eventFlow.throwException(getNextNodeForException());
    }

    /**
     * 后台动态更新控制流时使用
     *
     * @param []
     * @author Gavin
     * @date 2019/2/26 20:12
     */
    public void destroy() {
        if (nodeMap != null)
            nodeMap.clear();
    }

    /**
     * 用于判定位置
     *
     * @param [userState]
     * @author Gavin
     * @date 2019/2/28 20:34
     */
    @Override
    // TODO 做死循环测试
    public FlowExecutorResponse executeTemplate(UserState userState) {
        FlowCommonDecorator node = null;
        UserFlowStateElement userFlowStateElement = userState.getUserFlowStateElement();
        if (userFlowStateElement == null)
            return new FlowExecutorResponse(FlowExecutorResponse.EXCEPTION_STATE,
                    null, getNextNodeForException());
        try {
            String actionId = userFlowStateElement.getActionId();
            if (StringUtils.isEmpty(actionId) ||
                    actionId.equals(ActionFlowConstant.START_ACTION_FLOW.name()) ||
                    actionId.equals(ActionFlowConstant.END_ACTION_FLOW.name()))
                // 从firstNode开始执行
                node = getNode(this.firstNodeId);
            else
                node = getNode(actionId);
            if (node != null)
                return node.executeTemplate(userState);
            throw new Throwable();
        } catch (Throwable throwable) {
            if (userFlowStateElement.getActionId().equals(getNextNodeForException()))
                // 这说明执行 exception节点时报错, 不能继续执行了, 否则会死循环
                return new FlowExecutorResponse(FlowExecutorResponse.EXCEPTION_STATE,
                        null, ActionFlowConstant.END_ACTION_FLOW.name());
            // 如果正常报错处理, 则循环调用
            userFlowStateElement.setActionId(getNextNodeForException());
            return executeTemplate(userState);
        }
    }

    public void addNode(FlowCommonDecorator flowCommonDecorator) {
        nodeMap.put(flowCommonDecorator.getId(), flowCommonDecorator);
    }

    public FlowCommonDecorator getNode(String id) {
        return nodeMap.get(id);
    }

}
