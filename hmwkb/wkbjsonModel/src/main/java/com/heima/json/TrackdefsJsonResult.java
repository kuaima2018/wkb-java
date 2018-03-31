package com.heima.json;

import com.heima.common.WkbResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class TrackdefsJsonResult extends WkbResult implements Serializable {
    public List<TrackdefsJson> getTraces() {
        return traces;
    }

    public void setTraces(List<TrackdefsJson> traces) {
        this.traces = traces;
    }

    private List<TrackdefsJson> traces;
}
