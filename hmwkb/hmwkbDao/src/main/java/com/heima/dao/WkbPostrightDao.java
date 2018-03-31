package com.heima.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.heima.model.WkbPostright;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbPostrightDao extends BaseDao<WkbPostright> {
    List<WkbPostright> getAllRigthByCompany(String cId);
    int deleteRightById(Integer id);
    int updatePostright(WkbPostright wkbPostright);

    /******************************************/
    List<WkbPostright> getAllRightByOrg(Integer oId);

    WkbPostright getRoleById(Integer roleId);

    List<WkbPostright> getCompanyRoleByRoleId(@Param(value="pId")Integer pId,@Param(value="cId")String cId);
}
