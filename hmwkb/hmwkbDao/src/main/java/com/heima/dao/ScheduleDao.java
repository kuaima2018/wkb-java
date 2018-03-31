package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.ScheduleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-18
 * Time: 下午10:52
 * To change this template use File | Settings | File Templates.
 */
public interface ScheduleDao extends BaseDao<ScheduleModel> {

    public List<ScheduleModel> queryListByDate(@Param("uid")String uid,@Param("startDate")String startDate,@Param("endDate")String endDate);



}
