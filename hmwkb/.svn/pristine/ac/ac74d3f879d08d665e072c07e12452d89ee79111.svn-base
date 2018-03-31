package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbMessageGroupUser;
import com.heima.model.WkbUser;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-16
 */
public interface WkbMessageGroupUserDao extends BaseDao<WkbMessageGroupUser> {
    public Integer queryCountByGroup(Integer groupId);
    public List<WkbUser> queryUserByGroup(@Param(value="groupId")Integer groupId,@Param(value="index")Integer index,@Param(value="pageSize")Integer pageSize);
    public int modifyUserValidByGroup(@Param(value="groupId")Integer groupId,@Param(value="valid")Byte valid,@Param(value="validDate")Date validDate);
    public int removeUserByGroup(Integer groupId);
    public List<WkbMessageGroupUser> queryUser(@Param(value="groupId")Integer groupId,@Param(value="userIdList")List<Integer> userIdList);
    public int removeUser(@Param(value="groupId")Integer groupId,@Param(value="userIdList")List<Integer> userIdList);
    public int modifyUserValid(@Param(value="groupId")Integer groupId,@Param(value="userIdList")List<Integer> userIdList,@Param(value="valid")Byte valid,@Param(value="validDate")Date validDate);
}
