package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.dao.WkbCompanyDao;
import com.heima.dao.WkbOrgpostDao;
import com.heima.dao.WkbUserDao;
import com.heima.dto.WkbUserQueryDto;
import com.heima.model.SocialitySearch;
import com.heima.model.WkbCompany;
import com.heima.model.WkbUser;
import com.heima.service.WkbUserService;
import com.heima.service.biz.WkbBizException;
import com.heima.service.biz.WkbBizResult;
import com.heima.service.util.BulkListSplitter;
import com.heima.service.util.MD5Utils;
import com.heima.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbUserServiceImpl implements WkbUserService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WkbUserServiceImpl.class);

    @Autowired
    private WkbUserDao wkbUserDao;

    @Autowired
    private WkbCompanyDao wkbCompanyDao;

    @Autowired
    private WkbOrgpostDao wkbOrgpostDao;


    @Override
    public WkbBizResult saveUser(WkbUser wkbUser) {
        WkbBizResult wkbBizResult=new WkbBizResult();
        if(wkbUser.getCrtdatetime()==null)
            wkbUser.setCrtdatetime(new Date());
        if(wkbUser.getuId()==null||wkbUser.getuId().intValue()<0)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_ID.getCode());
            return wkbBizResult;
        }
        /*if(StringUtils.isBlank(wkbUser.getuName()))
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_NAME.getCode());
            return wkbBizResult;
        }*/
        if(StringUtils.isBlank(wkbUser.getuPwd()))
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_PASS.getCode());
            return wkbBizResult;
        }

        //数据库保存加密后的密文
        /*try
        {
            wkbUser.setuPwd(getPlainText(wkbUser.getuPwd()));
        }catch (Exception exp)
        {
            wkbBizResult.setCode(WkbMessage.USER_ADD_PASS_UNFORMAT);
            return wkbBizResult;
        }*/
        //目前只能一个手机号
        WkbUser wkbUserOld=wkbUserDao.queryUserByIdentifier(wkbUser.getuIdentifier());
        if(wkbUserOld!=null)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_ADD_ALREADY_EXIST_MOBILE.getCode());
            return wkbBizResult;
        }
        wkbUserDao.insertData(wkbUser);

        return wkbBizResult;
    }

    @Override
    public WkbBizResult updateUser(WkbUser wkbUser) {
        WkbBizResult wkbBizResult=new WkbBizResult();

        int count=wkbUserDao.updateUser(wkbUser);
        if(count<=0)
        {
            throw new WkbBizException(WkbMessageEnum.USER_NO_EXIST.getCode(),"");
        }
        return wkbBizResult;
    }

    @Override
    public WkbBizResult checkUser(Integer uId, String password) {
        WkbBizResult wkbBizResult=new WkbBizResult();
        if(uId==null||uId.intValue()<=0)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_ID.getCode());
            return wkbBizResult;
        }
        if(StringUtils.isBlank(password))
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_PASS.getCode());
            return wkbBizResult;
        }
        WkbUser wkbUser = wkbUserDao.getUser(uId);
        if(wkbUser==null)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_EXIST.getCode());
            return wkbBizResult;
        }
        if(!password.equals(wkbUser.getuPwd()))
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_USER_PASS_UNMATCH.getCode());
            return wkbBizResult;
        }

        return wkbBizResult;
    }

    @Override
    public WkbUser getUser(Integer uId) {
        return wkbUserDao.getUser(uId);
    }

    @Override
    public WkbBizResult applyJoinCompany(Integer uId, String companyId, boolean isConfirm) {
        WkbBizResult wkbBizResult=new WkbBizResult();
        WkbUser wkbUser = wkbUserDao.getUser(uId);
        if(wkbUser==null)
        {
            wkbBizResult.setCode(WkbMessageEnum.USER_NO_EXIST.getCode());
            return wkbBizResult;
        }
        if(companyId.equals(wkbUser.getcId()))
        {
            return wkbBizResult;
        }
        WkbCompany wkbCompany=wkbCompanyDao.getCompany(companyId);
        if(wkbCompany==null)
        {
            wkbBizResult.setCode(WkbMessageEnum.COMPANY_NOT_EXIST.getCode());
            return wkbBizResult;
        }

        if(isConfirm==false&&(StringUtils.isNotBlank(wkbUser.getcId())))
        {
            //如果已经加入其他公司，如何处理
            wkbBizResult.setCode(WkbMessageEnum.USER_JOINT_OTHER.getCode());
            return wkbBizResult;
        }
        else
        {
            WkbUser wkbUser1=new WkbUser();
            wkbUser1.setuId(wkbUser.getuId());
            wkbUser1.setcId(companyId);
            wkbUser1.setApplytime(new Date());
            wkbUser1.setOkcFlag((byte)0);
            wkbUser1.setuCompany(wkbCompany.getcName());
            int count=wkbUserDao.updateApply(wkbUser1);
            if(count!=1)
            {
                logger.error("user id:"+uId+" joint update count:"+count);
                throw new WkbBizException(WkbMessageEnum.USER_APPLY_ERROR.getCode(),"");
            }
            if(wkbUser.getOkcFlag()!=null&&wkbUser.getOkcFlag().intValue()==1)
                wkbOrgpostDao.removeUserRoles(wkbUser.getuId());
        }
        return wkbBizResult;
    }

    private Integer batchSize=50;
    @Override
    public List<WkbUser> queryUserList(List<Integer> uIdList) {

        List<WkbUser> wkbUserList=new ArrayList<WkbUser>();
        if(uIdList==null||uIdList.size()==0)
            return wkbUserList;
        BulkListSplitter<Integer> bulkListSplitter=new BulkListSplitter<Integer>();
        List<List<Integer>> listList =bulkListSplitter.splitList(uIdList,batchSize);
        for(List<Integer> itemList:listList)
        {
            List<WkbUser> wkbUsers =wkbUserDao.querUserList(itemList);
            if(wkbUsers!=null&&wkbUsers.size()>0)
            {
                wkbUserList.addAll(wkbUsers);
            }
        }
        return wkbUserList;
    }

    @Override
    public WkbUser queryUserByMobile(String mobile) {

        return wkbUserDao.queryUserByMobile(mobile);
    }

    @Override
    public WkbUser queryUserByIdentifier(String identifier) {

        return wkbUserDao.queryUserByIdentifier(identifier);
    }

    @Override
    public void removeUser(Integer userId) {
        wkbUserDao.removeUser(userId);
    }


    /*******************************/
    @Override
    public List<WkbUser> getUserByName(String name) {
        return null;
    }

    @Override
    public WkbUser getUserByPrimary(int id, int isAdmin) {
        List<WkbUser> wkbUserList=wkbUserDao.findByPrimary(id, isAdmin);
        if(wkbUserList!=null&&wkbUserList.size()>0)
            return wkbUserList.get(0);
        return null;
    }

    @Override
    public List<WkbUser> findNotJionEm(String companyId) {
        return wkbUserDao.findByCId(companyId,0, 0);
    }

    @Override
    public boolean jion(int id) {
        WkbUser wkbUser=wkbUserDao.getUser(id);
        if(wkbUser!=null)
        {
            if(wkbUser.getOkcFlag()!=null&&wkbUser.getOkcFlag().intValue()==1)
                return true;
        }
        return false;
    }

    @Override
    public List<WkbUser> findJionEm(String companyId) {
        return wkbUserDao.findByCId(companyId,0,1);
    }

    @Override
    public boolean reject(int id) {
        WkbUser wkbUser=new WkbUser();
        wkbUser.setOkcFlag((byte)2);
        wkbUser.setuId(id);
        int count=wkbUserDao.updateUser(wkbUser);
        if(count==1)
            return true;
        return false;
    }

    @Override
    public WkbUser pwd(WkbUser user) {
        WkbUser wkbUser=wkbUserDao.getUser(user.getuId());
        wkbUser.setuPwd(user.getuPwd());
        wkbUserDao.updateUser(wkbUser);
        return wkbUser;
    }

    @Override
    public boolean updateOrgByPK(WkbUser user) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<WkbUser> batchSave(WkbUser[] users) {
        List<WkbUser> wkbUserList=new ArrayList<WkbUser>();
        for(WkbUser wkbUser:users)
        {
            wkbUserDao.insertData(wkbUser);
            wkbUserList.add(wkbUser);
        }
        return wkbUserList;
    }

    @Override
    public WkbUser addAdmin(WkbUser user) {
        WkbUser wkbUser=user;
        wkbUser.setuAdmin((byte)1);
        wkbUser.setuPwd(MD5Utils.getMd5String(wkbUser.getuPwd()));
        wkbUserDao.insertData(wkbUser);
        return wkbUser;
    }

    @Override
    public List<WkbUser> getPageCompanyUsers(WkbUserQueryDto wkbUserQueryDto, Integer startPos, Integer endPos) {
        //mysql
        Integer pos=startPos-1;
        Integer size= endPos-pos;
        return wkbUserDao.getPageCompanyUsers(wkbUserQueryDto,pos,size);
    }

    @Override
    public Integer getCountCompanyUsers(WkbUserQueryDto wkbUserQueryDto) {
        return wkbUserDao.getCountCompanyUsers(wkbUserQueryDto);
    }

    @Override
    public int updateUsersByAdminAdd(List<Integer> uIdList, String cId) {
        int count=0;
        if(uIdList!=null)
        {

            List<Integer> noneUIdList=new ArrayList<Integer>();
            List<Integer> justUIdList=new ArrayList<Integer>();
            for(Integer uId:uIdList)
            {
                WkbUser wkbUser=wkbUserDao.getUser(uId);
                if(wkbUser!=null)
                {
                    if(wkbUser.getcId().equals(cId))
                    {
                        if(wkbUser.getOkcFlag()!=null)
                        {
                            if(wkbUser.getOkcFlag().intValue()==0)
                            {
                                noneUIdList.add(uId);
                            }
                            else
                            {
                                justUIdList.add(uId);
                            }
                        }
                    }
                }
            }
            if(noneUIdList.size()>0)
                count+=wkbUserDao.updateUsersByAdminAddAndApply(noneUIdList,cId);
            if(justUIdList.size()>0)
                count+=wkbUserDao.updateUsersByAdminAdd(justUIdList,cId);
        }
        return count;
    }

    @Override
    public int updateUsersByAdminDel(List<Integer> uIdList, String cId) {
        return wkbUserDao.updateUsersByAdminDel(uIdList,cId);
    }

    @Override
    public List<WkbUser> queryFuzzyByCompanyName(String companyName,int startPos,int endPos) {
        return wkbUserDao.queryFuzzyByCompanyName(companyName,startPos,endPos);
    }

    @Override
    public List<WkbUser> queryFuzzyByMobile(String mobile,int startPos,int endPos) {
        return wkbUserDao.queryFuzzyByMobile(mobile,startPos,endPos);
    }

    @Override
    public List<WkbUser> queryFuzzyByName(String name,int startPos,int endPos) {
        return wkbUserDao.queryFuzzyByName(name,startPos,endPos);
    }

    @Override
    public List<WkbUser> queryFuzzyByPosition(String position,int startPos,int endPos) {
        return wkbUserDao.queryFuzzyByPosition(position,startPos,endPos);
    }

    @Override
    public List<WkbUser> getPageCompanyFormalUsers(String companyId, Integer userId, Integer index, Integer pageSize) {
        int pos=(index-1)*pageSize;
        return wkbUserDao.getPageCompanyFormalUsers(companyId,userId,pos,pageSize);
    }

    @Override
    public int getCountCompanyFormalUsers(String companyId) {
        return wkbUserDao.getCountCompanyFormalUsers(companyId);
    }

    @Override
    public List<WkbUser> searchUsers(SocialitySearch socialitySearch, Integer index, Integer pageSize) {
        /*if(StringUtils.isBlank(socialitySearch.getSearchType())||StringUtils.isBlank(socialitySearch.getSearchValue()))
            return new ArrayList<WkbUser>();*/
        if(StringUtils.isBlank(socialitySearch.getSearchType()))
            socialitySearch.setSearchType("0");
        int pos=(index-1)*pageSize;
        return wkbUserDao.search(socialitySearch,pos,pageSize);
    }

    @Override
    public List<WkbUser> queryFriends(Integer userId, Integer startPos, Integer endPos) {
        return wkbUserDao.queryFriends(userId,startPos,endPos);
    }


    private static String getPlainText(String strEncrypt) throws Exception
    {
        if(StringUtils.isNotBlank(strEncrypt))
            return new String(MD5Util.decodeBytesHex(strEncrypt),"UTF-8");
        else
            return strEncrypt;
    }
}
