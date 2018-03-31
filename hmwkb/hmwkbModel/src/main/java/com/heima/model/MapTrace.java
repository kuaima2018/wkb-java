package com.heima.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-18
 * Time: 下午10:44
 * To change this template use File | Settings | File Templates.
 */
public class MapTrace implements Serializable {
    private Integer mid;
    private Integer uid;
    private String uname;
    private Date mTraceDate;
    private String mLalo;
    private String creator;
    private Date crtDatetime;
    private List<MapTrace> frdsuids;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Date getmTraceDate() {
        return mTraceDate;
    }

    public void setmTraceDate(Date mTraceDate) {
        this.mTraceDate = mTraceDate;
    }

    public String getmLalo() {
        return mLalo;
    }

    public void setmLalo(String mLalo) {
        this.mLalo = mLalo;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCrtDatetime() {
        return crtDatetime;
    }

    public void setCrtDatetime(Date crtDatetime) {
        this.crtDatetime = crtDatetime;
    }

    public List<MapTrace> getFrdsuids() {
        return frdsuids;
    }

    public void setFrdsuids(List<MapTrace> frdsuids) {
        this.frdsuids = frdsuids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapTrace mapTrace = (MapTrace) o;

        if (creator != null ? !creator.equals(mapTrace.creator) : mapTrace.creator != null) return false;
        if (crtDatetime != null ? !crtDatetime.equals(mapTrace.crtDatetime) : mapTrace.crtDatetime != null)
            return false;
        if (mLalo != null ? !mLalo.equals(mapTrace.mLalo) : mapTrace.mLalo != null) return false;
        if (mTraceDate != null ? !mTraceDate.equals(mapTrace.mTraceDate) : mapTrace.mTraceDate != null) return false;
        if (mid != null ? !mid.equals(mapTrace.mid) : mapTrace.mid != null) return false;
        if (uid != null ? !uid.equals(mapTrace.uid) : mapTrace.uid != null) return false;
        if (uname != null ? !uname.equals(mapTrace.uname) : mapTrace.uname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mid != null ? mid.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (uname != null ? uname.hashCode() : 0);
        result = 31 * result + (mTraceDate != null ? mTraceDate.hashCode() : 0);
        result = 31 * result + (mLalo != null ? mLalo.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (crtDatetime != null ? crtDatetime.hashCode() : 0);
        return result;
    }

// @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	//public Date getCrtdatetime() {
		//return crtdatetime;
	//}



}