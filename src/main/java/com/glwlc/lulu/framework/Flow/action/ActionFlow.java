package com.glwlc.lulu.framework.Flow.action;

/**
 * 流程图各个节点的抽象
 *
 * @Author: Gavin
 * @Date: 2019-02-26 19:26
 */
public interface ActionFlow {

    /**
     * 执行完之后的下一跳
     *
     * @author Gavin
     * @date 2019/2/26 19:36
     * @param []
     */
    public String next(String nextNode);

    /**
     * 执行过程中抛出异常的处理方法
     *
     * @author Gavin
     * @date 2019/2/26 19:36
     * @param []
     */
    public String throwException(String nextNode);
}
