package com.heima.service.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class BulkListSplitter<T extends Serializable> {
    public List<List<T>> splitList(List<T> list, int batchSize)
    {
        List<List<T>> listList=new ArrayList<List<T>>();

        int count=list.size() / batchSize;
        int lessCount=list.size()%batchSize;

        for(int i=0;i<count;i++)
        {
            List itemList=new ArrayList();
            for(int j=0;j<batchSize;j++)
            {
                itemList.add(list.get(i*batchSize+j));
            }
            listList.add(itemList);

        }
        if(lessCount>0)
        {
            List itemList=new ArrayList();
            for(int j=count*batchSize;j<list.size();j++)
            {
                itemList.add(list.get(j));
            }
            listList.add(itemList);
        }

        return listList;
    }
}
