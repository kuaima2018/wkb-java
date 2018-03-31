package com.heima.service;

import com.heima.dto.WkbUserQueryDto;
import com.heima.model.SocialitySearch;
import com.heima.model.WkbUser;
import com.heima.service.biz.WkbBizResult;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbUserService {
    WkbBizResult saveUser(WkbUser wkbUser);
    WkbBizResult updateUser(WkbUser wkbUser);
    WkbBizResult checkUser(Integer uId, String password);
    WkbUser getUser(Integer uId);
    WkbBizResult applyJoinCompany(Integer uId, String companyId, boolean isConfirm);
    List<WkbUser> queryUserList(List<Integer> uIdList);

    WkbUser queryUserByMobile(String mobile);
    WkbUser queryUserByIdentifier(String identifier);
    void removeUser(Integer userId);


    /*******************************/
    List<WkbUser> getUserByName(String name);

    WkbUser getUserByPrimary(int id, int isAdmin);

    List<WkbUser> findNotJionEm(String companyId);

    boolean jion(int id);

    List<WkbUser> findJionEm(String companyId);

    boolean reject(int id);

    WkbUser pwd(WkbUser user);

    boolean updateOrgByPK(WkbUser user);

    List<WkbUser> batchSave(WkbUser[] users);

    WkbUser addAdmin(WkbUser user);

    List<WkbUser> getPageCompanyUsers(WkbUserQueryDto wkbUserQueryDto,Integer startPos,Integer endPos);

    Integer getCountCompanyUsers(WkbUserQueryDto wkbUserQueryDto);

    int updateUsersByAdminAdd(List<Integer> uIdList, String cId);

    int updateUsersByAdminDel(List<Integer> uIdList, String cId);

    List<WkbUser> queryFuzzyByCompanyName(String companyName,int startPos,int endPos);

    List<WkbUser> queryFuzzyByMobile(String mobile,int startPos,int endPos);

    List<WkbUser> queryFuzzyByName(String name,int startPos,int endPos);

    List<WkbUser> queryFuzzyByPosition(String position,int startPos,int endPos);

    List<WkbUser> getPageCompanyFormalUsers(String companyId, Integer userId, Integer index,Integer pageSize);

    int getCountCompanyFormalUsers(String companyId);

    List<WkbUser> searchUsers(SocialitySearch socialitySearch, Integer index,Integer pageSize);

    List<WkbUser> queryFriends(Integer userId, Integer startPos,Integer endPos);
}
