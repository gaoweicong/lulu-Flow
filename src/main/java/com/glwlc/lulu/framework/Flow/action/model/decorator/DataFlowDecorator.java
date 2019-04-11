package com.glwlc.lulu.framework.Flow.action.model.decorator;

import com.glwlc.lulu.framework.Flow.action.DataFlow;
import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.executor.ExecutorEngine;
import com.glwlc.lulu.framework.Flow.state.UserState;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据流的装饰器类
 *
 * @Author: Gavin
 * @Date: 2019-02-28 15:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataFlowDecorator extends FlowCommonDecorator {

    private DataFlow dataFlow;

    private String method;

    public DataFlowDecorator(String method, String id, String nextNode, String nextNodeForException, String fatherNode) {
        super(id, nextNode, nextNodeForException, fatherNode);
        this.method = method;
        init();
    }

    private void init() {
        dataFlow = ExecutorEngine.generateProxyObject(DataFlow.class);
    }

    @Override
    public DialogData execute(UserState userState){
        return dataFlow.execute(method, userState);
    }

    @Override
    public String next() {
        return dataFlow.next(getNextNode());
    }

    @Override
    public String throwException() {
        return dataFlow.throwException(getNextNodeForException());
    }

}
