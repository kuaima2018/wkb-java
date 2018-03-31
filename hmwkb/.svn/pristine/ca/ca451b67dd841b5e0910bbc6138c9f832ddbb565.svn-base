package com.heima.service.impl;

import com.heima.dao.WkbFreeDao;
import com.heima.model.WkbFree;
import com.heima.service.WkbFreeService;
import com.heima.service.util.BulkListSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-13
 */
@Service
public class WkbFreeServiceImpl implements WkbFreeService {

    @Autowired
    private WkbFreeDao wkbFreeDao;

    @Override
    public List<WkbFree> queryFreeByUser(Integer userId, Integer index, Integer pageSize) {
        int pos=(index-1)*pageSize;
        return wkbFreeDao.queryFreeByUser(userId,pos,pageSize);
    }

    @Override
    public int deleteFreeByUser(Integer id, Integer userId) {
        return wkbFreeDao.deleteFreeByUser(id,userId);
    }

    private static final Integer batchSize=50;

    @Override
    public int deleteFrees(List<Integer> ids, Integer userId) {
        if(ids==null||ids.size()==0)
            return 0;
        BulkListSplitter<Integer> bulkListSplitter=new BulkListSplitter<Integer>();
        List<List<Integer>> listList =bulkListSplitter.splitList(ids,batchSize);
        int count=0;
        for(List<Integer> itemList:listList)
        {
            count+=wkbFreeDao.deleteFrees(itemList,userId);
        }
        return count;
    }

    @Override
    public void saveFree(WkbFree wkbFree) {
        wkbFreeDao.insertData(wkbFree);
    }
}
