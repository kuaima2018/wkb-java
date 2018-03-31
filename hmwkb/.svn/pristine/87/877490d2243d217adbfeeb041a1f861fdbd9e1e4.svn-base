package com.heima.service;

import com.heima.dto.WkbCompanyapplyQueryDto;
import com.heima.model.WkbCompanyapply;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbCompanyapplyService {
    public void saveCompanyApply(WkbCompanyapply wkbCompanyapply);
    List<WkbCompanyapply> queryCompanyapply(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto);
    Integer queryCountCompanyapply(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto);

    WkbCompanyapply getCompanyapply(Integer companyapplyId);
    boolean agreeCompanyapply(Integer companyapplyId);
    boolean removeCompanyapply(Integer companyapplyId);
}
