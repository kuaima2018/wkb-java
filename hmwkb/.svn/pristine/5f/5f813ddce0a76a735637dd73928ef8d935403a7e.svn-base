package com.heima.service.impl;

import com.heima.dao.DefShareDao;
import com.heima.dao.MapTraceDao;
import com.heima.json.*;
import com.heima.model.DefShareModel;
import com.heima.model.MapTrace;
import com.heima.service.MapTraceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-18
 * Time: 下午10:58
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MapTraceServiceImpl implements MapTraceService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MapTraceServiceImpl.class);
    @Autowired
    private MapTraceDao mapTraceDao;

    @Autowired
    private DefShareDao defShareDao;


    @Override
    public void saveMaptrace(MapTrace mapTrace) {
        //
        mapTraceDao.insertData(mapTrace);
    }
    @Override
    public void saveDefaultUser(MapTrace mapTrace)  {
        DefShareModel ds = new DefShareModel();
        ds.setUserId(mapTrace.getUid());

        List<DefShareModel> defShareList = defShareDao.queryForList(ds);
        if (null != defShareList && defShareList.size() == 0) {

            List<MapTrace> mapTraceList = mapTrace.getFrdsuids();
            for(MapTrace mt: mapTraceList) {
                save(mapTrace, mt);
            }
        } else {
            List<MapTrace> mapTraceList = mapTrace.getFrdsuids();
            for(MapTrace mt: mapTraceList) {
                boolean isExist = false;
                for(DefShareModel dsm :defShareList) {

                    if(mt.getUid() == dsm.getFriendId()) {
                        isExist = true;
                    }
                }
                if(!isExist) {
                    save(mapTrace, mt);
                }

            }
        }



        mapTraceDao.insertData(mapTrace);
    }

    private void save(MapTrace mapTrace, MapTrace mt) {
        DefShareModel defShareModel = new DefShareModel();
        defShareModel.setUserId(mapTrace.getUid());
        defShareModel.setUserName(mapTrace.getUname());
        defShareModel.setFriendId(mt.getUid());
        defShareModel.setFriendName(mt.getUname());
        defShareModel.setCreator(mapTrace.getUname());
        defShareModel.setCrtDateTime(new Timestamp(new Date().getTime()));
        defShareDao.insertData(defShareModel);
    }

    @Override
    public List<MapTrace> findByUserId(MapTrace mapTrace) {
        List<MapTrace>   list =  mapTraceDao.queryForList(mapTrace)  ;
        return  list;  //To change body of implemented methods use File | Settings | File Templates.
    }

	@Override
	public void deleteMaptraceByPK(MapTrace mapTrace) {
		mapTraceDao.deleteDataByPK(mapTrace);
	}

    @Override
    public List<MapTrace> findFrdsMap(Integer usrid) {
        List<Integer> frdList=new ArrayList<Integer>();
        frdList.add(1111);
        frdList.add(2222);

        return null;
    }

    @Override
    public List<MapTrace> findFrds(List<Integer> ids) {

       //
        return mapTraceDao.queryFrends(ids);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Value("${com.heima.track.count:30}")
    private Integer mapCount;//=10
    private static final BigDecimal precisionPermit=new BigDecimal("0.5");

    @Override
    public TrackPathJsonResult findPathByUsr(Trackinput trackinput) {

        TrackPathJsonResult trackPathJson=new TrackPathJsonResult();
        List<MapTrace> mapTraceList= mapTraceDao.queryTracepath(trackinput);

        trackPathJson.setUserid(trackinput.getUid());
        trackPathJson.setMlalos(new ArrayList<TrackPathTL>());
        //如果总的数量小于等于给定值，那么直接返回
        //如果大于给定值，保留起始和结束点，并过滤掉中间间隔小的点
        //过滤算法，就是简单递归去掉距离最短的两个点
        List<TrackPathTLExt> trackPathTLList=new ArrayList<TrackPathTLExt>();

        if(mapTraceList!=null)
        {
            for(MapTrace mapTrace:mapTraceList)
            {
                TrackPathTLExt trackPathTLExt=new TrackPathTLExt();
                trackPathTLExt.setMlalo(mapTrace.getmLalo());
                trackPathTLExt.setTime(mapTrace.getCrtDatetime());
                trackPathTLExt.setId(mapTrace.getMid());
                String[] items=trackPathTLExt.getMlalo().split(",");
                if(items.length==2)
                {
                    trackPathTLExt.setJingdu(new BigDecimal(items[1]));
                    trackPathTLExt.setWeidu(new BigDecimal(items[0]));
                }
                if(trackPathTLExt.getWeidu()!=null)
                    trackPathTLList.add(trackPathTLExt);
            }
        }
        Collections.sort(trackPathTLList, new ComparatorMapTraceTime());

        if(trackPathTLList.size()>mapCount)
        {
            //保留最后一个节点
            TrackPathTLExt lastTrackPathTL=trackPathTLList.get(trackPathTLList.size()-1);
            trackPathTLList.remove(trackPathTLList.size()-1);
            while (trackPathTLList.size()>mapCount-1)
            {
                calcTracePath(trackPathTLList);
                List<TrackPathTLExt> removeTrackPathTLExtList = seachMinTracks(trackPathTLList);
                if(removeTrackPathTLExtList.size()<=0)
                {
                    logger.error("can not find min path");
                    break;
                }
                removeMinTracks(trackPathTLList, removeTrackPathTLExtList);
            }
            trackPathTLList.add(lastTrackPathTL);
            Collections.sort(trackPathTLList, new ComparatorMapTraceTime());
        }

        for(TrackPathTLExt trackPathTLExt:trackPathTLList)
        {
            TrackPathTL trackPathTL=new TrackPathTL();
            BeanUtils.copyProperties(trackPathTLExt,trackPathTL);
            trackPathJson.getMlalos().add(trackPathTL);
        }
        return trackPathJson;
    }

    /**
     * 删除最小距离的节点集合（时间间隔相近的优先删除）
     * @param trackPathTLList
     * @param removeList
     */
    private void removeMinTracks(List<TrackPathTLExt> trackPathTLList, List<TrackPathTLExt> removeList)
    {
        //先删除时间间隔最短的节点
        //并且如果相邻的节点刚删除，那么不允许删除此节点，需要重新计算
        Collections.sort(removeList, new ComparatorMapTraceDelta(1));
        List<TrackPathTLExt> deleteList=new ArrayList<TrackPathTLExt>();
        for(int index=0;index<removeList.size();index++)
        {
            TrackPathTLExt removeNode=removeList.get(index);
            //检查相邻的节点是否删除了
            boolean bDelete=false;
            for(TrackPathTLExt trackPathTLExt:deleteList)
            {
                if(removeNode.getPreTraceId().equals(trackPathTLExt.getId()))
                {
                    bDelete=true;
                    break;
                }
                if(trackPathTLExt.getPreTraceId().equals(removeNode.getId()))
                {
                    bDelete=true;
                    break;
                }
            }
            if(bDelete==true)
            {
                continue;
            }
            //真正删除节点
            int delPos=-1;
            for(int i=0;i<trackPathTLList.size();i++)
            {
                if(trackPathTLList.get(i).getId().equals(removeNode.getId()))
                {
                    delPos=i;
                    break;
                }
            }
            if(delPos<0)
            {
                logger.error("no match node find!!!");
                return;
            }
            trackPathTLList.remove(delPos);
            if(trackPathTLList.size()<=mapCount-1)
                return;
        }
    }

    /**
     * 查找出最短距离的节点
     * @param trackPathTLList
     * @return
     */
    private List<TrackPathTLExt> seachMinTracks(List<TrackPathTLExt> trackPathTLList)
    {
        List<TrackPathTLExt> minTrackPathTLList=new ArrayList<TrackPathTLExt>();
        Collections.sort(trackPathTLList, new ComparatorMapTraceDelta(0));
        TrackPathTLExt minTrackPathTLExt=trackPathTLList.get(0);
        minTrackPathTLList.add(minTrackPathTLExt);
        for(int index=1;index<trackPathTLList.size();index++)
        {
            TrackPathTLExt trackPathTLExt=trackPathTLList.get(index);
            if(trackPathTLExt.getDelta().subtract(minTrackPathTLExt.getDelta()).abs().compareTo(precisionPermit)<=0)
            {
                minTrackPathTLList.add(trackPathTLExt);
            }
        }
        return minTrackPathTLList;
    }

    /**
     * 计算节点间距离
     * @param trackPathTLList
     */
    public void calcTracePath(List<TrackPathTLExt> trackPathTLList)
    {
        Collections.sort(trackPathTLList, new ComparatorMapTraceTime());
        if(trackPathTLList.get(0).getDelta()==null)
            trackPathTLList.get(0).setDelta(new BigDecimal("100000000000000000000000000"));
        for(int index=1;index<trackPathTLList.size();index++)
        {
            TrackPathTLExt trackPathTLNow=trackPathTLList.get(index);
            TrackPathTLExt trackPathTLPre=trackPathTLList.get(index-1);
            if(trackPathTLNow.getDelta()==null||!trackPathTLNow.getPreTraceId().equals(trackPathTLPre.getId()))
            {
                //BigDecimal path1=trackPathTLNow.getJingdu().subtract(trackPathTLPre.getJingdu()).pow(2);
                //BigDecimal path2=trackPathTLNow.getWeidu().subtract(trackPathTLPre.getWeidu()).pow(2);

                Double c1=Math.sin(Math.toRadians(trackPathTLNow.getWeidu().doubleValue()))*Math.sin(Math.toRadians(trackPathTLPre.getWeidu().doubleValue()));
                Double c2=Math.cos(Math.toRadians(trackPathTLNow.getWeidu().doubleValue()))*Math.cos(Math.toRadians(trackPathTLPre.getWeidu().doubleValue()));
                Double c3=Math.cos(Math.toRadians(trackPathTLNow.getJingdu().doubleValue())-Math.toRadians(trackPathTLPre.getJingdu().doubleValue()));

                Double dbl=c1+c2*c3;
                if(dbl<0D)
                {
                    logger.error("path result:"+dbl);
                    dbl=0D;
                }
                if(dbl>1.0D)
                {
                    logger.error("path result:"+dbl);
                    dbl=1.0D;
                }
                Double ds=6370996D*Math.acos(dbl);
                String str=String.valueOf(ds.toString());
                logger.debug("delta("+trackPathTLNow.getId()+"-"+trackPathTLPre.getId()+"):"+str);
                try
                {
                    trackPathTLNow.setDelta(new BigDecimal(str));
                }catch (Exception exp)
                {
                    logger.error("calc path error:"+str, exp);
                    trackPathTLNow.setDelta(BigDecimal.ZERO);
                }
                logger.debug("id:"+trackPathTLNow.getId()+"-delta:"+trackPathTLNow.getDelta());
                trackPathTLNow.setPreTraceId(trackPathTLPre.getId());

                trackPathTLNow.setTimeDelta(trackPathTLNow.getTime().getTime()-trackPathTLPre.getTime().getTime()/60000);
            }
        }

        //System.out.println("succ");
    }



    @Override
    public List<TrackdefsJson> finddeffrdsusr(Trackinput trackinput) {
        List<TrackdefsJson> trackPathJsonList=new ArrayList<TrackdefsJson>();
        List<MapTrace> mapTraceList= mapTraceDao.queryTraceDefsfrdsusr(trackinput);
        for(MapTrace mapTrace:mapTraceList) {
            TrackdefsJson tj = new TrackdefsJson();
            //tj.setUid(mapTrace.getUid());
            tj.setMlalo(mapTrace.getmLalo());
            //tj.setUname(mapTrace.getCreator());
            trackPathJsonList.add(tj);
        }
        return trackPathJsonList;
    }

    @Override
    public List<MapTrace> findLastPathByUsers(List<Integer> uIdList, Date lastDate) {
        List<MapTrace> mapTraceList= mapTraceDao.queryLastMapTraceByUsers(uIdList,lastDate);
        if(mapTraceList==null||mapTraceList.size()==0)
            return mapTraceList;
        else
        {
            List<MapTrace> retList=new ArrayList<MapTrace>();
            for(MapTrace mapTrace:mapTraceList)
            {
                if(this.findMapTrace(retList,mapTrace.getUid())!=null)
                    continue;
                retList.add(mapTrace);
            }
            return retList;
        }
    }

    private MapTrace findMapTrace(List<MapTrace> mapTraceList,Integer uid)
    {
        for(MapTrace mapTrace:mapTraceList)
        {
            if(uid.equals(mapTrace.getUid()))
                return mapTrace;
        }
        return null;
    }

}
