package com.heima.json;

import java.util.Comparator;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class JsonRevertComparatorUserTask implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        JsonUserTask userTask1=(JsonUserTask)o1;
        JsonUserTask userTask2=(JsonUserTask)o2;
        if(userTask1.getDate()==null)
        {
            return 10;
        }
        else if(userTask2.getDate()==null)
        {
            return -1;
        }
        else
        {
            return userTask2.getDate().compareTo(userTask1.getDate());
        }
    }
}
