package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.dto.WkbCompanyapplyQueryDto;
import com.heima.model.WkbCompany;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbCompanyDao extends BaseDao<WkbCompany> {
    WkbCompany getCompany(String companyId);
    List<WkbCompany> getAllCompanys();

    List<WkbCompany> queryCompanysByName(String cName);
    List<WkbCompany> queryPageCompany(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto);
    Integer queryCountCompany(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto);

    //int modifyComapny(WkbCompany wkbCompany);
}
