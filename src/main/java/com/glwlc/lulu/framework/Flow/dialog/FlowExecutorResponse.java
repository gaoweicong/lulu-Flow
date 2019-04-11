package com.glwlc.lulu.framework.Flow.dialog;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Gavin
 * @Date: 2019-02-27 14:29
 */
@Data
@AllArgsConstructor
public class FlowExecutorResponse implements Serializable {
    public static final int BEGIN_STATE = 0;
    public static final int END_STATE = 1;
    public static final int EXCEPTION_STATE = 3;

    // action 的状态
    private int State;
    // 返回给用户的data
    private DialogData dialogData;
    // 下一跳的key值
    private String nextNode;

}
