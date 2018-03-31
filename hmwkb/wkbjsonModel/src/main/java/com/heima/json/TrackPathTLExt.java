package com.heima.json;

import java.math.BigDecimal;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class TrackPathTLExt extends TrackPathTL {
    private BigDecimal delta;

    public Long getTimeDelta() {
        return timeDelta;
    }

    public void setTimeDelta(Long timeDelta) {
        this.timeDelta = timeDelta;
    }

    private Long timeDelta;

    public BigDecimal getWeidu() {
        return weidu;
    }

    public void setWeidu(BigDecimal weidu) {
        this.weidu = weidu;
    }

    public BigDecimal getJingdu() {
        return jingdu;
    }

    public void setJingdu(BigDecimal jingdu) {
        this.jingdu = jingdu;
    }

    private BigDecimal weidu;
    private BigDecimal jingdu;
    private Integer preTraceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;

    public BigDecimal getDelta() {
        return delta;
    }

    public void setDelta(BigDecimal delta) {
        this.delta = delta;
    }

    public Integer getPreTraceId() {
        return preTraceId;
    }

    public void setPreTraceId(Integer preTraceId) {
        this.preTraceId = preTraceId;
    }
}
