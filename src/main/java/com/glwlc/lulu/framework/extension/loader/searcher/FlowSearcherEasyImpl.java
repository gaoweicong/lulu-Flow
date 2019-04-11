package com.glwlc.lulu.framework.extension.loader.searcher;

import com.glwlc.lulu.framework.Flow.loader.action.model.DefaultActionFlowModel;
import com.glwlc.lulu.framework.Flow.loader.action.FlowSearcher;

import java.util.ArrayList;
import java.util.List;

import static com.glwlc.lulu.framework.Flow.action.model.constant.ActionFlowTypeConstant.*;

/**
 * @Author: Gavin
 * @Date: 2019-03-01 15:18
 */
public class FlowSearcherEasyImpl implements FlowSearcher {

    private static final String TYPE = "easy-impl";

    @Override
    public List<DefaultActionFlowModel> search() {
        DefaultActionFlowModel m1 = new DefaultActionFlowModel("1", EVENT_FLOW.name(),
                "dictate", "method", "regular", "nextNodeForTrue",
                "nextNodeForFalse", "dialog", "nextNode",
                "throwExceptionNode", "root", "11");
        DefaultActionFlowModel m2 = new DefaultActionFlowModel("11", CONTROL_FLOW.name(),
                "dictate", "method", "regular", "nextNodeForTrue",
                "nextNodeForFalse", "dialog", "nextNode",
                "throwExceptionNode", "1", null);
        DefaultActionFlowModel m3 = new DefaultActionFlowModel("12", DATA_FLOW.name(),
                "dictate", "method", "regular", "nextNodeForTrue",
                "nextNodeForFalse", "dialog", "nextNode",
                "throwExceptionNode", "1", null);
        DefaultActionFlowModel m4 = new DefaultActionFlowModel("13", DECISION_TREE_FLOW.name(),
                "dictate", "method", "regular", "nextNodeForTrue",
                "nextNodeForFalse", "dialog", "nextNode",
                "throwExceptionNode", "1", null);
        DefaultActionFlowModel m5 = new DefaultActionFlowModel("14", DIALOG_FLOW.name(),
                "dictate", "method", "regular", "nextNodeForTrue",
                "nextNodeForFalse", "dialog", "nextNode",
                "throwExceptionNode", "1", null);

        ArrayList<DefaultActionFlowModel> list = new ArrayList<>();
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        list.add(m5);
        return list;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
