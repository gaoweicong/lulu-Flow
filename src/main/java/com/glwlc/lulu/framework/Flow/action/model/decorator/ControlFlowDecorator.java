package com.glwlc.lulu.framework.Flow.action.model.decorator;

import com.glwlc.lulu.framework.Flow.action.ControlFlow;
import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.executor.ExecutorEngine;
import com.glwlc.lulu.framework.Flow.state.UserState;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 控制器的装饰器
 *
 * @Author: Gavin
 * @Date: 2019-02-28 15:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ControlFlowDecorator extends FlowCommonDecorator {

    private ControlFlow controlFlow;

    private String dictate;

    public ControlFlowDecorator(String dictate, String id, String nextNode, String nextNodeForException, String fatherNode) {
        super(id, nextNode, nextNodeForException, fatherNode);
        this.dictate = dictate;
        init();
    }

    private void init() {
        controlFlow = ExecutorEngine.generateProxyObject(ControlFlow.class);
    }

    @Override
    public DialogData execute(UserState userState){
        // 默认控制指令无状态
        return controlFlow.execute(dictate);
    }

    @Override
    public String next() {
        return controlFlow.next(getNextNode());
    }

    @Override
    public String throwException() {
        return controlFlow.throwException(getNextNodeForException());
    }



}
