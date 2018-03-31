package com.heima.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jay
 * Date: 14-3-22
 * Time: 下午12:52
 * To change this template use File | Settings | File Templates.
 */
public class Tracksetdefsinput implements Serializable {
    private Integer uid;
    private List<Integer>  frdsuid;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public List<Integer> getFrdsuid() {
        return frdsuid;
    }

    public void setFrdsuid(List<Integer> frdsuid) {
        this.frdsuid = frdsuid;
    }
}
