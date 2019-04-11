package com.glwlc.lulu.framework.Flow.action.model.decorator;

import com.glwlc.lulu.framework.Flow.action.DialogFlow;
import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.executor.ExecutorEngine;
import com.glwlc.lulu.framework.Flow.state.UserState;

/**
 * TODO 触发userState update的时候要更新对应的UserFlowStateElement的currentChoice
 * 然后继续触发流程
 * @Author: Gavin
 * @Date: 2019-02-28 15:52
 */
public class DialogFlowDecorator extends FlowCommonDecorator {

    private DialogFlow dialogFlow;

    private String dialog;

    public DialogFlowDecorator(String dialog, String id, String nextNode, String nextNodeForException, String fatherNode) {
        super(id, nextNode, nextNodeForException, fatherNode);
        this.dialog = dialog;
        init();
    }

    private void init() {
        dialogFlow = ExecutorEngine.generateProxyObject(DialogFlow.class);
    }

    @Override
    protected String next() {
        return dialogFlow.next(getNextNode());
    }

    @Override
    protected String throwException() {
        return dialogFlow.throwException(getNextNodeForException());
    }

    @Override
    protected DialogData execute(UserState userState) {
        return dialogFlow.execute(dialog);
    }
}
