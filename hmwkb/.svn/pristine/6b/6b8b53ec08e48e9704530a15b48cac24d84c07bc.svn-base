package com.heima.service;

import com.heima.dao.model.OrgRight;
import com.heima.model.WkbOrganization;
import com.heima.model.WkbUser;

import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbOrganizationService {
    List<WkbUser> queryOrganizationUsers(Integer orgId);
    OrgRight queryRight(WkbUser wkbUser);
    List<WkbOrganization> getTopOrganizations(String companyId);
    List<WkbOrganization> getChilds(Integer orgId);
    Map<Integer, WkbOrganization> queryCompanyUserOrganizations(List<WkbUser> wkbUserList);


    /*******************************************/
    List<WkbOrganization> saveOrgs(List<WkbOrganization> wkbOrganizationList);

    WkbOrganization queryOrganization(Integer orgId);

    void deleteOrganization(Integer orgId);

    boolean upateOrganization(WkbOrganization wkbOrganization);
    List<WkbOrganization> getOrganizationsByCompany(String companyId);

    List<WkbUser> getUsersByCompany(String companyId);


    /*******************************************/
    WkbOrganization addOrganization(WkbOrganization wkbOrganization);

    /**
     * 删除组织
     * @param orgId
     * @param bLoop 是否递归删除
     * @return 非递归删除并且非空时，返回false，否则返回true
     */
    boolean deleteOrganization(Integer orgId, boolean bLoop);
}
