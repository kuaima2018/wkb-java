package com.heima.service.impl;

import com.heima.dao.WkbScheduleDao;
import com.heima.model.WkbNotation;
import com.heima.model.WkbSchedule;
import com.heima.service.WkbNotationService;
import com.heima.service.WkbScheduleService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/16
 */
@Service
public class WkbScheduleServiceImpl implements WkbScheduleService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WkbScheduleServiceImpl.class);

    @Autowired
    private WkbScheduleDao wkbScheduleDao;
    @Autowired
    private WkbNotationService wkbNotationService;

    @Override
    public List<WkbSchedule> queryScheduleByUser(Integer userId, Date beginDate, Date endDate) {
        if(beginDate==null||endDate==null)
            return null;
        return wkbScheduleDao.querySchedule(userId,beginDate,endDate);
    }

    @Override
    public void editSchedule(WkbSchedule wkbSchedule) {
        //首先根据用户和日期查找记录，如果找到就更新，没找到就插入
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String str=simpleDateFormat.format(wkbSchedule.getScheduleDate());
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate=null,endDate=null;
        try{
            beginDate=simpleDateFormat.parse(str+" 00:00:00");
            endDate=simpleDateFormat.parse(str+" 23:59:59");
        }catch (Exception exp)
        {

            throw new RuntimeException(exp.getMessage());
        }
        List<WkbSchedule> wkbScheduleList=wkbScheduleDao.querySchedule(wkbSchedule.getUserId(),beginDate,endDate);
        if(wkbScheduleList==null||wkbScheduleList.size()==0)
        {
            wkbSchedule.setCreator(String.valueOf(wkbSchedule.getUserId()));
            wkbSchedule.setCreateTime(new Date());
            wkbScheduleDao.insertData(wkbSchedule);
        }
        else if(wkbScheduleList.size()==1)
        {
            wkbSchedule.setId(wkbScheduleList.get(0).getId());
            wkbSchedule.setUpdateTime(new Date());
            wkbScheduleDao.updateSchedule(wkbSchedule);
        }
        else
            throw new RuntimeException("每日计划日报数量无效："+wkbSchedule.getScheduleDate());

    }

    @Override
    public void editNotation(WkbNotation wkbNotation,Integer userId, Date scheduleDate) {
        //首先根据用户和日期查找记录，如果找到就更新，没找到就插入
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String str=simpleDateFormat.format(scheduleDate);
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate=null,endDate=null;
        try{
            beginDate=simpleDateFormat.parse(str+" 00:00:00");
            endDate=simpleDateFormat.parse(str+" 23:59:59");
        }catch (Exception exp)
        {

            throw new RuntimeException(exp.getMessage());
        }
        List<WkbSchedule> wkbScheduleList=wkbScheduleDao.querySchedule(userId,beginDate,endDate);
        if(wkbScheduleList!=null&&wkbScheduleList.size()==1)
        {
            wkbNotation.setSchId(wkbScheduleList.get(0).getId());
            wkbNotationService.saveNotation(wkbNotation);
            //TODO:wkt-最后推送批注通知
        }
        else
        {
            if(wkbScheduleList==null||wkbScheduleList.size()==0)
            {
                //生成一个新的空计划日报
                WkbSchedule wkbSchedule=new WkbSchedule();
                wkbSchedule.setCreator(String.valueOf(wkbNotation.getUserId()));
                wkbSchedule.setCreateTime(new Date());
                wkbSchedule.setUserId(userId);

                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                str = simpleDateFormat.format(scheduleDate);
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    wkbSchedule.setScheduleDate(simpleDateFormat.parse(str + " 00:00:00"));
                } catch (Exception exp) {
                    logger.error("parse date error:",exp);
                }
                wkbScheduleDao.insertData(wkbSchedule);
                wkbNotation.setSchId(wkbSchedule.getId());
                wkbNotationService.saveNotation(wkbNotation);
            }
            else
                throw new RuntimeException("每日计划日报数量无效");
        }
    }

    @Override
    public List<WkbSchedule> queryUserRemind(Integer userId, Integer duration) {
        //查询提醒日期从今天开始的天数里，计划现设定为一年内的
        Date date=new Date();
        Date remindEndDate=DateUtils.addDays(date,duration);

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String strBegin=simpleDateFormat.format(date);
        String strEnd=simpleDateFormat.format(DateUtils.addDays(date,365));

        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate=null,endDate=null;
        try{
            beginDate=simpleDateFormat.parse(strBegin+" 00:00:00");
            endDate=simpleDateFormat.parse(strEnd+" 23:59:59");
        }catch (Exception exp)
        {

            throw new RuntimeException(exp.getMessage());
        }

        return wkbScheduleDao.queryScheduleRemind(userId,beginDate,endDate,date,remindEndDate);
    }
}
