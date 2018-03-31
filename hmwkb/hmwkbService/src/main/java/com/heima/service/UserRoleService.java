package com.heima.service;

import com.heima.dto.WkbUserQueryDto;
import com.heima.json.WkbOrgUser;
import com.heima.model.WkbUser;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface UserRoleService {
    List<WkbOrgUser> queryTaskReceivers(Integer uId,boolean bFriend);

    @Deprecated
    List<WkbUser> queryUsersByRoleId(Integer roleId);


    //
    List<WkbUser> queryPageUsersByRoleId(WkbUserQueryDto wkbUserQueryDto,Integer index,Integer page);
    Integer queryCountUsersByRoleId(WkbUserQueryDto wkbUserQueryDto);


    List<WkbUser> queryPageCompanyApplyUsers(WkbUserQueryDto wkbUserQueryDto,Integer index,Integer page);
    Integer queryCountCompanyApplyUsers(WkbUserQueryDto wkbUserQueryDto);

    void addRoleUsers(Integer roleId, List<Integer> uIdList, Integer userId);
    void remvoeRoleUsers(Integer roleId, List<Integer> uIdList);

    String getCompanyFromRoleId(Integer roleId);

    void removeCompanyUsers(String companyId, Integer userId);
}
