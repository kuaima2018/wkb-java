package com.heima.service;

import com.heima.dto.WkbUserQueryDto;
import com.heima.model.WkbOrgpost;
import com.heima.model.WkbPostright;
import com.heima.model.WkbUser;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbPostrightService {
    List<WkbPostright> queryPostrightsByCompany(String companyId);
    void savePostrights(List<WkbPostright> wkbPostrightList);
    int removePostright(Integer postId);
    int updatePostright(WkbPostright wkbPostright);

    /*******************************************/
    WkbPostright addRole(WkbPostright wkbPostright);

    WkbPostright getRole(Integer roleId);

    /**
     * 删除角色
     * @param roleId
     * @param bLoop 是否递归
     * @return
     */
    boolean deleteRole(Integer roleId, boolean bLoop);

    List<WkbOrgpost> queryUserRolesByRoleId(Integer roleId);

    List<WkbPostright> queryRolesByOrgId(Integer orgId);

    List<WkbUser> queryPageUserRolesByRoleId(WkbUserQueryDto wkbUserQueryDto, Integer startPos, Integer endPos);

    Integer queryCountUserRolesByRoleId(WkbUserQueryDto wkbUserQueryDto);
}
