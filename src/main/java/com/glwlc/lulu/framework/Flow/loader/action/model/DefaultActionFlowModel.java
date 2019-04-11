package com.glwlc.lulu.framework.Flow.loader.action.model;

import com.glwlc.lulu.framework.Flow.action.model.constant.ActionFlowConstant;
import com.glwlc.lulu.framework.Flow.action.model.decorator.ControlFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.DataFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.DecisionTreeFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.DialogFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.EventFlowDecorator;
import com.glwlc.lulu.framework.Flow.action.model.decorator.FlowCommonDecorator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.glwlc.lulu.framework.Flow.action.model.constant.ActionFlowTypeConstant.CONTROL_FLOW;
import static com.glwlc.lulu.framework.Flow.action.model.constant.ActionFlowTypeConstant.DATA_FLOW;
import static com.glwlc.lulu.framework.Flow.action.model.constant.ActionFlowTypeConstant.DECISION_TREE_FLOW;
import static com.glwlc.lulu.framework.Flow.action.model.constant.ActionFlowTypeConstant.DIALOG_FLOW;
import static com.glwlc.lulu.framework.Flow.action.model.constant.ActionFlowTypeConstant.EVENT_FLOW;

/**
 * @Author: Gavin
 * @Date: 2019-02-28 20:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultActionFlowModel implements Serializable {

    // TODO 唯一标识 UUID??
    private String id;

    private String type;

    private String dictate;

    private String method;

    // 适用于js的规则
    private String regular;

    private String nextNodeForTrue;

    private String nextNodeForFalse;

    private String dialog;

    private String nextNode;

    private String throwExceptionNode;

    private String fatherNode = ActionFlowConstant.ROOT_ACTION_FLOW.name();
    // event 专用
    private String firstNodeId;

    public FlowCommonDecorator transfer() {
        if (EVENT_FLOW.name().equals(type))
            return new EventFlowDecorator(id, nextNode, throwExceptionNode, fatherNode, firstNodeId);
        if (CONTROL_FLOW.name().equals(type))
            return new ControlFlowDecorator(dictate, id, nextNode, throwExceptionNode, fatherNode);
        if (DATA_FLOW.name().equals(type))
            return new DataFlowDecorator(method, id, nextNode, throwExceptionNode, fatherNode);
        if (DECISION_TREE_FLOW.name().equals(type))
            return new DecisionTreeFlowDecorator(regular, nextNodeForTrue, nextNodeForFalse, id, nextNode, throwExceptionNode, fatherNode);
        if (DIALOG_FLOW.name().equals(type))
            return new DialogFlowDecorator(dialog, id, nextNode, throwExceptionNode, fatherNode);

        return null;
    }
}
