package com.heima.json;

import java.util.Comparator;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ComparatorMapTraceDelta implements Comparator {
    private int type;
    public ComparatorMapTraceDelta(int type)
    {
        this.type=type;
    }

    @Override
    public int compare(Object o1, Object o2) {
        TrackPathTLExt trackPathTLExt1=(TrackPathTLExt)o1;
        TrackPathTLExt trackPathTLExt2=(TrackPathTLExt)o2;
        if(type==0)
            return trackPathTLExt1.getDelta().compareTo(trackPathTLExt2.getDelta());
        else
            return trackPathTLExt1.getTimeDelta().compareTo(trackPathTLExt2.getTimeDelta());
    }
}
