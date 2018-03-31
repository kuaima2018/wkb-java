package com.heima.web.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 2015/4/25
 */
public class ListHelper {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ListHelper.class);

    public static List<Integer> parseStr(String str)
    {
        if(StringUtils.isNotBlank(str))
        {
            List<Integer>  list=new ArrayList<Integer>();
            String[] strs=str.split(",");
            for(String item : strs)
            {
                try {
                    list.add(Integer.parseInt(item));
                }catch (Exception exp)
                {
                    logger.error("str value is error:"+item);
                    return null;
                }
            }
            return list;
        }
        return null;
    }
}
