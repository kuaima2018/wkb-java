package com.heima.service;

import com.heima.model.WkbNotation;
import com.heima.model.WkbSchedule;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/16
 */
public interface WkbScheduleService {
    public List<WkbSchedule> queryScheduleByUser(Integer userId,Date beginDate,Date endDate);
    public void editSchedule(WkbSchedule wkbSchedule);
    public void editNotation(WkbNotation wkbNotation,Integer userId, Date scheduleDate);
    public List<WkbSchedule> queryUserRemind(Integer userId,Integer duration);
}
