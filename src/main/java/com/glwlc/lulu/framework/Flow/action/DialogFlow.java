package com.glwlc.lulu.framework.Flow.action;

import com.glwlc.lulu.framework.Flow.dialog.DialogData;

/**
 * 对话流
 * 与用户对话, 上报用户行为
 *
 * @Author: Gavin
 * @Date: 2019-02-26 19:27
 */
public interface DialogFlow extends ActionFlow {

    /**
     * 解析并返回数据库中对话
     *
     * @author Gavin
     * @date 2019/2/26 19:57
     * @param []
     */
    public DialogData execute(String dialog);


}
