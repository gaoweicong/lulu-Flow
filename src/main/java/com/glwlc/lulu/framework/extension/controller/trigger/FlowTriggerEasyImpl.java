package com.glwlc.lulu.framework.extension.controller.trigger;

import com.glwlc.lulu.framework.Flow.controller.FlowController;
import com.glwlc.lulu.framework.Flow.controller.FlowTrigger;
import com.glwlc.lulu.framework.Flow.controller.model.DefaultControllerExecuteResponse;
import com.glwlc.lulu.framework.Flow.dialog.DialogData;
import com.glwlc.lulu.framework.Flow.dialog.FlowExecutorResponse;
import com.glwlc.lulu.framework.Flow.loader.user.DefaultUserSearcher;
import com.glwlc.lulu.framework.Flow.state.UserState;
import com.glwlc.lulu.framework.util.DistributeLockUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Gavin
 * @Date: 2019-03-06 16:18
 */
public class FlowTriggerEasyImpl implements FlowTrigger {

    private static final String CONTROLLER_TYPE = "easy-impl";

    private FlowController controller;

    private DefaultUserSearcher defaultUserSearcher;

    private static final String TYPE = "easy-impl";

    /**
     * 1 线程问题: for循环顺序执行, 保证各个controller中各个event顺序执行, 执行结束后再更新数据库
     * 2 进程问题: 分布式锁, 执行后释放锁
     * 3 执行过程中用户数据改变, 此时应先更新数据库的userState, 因为controller只会改变event执行状态,
     * trigger执行结束后更新数据库时只更新了event状态, 不会造成数据不一致
     * 此时trigger再次触发应该循环等待获取锁
     *
     * @param [userState]
     * @author Gavin
     * @date 2019/3/6 16:43
     */
    @Override
    public List<DialogData> trigger(Long id) {
        List<DialogData> responses = new ArrayList<>();
        try {
            if (DistributeLockUtil.lock(id.toString())) {
                UserState userState = defaultUserSearcher.find(id);
                // 每次执行 , userState的event状态都会改变, 但用户数据不会变
                DefaultControllerExecuteResponse execute = controller.execute(userState);
                responses = execute.getDialogDataList();
                // 注意, 此过程最好不触发trigger, 会造成循环触发
                defaultUserSearcher.update(execute.getUserState());
            }
            return responses;
        } finally {
            DistributeLockUtil.release(id.toString());
        }
    }

    @Override
    public void startMonitor() {
        // 启动监视器, 轮寻user变化
        // TODO 推\拉 两种方式
    }

    @Override
    public void loadSearcher(DefaultUserSearcher defaultUserSearcher) {
        this.defaultUserSearcher = defaultUserSearcher;
    }

    @Override
    public void loadController(List<FlowController> controllers) {
        for (FlowController flowController : controllers) {
            if (flowController.getType().equals(CONTROLLER_TYPE)) {
                this.controller = flowController;
                return;
            }
        }
        this.controller = controllers.get(0);
    }

    @Override
    public void reloadController(List<FlowController> controllers) {
       loadController(controllers);
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
