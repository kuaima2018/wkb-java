package com.heima.service;

import com.heima.dto.WkbCompanyapplyQueryDto;
import com.heima.model.WkbCompany;
import com.heima.model.WkbUser;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-10
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbCompanyService {
    WkbCompany findCompany(String companyId);
    List<WkbCompany> getAllCompanys();

    List<WkbCompany> queryPageCompany(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto);
    Integer queryCountCompany(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto);
    void addCompanyFromApply(Integer companyApplyId,Integer uId);

    void updateCompany(WkbCompany wkbCompany);

    WkbCompany addCompany(WkbCompany wkbCompany,WkbUser currentUser);

    List<WkbCompany> findCompanyByName(String name);
}
