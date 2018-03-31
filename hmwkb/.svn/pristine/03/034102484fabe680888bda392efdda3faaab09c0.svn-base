package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/16
 */
public interface WkbScheduleDao extends BaseDao<WkbSchedule> {
    List<WkbSchedule> querySchedule(@Param(value="userId")Integer userId,@Param(value="beginDate")Date beginDate,@Param(value="endDate")Date endDate);
    List<WkbSchedule> queryScheduleRemind(@Param(value="userId")Integer userId,@Param(value="beginDate")Date beginDate,@Param(value="endDate")Date endDate,
                                          @Param(value="beginRemindDate")Date beginRemindDate,@Param(value="endRemindDate")Date endRemindDate);
    int updateSchedule(WkbSchedule wkbSchedule);
}
