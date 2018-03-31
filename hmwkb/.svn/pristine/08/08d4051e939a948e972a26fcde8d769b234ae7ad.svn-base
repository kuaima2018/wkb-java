package com.heima.json;

import java.util.Comparator;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ComparatorUserTask implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        UserTask userTask1=(UserTask)o1;
        UserTask userTask2=(UserTask)o2;
        if(userTask1.getDate()==null)
        {
            return -1;
        }
        else if(userTask2.getDate()==null)
        {
            return 10;
        }
        else
        {
            return userTask1.getDate().compareTo(userTask2.getDate());
        }
    }
}
