package com.glwlc.lulu.framework.Flow.action;

import com.glwlc.lulu.framework.Flow.state.UserState;
import com.glwlc.lulu.framework.Flow.dialog.DialogData;

/**
 * 数据流
 * 返回查询某个接口的数据
 *
 * @Author: Gavin
 * @Date: 2019-02-26 19:28
 */
public interface DataFlow extends ActionFlow {

    /**
     * 返回查询某个接口的数据
     *
     * @author Gavin
     * @date 2019/2/26 19:57
     * @param []
     */
    public DialogData execute(String method, UserState userState);
}
