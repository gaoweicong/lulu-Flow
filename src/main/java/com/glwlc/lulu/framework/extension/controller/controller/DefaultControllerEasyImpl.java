package com.glwlc.lulu.framework.extension.controller.controller;

import com.glwlc.lulu.framework.Flow.action.model.decorator.EventFlowDecorator;
import com.glwlc.lulu.framework.Flow.controller.FlowController;
import com.glwlc.lulu.framework.Flow.controller.model.DefaultControllerExecuteResponse;
import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.dialog.FlowExecutorResponse;
import com.glwlc.lulu.framework.Flow.loader.action.DefaultFlowLoader;
import com.glwlc.lulu.framework.Flow.state.UserFlowStateElement;
import com.glwlc.lulu.framework.Flow.state.UserState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gavin
 * @Date: 2019-03-12 10:59
 */
public class DefaultControllerEasyImpl implements FlowController {

    private static final String TYPE = "easy-impl";

    private DefaultFlowLoader defaultFlowLoader;

    private Map<String, EventFlowDecorator> flowDecoratorMap;

    @Override
    public DefaultControllerExecuteResponse execute(UserState userState) {
        List<DialogData> dialogDataList = new ArrayList<>();

        if (flowDecoratorMap==null)
            return null;
        List<UserFlowStateElement> userStateData = userState.getUserStateData();

        for (UserFlowStateElement userFlowStateElement : userStateData) {
            EventFlowDecorator eventFlowDecorator = flowDecoratorMap.get(userFlowStateElement.getEventId());
            if (eventFlowDecorator==null)
                continue;
            userState.setUserFlowStateElement(userFlowStateElement);
            FlowExecutorResponse flowExecutorResponse = eventFlowDecorator.executeTemplate(userState);
            // 更新
            userFlowStateElement.setActionId(flowExecutorResponse.getNextNode());
            userFlowStateElement.setState(flowExecutorResponse.getState());
            userFlowStateElement.setUpdateTime(System.currentTimeMillis());
            dialogDataList.add(flowExecutorResponse.getDialogData());
        }
        return new DefaultControllerExecuteResponse(dialogDataList, userState);
    }

    @Override
    public void loadFlowLoader(DefaultFlowLoader defaultFlowLoader) {
        this.defaultFlowLoader = defaultFlowLoader;
        flowDecoratorMap = defaultFlowLoader.get();
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
