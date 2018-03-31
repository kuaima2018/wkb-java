package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.dao.WkbOrganizationDao;
import com.heima.dao.WkbPostrightDao;
import com.heima.dao.model.OrgRight;
import com.heima.model.WkbOrganization;
import com.heima.model.WkbPostright;
import com.heima.model.WkbUser;
import com.heima.service.WkbOrganizationService;
import com.heima.service.WkbPostrightService;
import com.heima.service.WkbSeqService;
import com.heima.service.biz.WkbBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-15
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbOrganizationServiceImpl implements WkbOrganizationService {

    @Autowired
    private WkbOrganizationDao wkbOrganizationDao;

    @Autowired
    private WkbPostrightDao wkbPostrightDao;
    @Autowired
    private WkbSeqService wkbSeqService;

    @Autowired
    private WkbPostrightService wkbPostrightService;

    @Override
    public List<WkbUser> queryOrganizationUsers(Integer orgId) {
        return wkbOrganizationDao.queryOrganizationUsers(orgId);
    }

    @Override
    public OrgRight queryRight(WkbUser wkbUser) {
        return wkbOrganizationDao.queryRight(wkbUser);
    }

    @Override
    public List<WkbOrganization> getTopOrganizations(String companyId) {
        return wkbOrganizationDao.queryTopOrgs(companyId);
    }

    @Override
    public List<WkbOrganization> getChilds(Integer orgId) {
        return wkbOrganizationDao.queryChilds(orgId);
    }

    @Override
    public Map<Integer, WkbOrganization> queryCompanyUserOrganizations(List<WkbUser> wkbUserList) {
        Map<Integer, WkbOrganization> map=new HashMap<Integer, WkbOrganization>();
        List<OrgRight> orgRightList= wkbOrganizationDao.queryCompanyFriends(wkbUserList);
        if(orgRightList!=null&&orgRightList.size()>0)
        {
            for(OrgRight orgRight:orgRightList)
            {
                if(!map.containsKey(orgRight.getuId()))
                {
                    map.put(orgRight.getuId(),orgRight);
                }
            }
        }
        return map;
    }

    @Override
    public List<WkbOrganization> saveOrgs(List<WkbOrganization> wkbOrganizationList) {
        if(wkbOrganizationList!=null)
        {
            for(WkbOrganization wkbOrganization:wkbOrganizationList)
            {
                if(wkbOrganization.getCrtdatetime()==null)
                    wkbOrganization.setCrtdatetime(new Date());
                /*if(wkbOrganization.getCreator()==null)
                    wkbOrganization.setCreator(null);*/
                wkbOrganizationDao.insertData(wkbOrganization);
            }
        }
        return wkbOrganizationList;
    }

    @Override
    public WkbOrganization queryOrganization(Integer orgId) {
        return wkbOrganizationDao.getWkbOrganization(orgId);
    }

    @Override
    public void deleteOrganization(Integer orgId) {
        wkbOrganizationDao.delWkbOrganization(orgId);
    }

    @Override
    public boolean upateOrganization(WkbOrganization wkbOrganization) {
        int count=wkbOrganizationDao.updateWkbOrganization(wkbOrganization);
        if(count>0)
            return true;
        return false;
    }

    @Override
    public List<WkbOrganization> getOrganizationsByCompany(String companyId) {
        return wkbOrganizationDao.getAllOrgsByCompany(companyId);
    }

    @Override
    public List<WkbUser> getUsersByCompany(String companyId) {
        return wkbOrganizationDao.getAllUsersByCompany(companyId);
    }

    @Override
    public WkbOrganization addOrganization(WkbOrganization wkbOrganization) {
        //如果同一级中，相同名称存在，那么报错
        /*List<WkbOrganization> wkbOrganizationList=wkbOrganizationDao.queryCompanyOrgByName(wkbOrganization.getcId(),wkbOrganization.getoId());
        if(wkbOrganizationList!=null&&wkbOrganizationList.size()>0)
        {
            throw new WkbBizException(WkbMessageEnum.ORA_EXIST_ERROR.getCode(),"");
        }*/
        List<WkbOrganization> wkbOrganizationList;
        if(wkbOrganization.getoFatherId()!=null&&wkbOrganization.getoFatherId().intValue()>0)
        {
            wkbOrganizationList=wkbOrganizationDao.queryChilds(wkbOrganization.getoFatherId());
        }
        else
        {
            wkbOrganizationList=wkbOrganizationDao.queryTopOrgs(wkbOrganization.getcId());
        }
        if(wkbOrganizationList!=null)
        {
            for(WkbOrganization item:wkbOrganizationList)
            {
                if(wkbOrganization.getoName().equalsIgnoreCase(item.getoName()))
                {
                    throw new WkbBizException(WkbMessageEnum.ORA_EXIST_ERROR.getCode(),"");
                }
            }
        }

        if(wkbOrganization.getCrtdatetime()==null)
            wkbOrganization.setCrtdatetime(new Date());
        if(wkbOrganization.getoFatherId()!=null&&wkbOrganization.getoFatherId().intValue()<=0)
            wkbOrganization.setoFatherId(null);

        if(wkbOrganization.getoFatherId()!=null&&wkbOrganization.getoFatherId().intValue()>0)
        {
            WkbOrganization wkbOrganizationParent=wkbOrganizationDao.getWkbOrganization(wkbOrganization.getoFatherId());
            if(wkbOrganizationParent==null)
            {
                throw new WkbBizException(WkbMessageEnum.ORA_NO_PARENT.getCode(),"");
            }
            else
            {
                if(!wkbOrganization.getcId().equals(wkbOrganizationParent.getcId()))
                {
                    throw new WkbBizException(WkbMessageEnum.ORA_PARENT_INVALID.getCode(),"");
                }
            }
        }

        int seq=wkbSeqService.getOrganizationId();
        wkbOrganization.setId(seq);
        wkbOrganization.setoId(String.valueOf(seq));
        wkbOrganizationDao.insertData(wkbOrganization);
        return wkbOrganization;
    }

    @Override
    public boolean deleteOrganization(Integer orgId, boolean bLoop) {
        if(bLoop==false)
        {
            List<WkbOrganization> wkbOrganizationList=wkbOrganizationDao.queryChilds(orgId);
            if (wkbOrganizationList!=null&&wkbOrganizationList.size()>0)
                return false;

            List<WkbPostright> wkbPostrightList=wkbPostrightDao.getAllRightByOrg(orgId);
            if(wkbPostrightList!=null&&wkbPostrightList.size()>0)
                return false;
        }

        //1.先删除组织里面的岗位，然后删除子组织
        this.removeOrganization(orgId);
        return true;
    }

    private void removeOrganization(Integer orgId)
    {
        List<WkbOrganization> wkbOrganizationList=wkbOrganizationDao.queryChilds(orgId);
        if (wkbOrganizationList!=null&&wkbOrganizationList.size()>0)
        {
            for(WkbOrganization child:wkbOrganizationList)
            {
                this.removeOrganization(child.getId());
            }
        }
        //删除岗位
        List<WkbPostright> wkbPostrightList=wkbPostrightDao.getAllRightByOrg(orgId);
        if(wkbPostrightList!=null&&wkbPostrightList.size()>0)
        {
            for (WkbPostright role :wkbPostrightList)
            {
                wkbPostrightService.deleteRole(role.getId(),true);
            }
        }

        this.deleteOrganization(orgId);
    }
}
