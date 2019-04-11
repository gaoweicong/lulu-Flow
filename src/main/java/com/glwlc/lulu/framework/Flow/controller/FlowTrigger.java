package com.glwlc.lulu.framework.Flow.controller;

import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.extension.CommonExtension;
import com.glwlc.lulu.framework.Flow.loader.user.DefaultUserSearcher;

import java.util.List;

/**
 * TODO
 * 流程触发器, 可以是数据驱动(mq监控用户数据变化) 后台推
 * 也可以是app调用触发(app用户行为触动) 用户拉
 * 被观察者
 * trigger和controller应该是一一对应的
 *
 * @Author: Gavin
 * @Date: 2019-03-06 11:29
 */
public interface FlowTrigger extends CommonExtension {

    /**
     * 触发, 观察者模式
     *
     * @author Gavin
     * @date 2019/3/6 13:46
     * @param [userState]
     */
    public List<DialogData> trigger(Long id);

    /**
     * 开启触发器监控
     *
     * @author Gavin
     * @date 2019/3/6 11:53
     * @param []
     */
    public void startMonitor();

    public void loadSearcher(DefaultUserSearcher defaultUserSearcher);


    /**
     * 加载控制器
     *
     * @author Gavin
     * @date 2019/3/6 15:33
     * @param []
     */
    public void loadController(List<FlowController> controllers);


    public void reloadController(List<FlowController> controllers);
}
