package com.heima.service;

import com.heima.json.TrackPathJsonResult;
import com.heima.json.TrackPathTLExt;
import com.heima.json.TrackdefsJson;
import com.heima.json.Trackinput;
import com.heima.model.MapTrace;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-18
 * Time: 下午10:57
 * To change this template use File | Settings | File Templates.
 */

public interface MapTraceService {
    void saveMaptrace(MapTrace mapTrace);
    void saveDefaultUser(MapTrace mapTrace);
    List<MapTrace> findByUserId(MapTrace mapTrace);
    void deleteMaptraceByPK(MapTrace mapTrace);

    List<MapTrace> findFrdsMap(Integer usrid);

    List<MapTrace> findFrds(List<Integer> ids);

    TrackPathJsonResult findPathByUsr(Trackinput trackinput);
    List<TrackdefsJson> finddeffrdsusr(Trackinput trackinput);

    List<MapTrace> findLastPathByUsers(List<Integer> uIdList,Date lastDate);


    void calcTracePath(List<TrackPathTLExt> trackPathTLList);

}
