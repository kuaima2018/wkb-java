package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbNotation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/29
 */
public interface WkbNotationDao extends BaseDao<WkbNotation> {
    List<WkbNotation> queryBySchedule(@Param(value="scheduleIdList")List<Integer> scheduleIdList, @Param(value="type")Integer type);
}
