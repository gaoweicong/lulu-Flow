package com.glwlc.lulu.framework.Flow.controller.model;

import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.state.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Gavin
 * @Date: 2019-03-06 17:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultControllerExecuteResponse implements Serializable {

    private List<DialogData> dialogDataList;

    private UserState userState;
}
