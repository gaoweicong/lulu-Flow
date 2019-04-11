package com.glwlc.lulu.framework.Flow.loader.action;

import com.glwlc.lulu.framework.Flow.loader.action.model.DefaultActionFlowModel;
import com.glwlc.lulu.framework.Flow.extension.CommonExtension;

import java.util.List;

/**
 * 流程图搜索器, 可扩展redis, mysql, mongodb
 *
 * @Author: Gavin
 * @Date: 2019-02-28 19:43
 */
public interface FlowSearcher extends CommonExtension {

    /**
     * 应该返回一种统一对象
     *
     * @author Gavin
     * @date 2019/2/28 20:02
     * @param []
     */
    public List<DefaultActionFlowModel> search();


}
