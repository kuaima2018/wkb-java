package com.heima.service.impl;

import com.heima.common.WkbMessageEnum;
import com.heima.dao.WkbCompanyapplyDao;
import com.heima.dto.WkbCompanyapplyQueryDto;
import com.heima.model.WkbCompanyapply;
import com.heima.service.WkbCompanyapplyService;
import com.heima.service.biz.WkbBizException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class WkbCompanyapplyServiceImpl implements WkbCompanyapplyService {

    @Autowired
    private WkbCompanyapplyDao wkbCompanyapplyDao;

    @Override
    public void saveCompanyApply(WkbCompanyapply wkbCompanyapply) {
        if(StringUtils.isBlank(wkbCompanyapply.getcId())&&StringUtils.isBlank(wkbCompanyapply.getcName()))
        {
            throw new WkbBizException(WkbMessageEnum.COMPANY_APPLY_NAME_INVALID.getCode(),"");
        }
        if(wkbCompanyapply.getCreator()==null)
        {
            throw new WkbBizException(WkbMessageEnum.USER_NO_ID.getCode(),"");
        }

        List<WkbCompanyapply> wkbCompanyapplyList=wkbCompanyapplyDao.findCompanyByName(wkbCompanyapply);
        if(wkbCompanyapplyList.size()>0)
        {
            throw new WkbBizException(WkbMessageEnum.COMPANY_APPLY_NAME_EXIST.getCode(),"");
        }

        wkbCompanyapplyDao.insertData(wkbCompanyapply);
    }

    @Override
    public List<WkbCompanyapply> queryCompanyapply(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto) {
        Integer startPos=wkbCompanyapplyQueryDto.getStartPos()-1;
        Integer size= wkbCompanyapplyQueryDto.getEndPos()-startPos;
        wkbCompanyapplyQueryDto.setStartPos(startPos);
        wkbCompanyapplyQueryDto.setEndPos(size);
        return wkbCompanyapplyDao.queryCompanyapply(wkbCompanyapplyQueryDto);
    }

    @Override
    public Integer queryCountCompanyapply(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto) {
        return wkbCompanyapplyDao.queryCountCompanyapply(wkbCompanyapplyQueryDto);
    }

    @Override
    public WkbCompanyapply getCompanyapply(Integer companyapplyId) {
        return wkbCompanyapplyDao.getCompanyapply(companyapplyId);
    }

    @Override
    public boolean agreeCompanyapply(Integer companyapplyId) {
        int count=wkbCompanyapplyDao.updateCompanyapplyStatus(companyapplyId,"0","1");
        if(count==1)
            return true;
        return false;
    }

    @Override
    public boolean removeCompanyapply(Integer companyapplyId) {
        WkbCompanyapply wkbCompanyapply=wkbCompanyapplyDao.getCompanyapply(companyapplyId);
        if(wkbCompanyapply==null)
        {
            throw new WkbBizException(WkbMessageEnum.COMPANY_APPLY_NO_EXIST.getCode(),"");
        }
        if("2".equals(wkbCompanyapply.getStatus()))
        {
            throw new WkbBizException(WkbMessageEnum.COMPANY_APPLY_HANDLED.getCode(),"");
        }
        int count=wkbCompanyapplyDao.updateCompanyapplyStatus(companyapplyId,wkbCompanyapply.getStatus(),"2");
        if(count==1)
            return true;
        return false;
    }
}
