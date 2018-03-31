package com.heima.service;

import com.heima.model.WkbMessageGroup;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-16
 */
public interface WkbMessageGroupService {
    public void addMessageGroup(WkbMessageGroup wkbMessageGroup);
    public void deleteMessageGroup(Integer groupId,Integer userId);
    public List<WkbMessageGroup> getUserGroups(Integer userId);
    public WkbMessageGroup getGroup(Integer groupId,Integer userId);
}
