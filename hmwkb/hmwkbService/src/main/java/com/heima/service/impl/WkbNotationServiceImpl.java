package com.heima.service.impl;

import com.heima.dao.WkbNotationDao;
import com.heima.model.WkbNotation;
import com.heima.service.WkbNotationService;
import com.heima.service.util.BulkListSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/30
 */
@Service
public class WkbNotationServiceImpl implements WkbNotationService {
    @Autowired
    private WkbNotationDao wkbNotationDao;

    @Override
    public void saveNotation(WkbNotation wkbNotation) {
        if(wkbNotation.getCreateTime()==null)
            wkbNotation.setCreateTime(new Date());
        wkbNotationDao.insertData(wkbNotation);
    }

    @Override
    public List<WkbNotation> queryNotation(List<Integer> schedulerIdList, Integer type) {
        BulkListSplitter<Integer> bulkListSplitter=new BulkListSplitter<Integer>();
        List<List<Integer>> itemList=bulkListSplitter.splitList(schedulerIdList,50);
        List<WkbNotation> wkbNotationList=new ArrayList<WkbNotation>();
        for(List<Integer> item:itemList)
        {
            wkbNotationList.addAll(wkbNotationDao.queryBySchedule(item,type));
        }
        return  wkbNotationList;
    }
}
