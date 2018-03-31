package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
public interface WkbCustomerDao extends BaseDao<WkbCustomer> {
    public int updateCustomer(WkbCustomer wkbCustomer);
    public WkbCustomer getCustomer(Integer customerId);
    public int deleteCustomer(@Param(value="customerId")Integer customerId,@Param(value="userId")Integer userId);
    public int updateCustomersGroup(@Param(value="orgGroupId")Integer orgGroupId,@Param(value="newGroupId")Integer newGroupId,@Param(value="userId")Integer userId);
    public List<WkbCustomer> queryCustomersByPage(@Param(value="userId")Integer userId,@Param(value="groupId")Integer groupId,@Param(value="orderByCol")String orderByCol,@Param(value="startPos")Integer startPos,@Param(value="endPos")Integer endPos);
}
