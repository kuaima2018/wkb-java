package com.heima.service.impl;

import com.heima.service.CompanyIndustyService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/8
 */
@Service
public class CompanyIndustyServiceImpl implements CompanyIndustyService {
    @Override
    public Map<Integer, String> getIndustyData() {
        Map<Integer,String> map=new HashMap<Integer, String>();
        map.put(1,"行业1");
        map.put(2,"行业2");
        return map;
    }
}
