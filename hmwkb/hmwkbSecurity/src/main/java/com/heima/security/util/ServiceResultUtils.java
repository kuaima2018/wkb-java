package com.heima.security.util;

import com.heima.security.common.ServiceResultCode;
import com.heima.security.model.ServiceResult;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-12-15
 * Time: 下午10:07
 * To change this template use File | Settings | File Templates.
 */
public class ServiceResultUtils {
    public static boolean isSucc(ServiceResult serviceResult)
    {
        if(serviceResult==null)
            return false;
        if(serviceResult.getCode()==null||serviceResult.getCode().intValue()==0)
            return true;
        return false;
    }

    public static boolean isSystemError(ServiceResult serviceResult)
    {
        if(serviceResult!=null&&serviceResult.getCode().intValue()==ServiceResultCode.SYSTEM_UNKNOWN_ERROR.getIndex())
            return true;
        return false;
    }

    public static String getDescFromCode(int code)
    {
        ServiceResultCode src=ServiceResultCode.getFromIndex(code);
        if(src!=null)
        {
            return src.getName();
        }
        else
            return "";
    }
}
