package com.heima.service;

import com.heima.model.WkbNotation;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/30
 */
public interface WkbNotationService {
    public void saveNotation(WkbNotation wkbNotation);
    public List<WkbNotation> queryNotation(List<Integer> schedulerIdList,Integer type);
}
