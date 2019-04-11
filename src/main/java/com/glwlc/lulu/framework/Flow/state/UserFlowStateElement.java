package com.glwlc.lulu.framework.Flow.state;

import com.glwlc.lulu.framework.Flow.action.model.constant.ActionFlowConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Gavin
 * @Date: 2019-03-12 14:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFlowStateElement implements Serializable {

    private String eventId;
    // 默认初始节点
    private String actionId = ActionFlowConstant.START_ACTION_FLOW.name();

    private Long updateTime;

    private int state = 0;
    // 用于对话流用户选择 后接决策树
    private int currentChoice;

}
