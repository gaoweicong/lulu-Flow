package com.glwlc.lulu.framework.Flow.state;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Gavin
 * @Date: 2019-02-26 19:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserState implements Serializable{

    private Long id;

    private UserFlowStateElement userFlowStateElement;

    // UserHealthStateElement 因为是多触发器, 用list保证一致
    private List<UserHealthStateElement> userHealthData;
    // UserFlowStateElement
    private List<UserFlowStateElement> userStateData;

}
