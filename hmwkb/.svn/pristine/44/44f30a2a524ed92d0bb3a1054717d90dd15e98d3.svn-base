package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.dao.WkbOrganizationDao;
import com.heima.dao.WkbOrgpostDao;
import com.heima.dao.WkbPostrightDao;
import com.heima.dto.WkbUserQueryDto;
import com.heima.model.WkbOrganization;
import com.heima.model.WkbOrgpost;
import com.heima.model.WkbPostright;
import com.heima.model.WkbUser;
import com.heima.service.WkbPostrightService;
import com.heima.service.WkbSeqService;
import com.heima.service.biz.TaskRight;
import com.heima.service.biz.WkbBizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbPostrightServiceImpl implements WkbPostrightService {

    @Autowired
    private WkbPostrightDao wkbPostrightDao;

    @Autowired
    private WkbOrganizationDao wkbOrganizationDao;

    @Autowired
    private WkbOrgpostDao wkbOrgpostDao;

    @Autowired
    private WkbSeqService wkbSeqService;

    @Override
    public List<WkbPostright> queryPostrightsByCompany(String companyId) {
        return wkbPostrightDao.getAllRigthByCompany(companyId);
    }

    @Override
    public void savePostrights(List<WkbPostright> wkbPostrightList) {
        for(WkbPostright wkbPostright:wkbPostrightList)
        {
            wkbPostrightDao.insertData(wkbPostright);
        }
    }

    @Override
    public int removePostright(Integer postId) {
        return wkbPostrightDao.deleteRightById(postId);
    }

    @Override
    public int updatePostright(WkbPostright wkbPostright) {
        return wkbPostrightDao.updatePostright(wkbPostright);
    }

    @Override
    public WkbPostright addRole(WkbPostright wkbPostright) {
        if(wkbPostright.getoId()==null||wkbPostright.getoId().intValue()<=0)
            return null;
        if(wkbPostright.getpRight()==null)
            wkbPostright.setpRight(TaskRight.LOWLEVEL.getRight());
        WkbOrganization wkbOrganization= wkbOrganizationDao.getWkbOrganization(wkbPostright.getoId());
        if(wkbOrganization==null)
        {
            throw new WkbBizException(WkbMessageEnum.ORA_PARENT_INVALID.getCode(),wkbPostright.getoId().toString());
        }
        if(wkbPostright.getCrtdatetime()==null)
        {
            wkbPostright.setCrtdatetime(new Date());
        }


        /*List<WkbPostright> roleList=wkbPostrightDao.getCompanyRoleByRoleId(wkbPostright.getpId(),wkbOrganization.getcId());
        if(roleList!=null&&roleList.size()>0)
        {
            throw new WkbBizException(WkbMessageEnum.ROLE_EXIST_ERROR.getCode(),wkbPostright.getoId().toString());
        }*/
        List<WkbPostright> wkbPostrightList=wkbPostrightDao.getAllRightByOrg(wkbPostright.getoId());
        if(wkbPostrightList!=null)
        {
            for(WkbPostright item:wkbPostrightList)
            {
                if(wkbPostright.getpName().equalsIgnoreCase(item.getpName()))
                    throw new WkbBizException(WkbMessageEnum.ROLE_EXIST_ERROR.getCode(),wkbPostright.getoId().toString());
            }
        }

        int seq=wkbSeqService.getRoleId();
        wkbPostright.setId(seq);
        wkbPostright.setpId(seq);
        wkbPostrightDao.insertData(wkbPostright);
        return wkbPostright;
    }

    @Override
    public WkbPostright getRole(Integer roleId) {
        return wkbPostrightDao.getRoleById(roleId);
    }

    @Override
    public boolean deleteRole(Integer roleId, boolean bLoop) {
        WkbPostright wkbPostright=wkbPostrightDao.getRoleById(roleId);
        if(wkbPostright==null)
        {
            throw new WkbBizException(WkbMessageEnum.ROLE_NOT_EXIST.getCode(),roleId.toString());
        }
        if(bLoop==false)
        {
            List<WkbOrgpost> wkbOrgpostList=wkbOrgpostDao.getUserRolesByRoleId(wkbPostright.getpId());
            if(wkbOrgpostList!=null&&wkbOrgpostList.size()>0)
                return false;
        }


        //用户回收
        wkbOrgpostDao.resetCompanyByRoleId(wkbPostright.getId());
        wkbOrgpostDao.removeUserRolesByRoleId(wkbPostright.getId());
        //删除
        wkbPostrightDao.deleteRightById(wkbPostright.getId());

        return true;
    }

    @Override
    public List<WkbOrgpost> queryUserRolesByRoleId(Integer roleId) {
        return wkbOrgpostDao.getUserRolesByRoleId(roleId);
    }

    @Override
    public List<WkbPostright> queryRolesByOrgId(Integer orgId) {
        return wkbPostrightDao.getAllRightByOrg(orgId);
    }

    @Override
    public List<WkbUser> queryPageUserRolesByRoleId(WkbUserQueryDto wkbUserQueryDto, Integer startPos, Integer endPos) {
        //mysql
        startPos=startPos-1;
        Integer size= endPos-startPos;
        return wkbOrgpostDao.getPageUserRolesByRoleId(wkbUserQueryDto,startPos,size);
    }

    @Override
    public Integer queryCountUserRolesByRoleId(WkbUserQueryDto wkbUserQueryDto) {
        return wkbOrgpostDao.getCountUserRolesByRoleId(wkbUserQueryDto);
    }
}
