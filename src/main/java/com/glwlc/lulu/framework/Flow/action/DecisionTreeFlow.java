package com.glwlc.lulu.framework.Flow.action;

import com.glwlc.lulu.framework.Flow.state.UserState;

/**
 * 决策树流, 抽象之一
 * 后台根据用户状态机中的某些元素做判断
 *
 * @Author: Gavin
 * @Date: 2019-02-26 19:27
 */
public interface DecisionTreeFlow extends ActionFlow {

    /**
     * 动态代理执行, 加载js执行器
     * 决策方法
     *
     * @author Gavin
     * @date 2019/2/26 19:30
     * @param [regular, userState]
     */
    public Boolean decide(String regular, UserState userState);

    /**
     * decide 返回true调用的方法, 返回event中map的key
     *
     * @author Gavin
     * @date 2019/2/26 19:34
     * @param []
     */
    public String forTrue(String nextNode);

    /**
     * decide 返回false调用的方法, 返回event中map的key
     *
     * @author Gavin
     * @date 2019/2/26 19:35
     * @param []
     */
    public String forFalse(String nextNode);

}
