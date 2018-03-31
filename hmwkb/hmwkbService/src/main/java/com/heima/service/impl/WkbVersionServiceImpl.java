package com.heima.service.impl;

import com.heima.dao.WkbVersionDao;
import com.heima.model.WkbVersion;
import com.heima.service.WkbVersionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuzhikai on 2015/10/7.
 */
@Service
public class WkbVersionServiceImpl implements WkbVersionService {

    @Autowired
    private WkbVersionDao wkbVersionDao;

    @Override
    public WkbVersion queryByAppType(String appType) {
        if(StringUtils.isNotBlank(appType))
            return wkbVersionDao.queryByType(appType);
        else
            return null;
    }
}
