package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbContactLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xuzhikai on 2015/7/18.
 */
public interface WkbContactLogDao extends BaseDao<WkbContactLog> {
    public int deleteContactLog(@Param(value="contactLogId")Integer contactLogId, @Param(value="userId")Integer userId);
    public List<WkbContactLog> queryLogsByPage(@Param(value="customerId")Integer customerId,@Param(value="userId")Integer userId,@Param(value="startPos")Integer startPos,@Param(value="endPos")Integer endPos);
}
