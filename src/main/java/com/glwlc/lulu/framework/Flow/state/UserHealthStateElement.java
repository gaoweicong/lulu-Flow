package com.glwlc.lulu.framework.Flow.state;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Gavin
 * @Date: 2019-03-06 10:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHealthStateElement implements Serializable {

    private String name;

    private Object value;

    private Long updateTime;
}
