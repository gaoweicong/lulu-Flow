package com.glwlc.lulu.framework.Flow.action.model.decorator;

import com.glwlc.lulu.framework.Flow.action.ControlFlow;
import com.glwlc.lulu.framework.Flow.action.DecisionTreeFlow;
import com.glwlc.lulu.framework.Flow.dialog.FlowExecutorResponse;
import com.glwlc.lulu.framework.Flow.executor.ExecutorEngine;
import com.glwlc.lulu.framework.Flow.executor.FlowProxyExecutor;
import com.glwlc.lulu.framework.Flow.state.UserState;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 决策树的装饰器
 * 一个flow一个对象
 *
 * @Author: Gavin
 * @Date: 2019-02-27 14:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DecisionTreeFlowDecorator extends FlowCommonDecorator {

    private DecisionTreeFlow decisionTreeFlow;

    private String regular;

    private String nextNodeForTrue;

    private String nextNodeForFalse;


    public DecisionTreeFlowDecorator(String regular, String nextNodeForTrue,
                                     String nextNodeForFalse, String id, String nextNode, String nextNodeForException, String fatherNode) {
        super(id, nextNode, nextNodeForException, fatherNode);
        this.regular = regular;
        this.nextNodeForTrue = nextNodeForTrue;
        this.nextNodeForFalse = nextNodeForFalse;
        init();
    }

    private void init() {
        decisionTreeFlow = ExecutorEngine.generateProxyObject(DecisionTreeFlow.class);
    }

    private Boolean decide(UserState userState) {
        return decisionTreeFlow.decide(regular, userState);
    }

    private String forTrue() {
        return decisionTreeFlow.forTrue(nextNodeForTrue);
    }

    private String forFalse() {
        return decisionTreeFlow.forFalse(nextNodeForFalse);
    }

    public String next() {
        return decisionTreeFlow.next(getNextNode());
    }

    public String throwException() {
        return decisionTreeFlow.throwException(getNextNodeForException());
    }

    /**
     * 执行的模板方法
     *
     * @author Gavin
     * @date 2019/2/28 15:07
     * @param [userState]
     */
    @Override
    public FlowExecutorResponse executeTemplate(UserState userState) {
        String nextNode = null;
        int state = 0;
        try {
            nextNode = decide(userState) ? forTrue() : forFalse();
            state = FlowExecutorResponse.END_STATE;
        } catch (Throwable e) {
            state = FlowExecutorResponse.EXCEPTION_STATE;
            nextNode = throwException();
        }
        return new FlowExecutorResponse(state, null, nextNode);
    }
}
