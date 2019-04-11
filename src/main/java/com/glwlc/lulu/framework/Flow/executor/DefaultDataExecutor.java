package com.glwlc.lulu.framework.Flow.executor;

import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.state.UserState;

/**
 * @Author: Gavin
 * @Date: 2019-03-13 11:33
 */
public interface DefaultDataExecutor {
    public DialogData executeMethod(String method, UserState userState);
}
