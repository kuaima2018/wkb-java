package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.dto.WkbUserQueryDto;
import com.heima.model.WkbOrgpost;
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
public interface WkbOrgpostDao extends BaseDao<WkbOrgpost> {
    List<WkbOrgpost> getAllOrgpostsByCompany(String companyId);

    /**
     * 根据岗位id来删除岗位用户权限
     * @param pId
     * @return
     */
    int removeUserRolesByRoleId(Integer pId);

    int removeUserRolesByUserId(@Param(value="pId")Integer pId, @Param(value="uId")Integer uId);

    int removeUserRoleById(Integer id);

    List<WkbOrgpost> getUserRolesByRoleId(Integer pId);

    List<WkbUser> getPageUserRolesByRoleId(@Param(value="userQuery")WkbUserQueryDto wkbUserQueryDto,@Param(value="startPos")Integer startPos,@Param(value="endPos")Integer endPos);

    int getCountUserRolesByRoleId(@Param(value="userQuery")WkbUserQueryDto wkbUserQueryDto);

    int resetCompanyByRoleId(Integer pId);

    int resetCompanyByUsers(List<Integer> uIdList);

    int removeUserRolesByUsers(@Param(value="uIdList")List<Integer> uIdList,@Param(value="pId")Integer pId);

    int removeUserRoles(@Param(value="userId")Integer userId);
}
