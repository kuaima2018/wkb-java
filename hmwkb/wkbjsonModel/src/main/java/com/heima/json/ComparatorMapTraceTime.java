package com.heima.json;

import java.util.Comparator;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ComparatorMapTraceTime implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        TrackPathTLExt trackPathTLExt1=(TrackPathTLExt)o1;
        TrackPathTLExt trackPathTLExt2=(TrackPathTLExt)o2;
        return trackPathTLExt1.getTime().compareTo(trackPathTLExt2.getTime());
    }
}
