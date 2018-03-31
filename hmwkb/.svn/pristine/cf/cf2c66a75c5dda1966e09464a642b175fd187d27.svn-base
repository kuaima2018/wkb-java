package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbMessageGroup;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-16
 */
public interface WkbMessageGroupDao extends BaseDao<WkbMessageGroup> {
    int removeMessageGroup(Integer id);
    List<WkbMessageGroup> queryByMessageGroup(WkbMessageGroup wkbMessageGroup);
    int modifyMessageGroupValid(@Param(value="id")Integer id,@Param(value="valid")Byte valid,@Param(value="validDate")Date validDate);
}
