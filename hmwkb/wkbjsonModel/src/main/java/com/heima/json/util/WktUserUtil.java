package com.heima.json.util;

import com.heima.json.JsonUser;
import com.heima.model.WkbUser;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-10
 */
public class WktUserUtil {
    public static void convert(WkbUser wkbUser,JsonUser jsonUser)
    {
        jsonUser.setUserId(wkbUser.getuId());
        jsonUser.setUserName(wkbUser.getuIdentifier());
        jsonUser.setNickName(wkbUser.getuName());
        if(wkbUser.getuSex()!=null)
            jsonUser.setGender(wkbUser.getuSex().intValue());
        jsonUser.setTelephone(wkbUser.getuTel());
        jsonUser.setCompany(wkbUser.getuCompany());
        jsonUser.setPosition(wkbUser.getuTitle());
        if(!StringUtils.isEmpty(wkbUser.getuIncome()))
            jsonUser.setIncome(Integer.parseInt(wkbUser.getuIncome()));
        jsonUser.setEmail(wkbUser.getuEmail());
        jsonUser.setAddress(wkbUser.getuAddr());
        jsonUser.setPostcode(wkbUser.getuZipcode());
        jsonUser.setFax(wkbUser.getuFax());
        jsonUser.setImageUrl(wkbUser.getImageUrl());
        jsonUser.setMobile(wkbUser.getuMobile());
        if(!StringUtils.isEmpty(wkbUser.getcId()))
            jsonUser.setCompanyId(wkbUser.getcId());
    }

    public static void mergeFromJson(WkbUser wkbUser,JsonUser jsonUser)
    {
        if(jsonUser.getUserId()!=null)
            wkbUser.setuId(jsonUser.getUserId());
        if(!StringUtils.isEmpty(jsonUser.getUserName()))
            wkbUser.setuIdentifier(jsonUser.getUserName());
        if(!StringUtils.isEmpty(jsonUser.getNickName()))
            wkbUser.setuName(jsonUser.getNickName());
        if(jsonUser.getGender()!=null)
            wkbUser.setuSex(jsonUser.getGender().byteValue());
        if(!StringUtils.isEmpty(jsonUser.getTelephone()))
            wkbUser.setuTel(jsonUser.getTelephone());
        if(!StringUtils.isEmpty(jsonUser.getCompany()))
            wkbUser.setuCompany(jsonUser.getCompany());
        if(!StringUtils.isEmpty(jsonUser.getPosition()))
            wkbUser.setuTitle(jsonUser.getPosition());
        if(jsonUser.getIncome()!=null)
            wkbUser.setuIncome(String.valueOf(jsonUser.getIncome()));
        if(!StringUtils.isEmpty(jsonUser.getEmail()))
            wkbUser.setuEmail(jsonUser.getEmail());
        if(!StringUtils.isEmpty(jsonUser.getAddress()))
            wkbUser.setuAddr(jsonUser.getAddress());
        if(!StringUtils.isEmpty(jsonUser.getPostcode()))
            wkbUser.setuZipcode(jsonUser.getPostcode());
        if(!StringUtils.isEmpty(jsonUser.getFax()))
            wkbUser.setuFax(jsonUser.getFax());
        if(!StringUtils.isEmpty(jsonUser.getImageUrl()))
            wkbUser.setImageUrl(jsonUser.getImageUrl());
        if(!StringUtils.isEmpty(jsonUser.getMobile()))
            wkbUser.setuMobile(jsonUser.getMobile());

    }
}
