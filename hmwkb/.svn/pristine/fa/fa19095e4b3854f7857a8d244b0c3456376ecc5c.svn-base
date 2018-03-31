package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.dao.model.OrgRight;
import com.heima.model.WkbOrganization;
import com.heima.model.WkbUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbOrganizationDao extends BaseDao<WkbOrganization> {
    List<WkbUser> queryOrganizationUsers(Integer orgId);
    OrgRight queryRight(WkbUser wkbUser);
    List<OrgRight> queryCompanyFriends(List<WkbUser> wkbUserList);
    List<WkbOrganization> queryChilds(Integer orgId);
    List<WkbOrganization> queryTopOrgs(String companyId);

    WkbOrganization getWkbOrganization(Integer id);
    int delWkbOrganization(Integer id);
    int updateWkbOrganization(WkbOrganization wkbOrganization);

    List<WkbOrganization> getAllOrgsByCompany(String companyId);

    List<WkbUser> getAllUsersByCompany(String companyId);

    /***************************************************/
    List<WkbOrganization> queryCompanyOrgByName(@Param(value="cId")String cId, @Param(value="oId")String oId);
}
