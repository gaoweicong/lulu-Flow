package com.glwlc.lulu.framework.Flow.loader.action;

import com.glwlc.lulu.framework.Flow.action.model.decorator.EventFlowDecorator;

import java.util.Map;

/**
 * @Author: Gavin
 * @Date: 2019-03-06 18:06
 */
public interface DefaultFlowLoader {

    public Map<String, EventFlowDecorator> load();

    public Map<String, EventFlowDecorator> reload();

    public Map<String, EventFlowDecorator> get();
}
