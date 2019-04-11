package com.glwlc.lulu.framework.extension.loader.searcher;

import com.glwlc.lulu.framework.Flow.loader.user.UserStateSearcher;
import com.glwlc.lulu.framework.Flow.state.UserFlowStateElement;
import com.glwlc.lulu.framework.Flow.state.UserHealthStateElement;
import com.glwlc.lulu.framework.Flow.state.UserState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gavin
 * @Date: 2019-03-06 10:21
 */
public class UserStateSearcherEasyImpl implements UserStateSearcher {

    private static final String TYPE = "easy-impl";

    private static Map<Long, UserState> userMap;

    private void init(){
        List<UserHealthStateElement> array1 = new ArrayList<>();
        array1.add(new UserHealthStateElement("steps", 3476, 1551840946000L));
        array1.add(new UserHealthStateElement("energy", 3476, 1551840946000L));
        array1.add(new UserHealthStateElement("systolic", 80, 1551840946000L));
        array1.add(new UserHealthStateElement("diastolic", 120, 1551840946000L));
        List<UserFlowStateElement> array2 = new ArrayList<>();
        array2.add(new UserFlowStateElement("1","11", 1551840946000L, 1,1));
        array2.add(new UserFlowStateElement("1","11", 1551840946000L, 1,1));
        array2.add(new UserFlowStateElement("1","11", 1551840946000L, 1,1));
        array2.add(new UserFlowStateElement("1","11", 1551840946000L, 1,1));
        UserState userState1 = new UserState();
        userState1.setUserHealthData(array1);
        userState1.setUserStateData(array2);
        userMap.put(1L, userState1);
        UserState userState5 = new UserState();
        userState5.setUserHealthData(array1);
        userState5.setUserStateData(array2);
        userMap.put(5L, userState5);
        UserState userState2 = new UserState();
        userState2.setUserHealthData(array1);
        userState2.setUserStateData(array2);
        userMap.put(2L, userState2);
        UserState userState3 = new UserState();
        userState3.setUserHealthData(array1);
        userState3.setUserStateData(array2);
        userMap.put(3L, userState3);
        UserState userState4 = new UserState();
        userState4.setUserHealthData(array1);
        userState4.setUserStateData(array2);
        userMap.put(4L, userState4);
    }

    @Override
    public UserState find(Long id) {
        if (userMap==null)
            init();
        return userMap.get(id);
    }

    @Override
    public UserState update(UserState userState) {
        userMap.put(userState.getId(), userState);
        return userState;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
