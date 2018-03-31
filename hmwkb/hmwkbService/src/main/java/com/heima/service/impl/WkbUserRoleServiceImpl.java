package com.heima.service.impl;

import com.heima.dao.WkbOrgpostDao;
import com.heima.model.WkbOrgpost;
import com.heima.service.WkbUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbUserRoleServiceImpl implements WkbUserRoleService {
    @Autowired
    private WkbOrgpostDao wkbOrgpostDao;

    @Override
    public List<WkbOrgpost> getUserRolesByCompany(String companyId) {
        return wkbOrgpostDao.getAllOrgpostsByCompany(companyId);
    }

    @Override
    public List<WkbOrgpost> addUserRoles(Integer roleId, List<Integer> uIdList) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int deleteUserRolesByRoleId(Integer roleId) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int deleteUserRole(Integer roleId, Integer userId) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int deleteUserRoleById(Integer id) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
