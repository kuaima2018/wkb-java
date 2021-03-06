package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.json.*;
import com.heima.model.MapTrace;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-18
 * Time: 下午10:52
 * To change this template use File | Settings | File Templates.
 */
public interface MapTraceDao extends BaseDao<MapTrace> {
    //List<MapTrace>  findByUsrs(List<Integer> usrList);
    List<MapTrace> queryTracepath(Trackinput trackinput);
    List<MapTrace> queryTraceDefsfrdsusr(Trackinput trackinput);
    List<MapTrace> queryLastMapTraceByUsers(@Param(value="uIdList")List<Integer> uIdList,@Param(value="lastDate")Date lastDate);

}
