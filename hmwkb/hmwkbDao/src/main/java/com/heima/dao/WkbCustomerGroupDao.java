package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbCustomerGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
public interface WkbCustomerGroupDao extends BaseDao<WkbCustomerGroup> {
    public List<WkbCustomerGroup> queryAll(Integer userId);
    public int deleteGroup(@Param(value="groupId")Integer groupId,@Param(value="userId")Integer userId);
    public int updateGroup(WkbCustomerGroup wkbCustomerGroup);
}
