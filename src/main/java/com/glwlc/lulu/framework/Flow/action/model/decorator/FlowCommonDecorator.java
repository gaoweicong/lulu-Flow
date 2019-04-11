package com.glwlc.lulu.framework.Flow.action.model.decorator;

import com.glwlc.lulu.framework.Flow.state.UserState;
import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.dialog.FlowExecutorResponse;

/**
 * @Author: Gavin
 * @Date: 2019-02-28 15:25
 */
public abstract class FlowCommonDecorator {

    private String id;

    private String nextNode;

    private String nextNodeForException;

    private String fatherNode;

    public FlowCommonDecorator() { }

    public FlowCommonDecorator(String id, String nextNode, String nextNodeForException, String fatherNode) {
        this.id = id;
        this.nextNode = nextNode;
        this.nextNodeForException = nextNodeForException;
        this.fatherNode = fatherNode;
    }

    protected abstract String next();

    protected abstract String throwException();

    protected DialogData execute(UserState userState) {
        // 默认空实现
        return null;
    }

    ;

    /**
     * 流的模板方法
     * 没有中间状态
     *
     * @param [userState]
     * @author Gavin
     * @date 2019/2/28 15:09
     */
    public FlowExecutorResponse executeTemplate(UserState userState) {
        String nextNode = null;
        int state = 0;
        DialogData dialogData = null;
        try {
            dialogData = execute(userState);
            nextNode = next();
            state = FlowExecutorResponse.END_STATE;
        } catch (Throwable e) {
            state = FlowExecutorResponse.EXCEPTION_STATE;
            nextNode = throwException();
        }
        return new FlowExecutorResponse(state, dialogData, nextNode);
    }

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

    public String getNextNodeForException() {
        return nextNodeForException;
    }

    public void setNextNodeForException(String nextNodeForException) {
        this.nextNodeForException = nextNodeForException;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(String fatherNode) {
        this.fatherNode = fatherNode;
    }
}
