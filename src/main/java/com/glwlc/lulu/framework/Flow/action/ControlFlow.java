package com.glwlc.lulu.framework.Flow.action;

import com.glwlc.lulu.framework.Flow.action.ActionFlow;
import com.glwlc.lulu.framework.Flow.dialog.DialogData;

/**
 * 控制流
 * 控制app打开某个界面
 *
 * @Author: Gavin
 * @Date: 2019-02-26 19:28
 */
public interface ControlFlow extends ActionFlow {

    /**
     * 返回app跳转指令
     *
     * @author Gavin
     * @date 2019/2/26 19:57
     * @param []
     */
    public DialogData execute(String dictate);
}
