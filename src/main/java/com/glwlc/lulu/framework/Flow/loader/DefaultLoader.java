package com.glwlc.lulu.framework.Flow.loader;

import com.glwlc.lulu.framework.Flow.loader.action.DefaultFlowLoader;
import com.glwlc.lulu.framework.Flow.loader.user.DefaultUserSearcher;

/**
 * 根据最小知识原则, 此接口用于controller搜索用户状态
 * LoaderEngine实现了此接口, 类似于装饰器, 可实现searcher的无扰切换
 *
 * @Author: Gavin
 * @Date: 2019-03-06 17:37
 */
public interface DefaultLoader extends DefaultUserSearcher, DefaultFlowLoader {
}
