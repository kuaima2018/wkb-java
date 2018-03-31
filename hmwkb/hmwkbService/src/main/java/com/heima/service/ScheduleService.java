package com.heima.service;

import com.heima.model.ScheduleModel;
import com.heima.model.UserinfoModel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-20
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
public interface ScheduleService {
    int saveSchedule(ScheduleModel model);
    List<ScheduleModel> queryListByDate(String uid,String startDate,String endDate);
    int updateSchedule(ScheduleModel model);

}
