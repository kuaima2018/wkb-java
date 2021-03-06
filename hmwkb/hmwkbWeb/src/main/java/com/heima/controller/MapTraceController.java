package com.heima.controller;

import com.heima.common.WktResult;
import com.heima.common.WktStatus;
import com.heima.json.*;
import com.heima.model.*;
import com.heima.service.MapTraceService;
import com.heima.service.WkbUserService;
import com.heima.service.biz.WkbBizException;
import com.heima.web.service.SystemConfigure;
import com.heima.web.util.ListHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-18
 * Time: 下午11:01
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping({"/location"})
public class MapTraceController extends BaseController{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MapTraceController.class);
    @Autowired
    private MapTraceService mapTraceService;
    @Autowired
    private WkbUserService wkbUserService;
    @Autowired
    private SystemConfigure systemConfigure;

    @RequestMapping(value = "/trackAdd")
    @ResponseBody
    public WktStatusResult addMapTrace(@RequestBody JsonTrackQuery jsonTrackQuery)
    {
        WktStatus wktStatus=new WktStatus();
        if(jsonTrackQuery==null||jsonTrackQuery.getUserId()==null||jsonTrackQuery.getMlalo()==null)
        {
            wktStatus.setErrorCode(1);
            wktStatus.setErrorMessage("未提供参数");
            return new WktStatusResult(wktStatus);
        }

        try
        {
            MapTrace mapTrace=new MapTrace();
            Calendar cal = Calendar.getInstance();
            mapTrace.setUid(jsonTrackQuery.getUserId());
            mapTrace.setmLalo(jsonTrackQuery.getMlalo());
            mapTrace.setmTraceDate(cal.getTime());
            mapTrace.setCrtDatetime(cal.getTime());
            mapTrace.setCreator(String.valueOf(jsonTrackQuery.getUserId()));
            mapTraceService.saveMaptrace(mapTrace);
        }catch (WkbBizException wkbExp)
        {
            logger.error("addMapTrace error:"+wkbExp.getCode(), wkbExp);
            wktStatus.setErrorCode(-100);
            wktStatus.setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("addMapTrace unkown error:",exp);
            wktStatus.setErrorCode(-200);
            wktStatus.setErrorMessage(exp.getMessage());
        }

        return new WktStatusResult(wktStatus);
    }

    @RequestMapping(value = "/trackPath")
    @ResponseBody
    public WktResult trackPath(@RequestBody JsonTrackQuery jsonTrackQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonTrackQuery!=null&&jsonTrackQuery.getTargetId()==null)
        {
            jsonTrackQuery.setTargetId(jsonTrackQuery.getUserId());
        }
        if(jsonTrackQuery==null||jsonTrackQuery.getTargetId()==null||jsonTrackQuery.getDate()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供参数");
            return wktResult;
        }

        try
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Trackinput trackinput=new Trackinput();
            trackinput.setUid(jsonTrackQuery.getTargetId());
            trackinput.setTime(simpleDateFormat.parse(jsonTrackQuery.getDate()));
            TrackPathJsonResult trackPathJsonResult = mapTraceService.findPathByUsr(trackinput);

            wktResult.setResult(trackPathJsonResult.getMlalos());
        }
        catch (WkbBizException wkbExp)
        {
            logger.error("trackPath error:"+wkbExp.getCode(), wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("trackPath unkown error:", exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;
    }

    @RequestMapping(value = "/trackDefs")
    @ResponseBody
    public WktResult  trackdefs(@RequestBody JsonTrackQuery jsonTrackQuery)
    {
        WktResult wktResult=new WktResult();
        if(jsonTrackQuery==null||jsonTrackQuery.getDate()==null)
        {
            wktResult.getStatus().setErrorCode(1);
            wktResult.getStatus().setErrorMessage("未提供参数");
            return wktResult;
        }

        List<Integer> idList= ListHelper.parseStr(jsonTrackQuery.getStrTargetIds());
        if(idList!=null&&idList.size()>0)
        {
            jsonTrackQuery.setTargetIds(idList);
        }
        if(jsonTrackQuery.getTargetIds()==null||jsonTrackQuery.getTargetIds().size()==0)
        {
            wktResult.getStatus().setErrorCode(2);
            wktResult.getStatus().setErrorMessage("未提供参数");
            return wktResult;
        }

        try
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date lastDate=simpleDateFormat.parse(jsonTrackQuery.getDate());
            List<MapTrace> mapTraceList=mapTraceService.findLastPathByUsers(jsonTrackQuery.getTargetIds(),lastDate);

            wktResult.setResult(convertFromMaptraces(mapTraceList));
        }catch (WkbBizException wkbExp)
        {
            logger.error("trackdefs error:"+wkbExp.getCode(), wkbExp);
            wktResult.getStatus().setErrorCode(-100);
            wktResult.getStatus().setErrorMessage(this.getBizError(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            logger.error("trackdefs unkown error:",exp);
            wktResult.getStatus().setErrorCode(-200);
            wktResult.getStatus().setErrorMessage(exp.getMessage());
        }
        return wktResult;

    }

    private List<JsonUserTrackdefs> convertFromMaptraces(List<MapTrace> mapTraceList)
    {
        List<JsonUserTrackdefs> jsonUserTrackdefsList=new ArrayList<JsonUserTrackdefs>();
        if(mapTraceList==null||mapTraceList.size()<=0)
            return jsonUserTrackdefsList;

        List<Integer> uIdList=new ArrayList<Integer>();
        for(MapTrace mapTrace:mapTraceList)
        {
            JsonUserTrackdefs trackdefsJson=new JsonUserTrackdefs();
            trackdefsJson.setDate(mapTrace.getCrtDatetime());
            trackdefsJson.setMlalo(mapTrace.getmLalo());
            trackdefsJson.setUserId(mapTrace.getUid());

            if(!uIdList.contains(trackdefsJson.getUserId()))
            {
                uIdList.add(trackdefsJson.getUserId());
            }

            jsonUserTrackdefsList.add(trackdefsJson);
        }
        List<WkbUser> wkbUserList= wkbUserService.queryUserList(uIdList);
        for(JsonUserTrackdefs trackdefsJson:jsonUserTrackdefsList)
        {
            for(WkbUser wkbUser:wkbUserList)
            {
                if(wkbUser.getuId().equals(trackdefsJson.getUserId()))
                {
                    if(StringUtils.isNotBlank(wkbUser.getuName()))
                        trackdefsJson.setName(wkbUser.getuName());
                    else
                        trackdefsJson.setName(wkbUser.getuIdentifier());
                    if(StringUtils.isNotBlank(wkbUser.getImageUrl()))
                        trackdefsJson.setImageUrl(systemConfigure.getImageServer()+wkbUser.getImageUrl());
                    break;
                }
            }
        }
        return jsonUserTrackdefsList;
    }











    //****************************************************************************************************
    @RequestMapping(value = "/setdefs", method = {RequestMethod.POST})
    @ResponseBody
    public RetInfo setdefs(@RequestBody DefautUserRequest userRequest)
    {
        MapTrace mapTrace=new MapTrace();
        mapTrace.setUid(userRequest.getUid());
        mapTrace.setUname(userRequest.getUname());
        List<FriendRequest> frList = userRequest.getFrdsuids();
        List<MapTrace> mtList = new ArrayList<MapTrace>();
        for (FriendRequest fr :frList) {
            MapTrace mt = new MapTrace();
            mt.setUid(fr.getFid());
            mt.setUname(fr.getFname());
            mtList.add(mt);
        }
        mapTrace.setFrdsuids(mtList);

        mapTraceService.saveDefaultUser(mapTrace);

        RetInfo retInfo=new RetInfo();
        retInfo.setSuccess(true);
        retInfo.setMessage("succ");
        return retInfo;
    }


    @RequestMapping(value = "/mapTraces", method = {RequestMethod.POST})
    @ResponseBody
    public List<MapTrace> mapTraces(@RequestBody TempInfo tempInfo)
    {
        MapTrace mt = new MapTrace();
        mt.setUid(tempInfo.getUid());
        return mapTraceService.findByUserId(mt);

    }
    
    @RequestMapping(value = "/del/{m_id}", method = {RequestMethod.GET})
    @ResponseBody
    public RetInfo del(@PathVariable int m_id)
    {
//        List<MapTrace> users = mapTraceService.findByUserId(tempInfo.getU_id());
        MapTrace mt = new MapTrace();
        mt.setMid(m_id);
        mapTraceService.deleteMaptraceByPK(mt);
        RetInfo retInfo=new RetInfo();
        retInfo.setSuccess(true);
        retInfo.setMessage("succ");
        return retInfo;
       // return mt;
//        return users;
    }

    @RequestMapping(value = "/testget/{m_id}", method = {RequestMethod.GET})
    @ResponseBody
    public RetInfo testget(@PathVariable int m_id)
    {

        RetInfo retInfo=new RetInfo();
        retInfo.setSuccess(true);
        retInfo.setMessage("succ");
        return retInfo;
    }
    @RequestMapping(value = "/ppp", method = {RequestMethod.POST})
    @ResponseBody
    public RetInfo aaa(@RequestBody TempInfo tempInfo)
    {
       RetInfo retInfo=new RetInfo();
        retInfo.setSuccess(true);
        retInfo.setMessage("succ");
        return retInfo;
    }

   /* @RequestMapping(value = "/frdsMap/{m_id}", method = {RequestMethod.GET})
    @ResponseBody
    public List<FrdMap> fetchFrdMaps(@PathVariable int m_id)
    {
        try{
            List<FrdMap>  frdMapList=new ArrayList<FrdMap>();
            List<MapTrace> mapTraceList= mapTraceService.findFrdsMap(m_id);
            for(MapTrace mapTrace:mapTraceList)
            {
                FrdMap frdMap=new FrdMap();
                frdMap.setAccount(mapTrace.getU_id());
                frdMap.setName("");

                frdMapList.add(frdMap);
            }

            return frdMapList;
        }catch (Exception exp)
        {
            logger.error("error",exp);
            return null;
        }
    }*/

    /**
     *
     * @param ids      [123,123,123]
     * @return  list of MapTrace json
     */
    @RequestMapping(value = "/trackdefsold", method = {RequestMethod.POST})
    @ResponseBody
    public List<FrdMap> fetchFrdMaps(@RequestBody List<Integer> ids)
    {
         logger.debug("ids:" +ids);

        List<FrdMap> frdMapList=new ArrayList<FrdMap>();
        List<MapTrace> mapTraceList= mapTraceService.findFrds(ids);
        for(MapTrace mapTrace:mapTraceList)
        {
            FrdMap frdMap=new FrdMap();
            BeanUtils.copyProperties(mapTrace,frdMap);
            frdMapList.add(frdMap);
        }
        return frdMapList;
//        List<FrdMap>  frdMapList=new ArrayList<FrdMap>();
//        List<MapTrace> mapTraceList= mapTraceService.findFrdsMap(m_id);
//        for(MapTrace mapTrace:mapTraceList)
//        {
//            FrdMap frdMap=new FrdMap();
//            frdMap.setAccount(mapTrace.getU_id());
//            frdMap.setName("");
//
//            frdMapList.add(frdMap);
//        }
//
//        return frdMapList;

    }


    @RequestMapping(value = "/group", method = {RequestMethod.POST})
    @ResponseBody
    public List<Group> fetchtest(@RequestBody List<Integer> ids)
    {
        logger.debug("ids:" +ids);
       List<Group> groupList=new ArrayList<Group>();
        Group group=new Group("技术组");
        FrdMap frdMap=new FrdMap();
        frdMap.setCreator("xxx");
        group.getFrdMapList().add(frdMap);

        groupList.add(group);

        group=new Group("技术组");
        frdMap=new FrdMap();
        frdMap.setCreator("xxx");
        group.getFrdMapList().add(frdMap);

        frdMap=new FrdMap();
        frdMap.setCreator("yyy");
        group.getFrdMapList().add(frdMap);

        groupList.add(group);

        return groupList;

    }
}
