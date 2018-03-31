package com.heima.service;

import com.heima.model.WkbMessageGroupUser;
import com.heima.model.WkbUser;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-17
 */
public interface WkbMessageGroupUserService {
    public int getGroupCount(Integer groupId,Integer groupType,Integer userId);
    public List<WkbUser> queryGroupUsers(Integer groupId,Integer groupType,Integer userId,Integer index,Integer pageSize);
    public void deleteUserByGroup(Integer groupId);
    public void addUser(Integer groupId,List<Integer> userIdList,Integer userId);
    public void deleteUser(Integer groupId,List<Integer> userIdList);
}
