package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.dto.WkbCompanyapplyQueryDto;
import com.heima.model.WkbCompanyapply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbCompanyapplyDao extends BaseDao<WkbCompanyapply> {
    List<WkbCompanyapply> findCompanyByName(WkbCompanyapply wkbCompanyapply);

    List<WkbCompanyapply> queryCompanyapply(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto);

    int queryCountCompanyapply(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto);

    WkbCompanyapply getCompanyapply(Integer companyapplyId);

    int updateCompanyapplyStatus(@Param(value="companyapplyId")Integer companyapplyId, @Param(value="orgStatus")String orgStatus,@Param(value="newStatus") String newStatus);
}
