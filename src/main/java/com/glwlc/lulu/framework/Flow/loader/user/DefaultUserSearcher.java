package com.glwlc.lulu.framework.Flow.loader.user;

import com.glwlc.lulu.framework.Flow.state.UserState;


public interface DefaultUserSearcher {

    public UserState find(Long id);

    /**
     * TODO 此处可增加异步触发流程
     *
     * @author Gavin
     * @date 2019/3/6 17:55
     * @param [userState]
     */
    public UserState update(UserState userState);

}
