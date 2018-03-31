package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbVersion;

/**
 * Created by xuzhikai on 2015/10/7.
 */
public interface WkbVersionDao extends BaseDao<WkbVersion> {
    WkbVersion queryByType(String appType);
}
