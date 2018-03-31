package com.heima.web.service.impl;

import com.heima.service.AuthCodeService;
import com.heima.web.service.AuthcodeManagerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 15-3-10
 */
@Service
public class AuthcodeManagerServiceImpl implements AuthcodeManagerService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuthcodeManagerServiceImpl.class);

    @Autowired
    private AuthCodeService authCodeService;

    /*@Value("${com.heima.sms.server.ip}")
    private String serverIp;
    @Value("${com.heima.sms.server.port}")
    private String serverPort;
    @Value("${com.heima.sms.account.sid}")
    private String accountSid;
    @Value("${com.heima.sms.account.token}")
    private String accountToken;
    @Value("${com.heima.sms.appid}")
    private String appId;
    @Value("${com.heima.sms.template}")
    private String templateNo; */


    @Value("${com.heima.sms.url}")
    private String smsUrl;
    @Value("${com.heima.sms.name}")
    private String smsName;
    @Value("${com.heima.sms.password}")
    private String smsPassword;

    @Autowired
    @Qualifier("payRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("smsId")
    private String smsTemplate;


    @Override
    public void genAuthcodeAndSend(String mobile) {
        Map<String,String> map=authCodeService.genAuthCode(mobile,4);
        //wkt-发送验证码到手机
        String bSucc=map.get("result");
        if("1".equals(bSucc))
            return;

        String authCode=map.get("code");

        String strContent=String.format(smsTemplate,authCode);
//        String strContent=String.format("",authCode);
        String strUrl=String.format("%s?name=%s&pwd=%s&content=%s&mobile=%s&type=pt",
                smsUrl,smsName,smsPassword,strContent, mobile);

        logger.debug(String.format("mobile:%s-authcode:%s",mobile,authCode));

        String result;
        try{
            ResponseEntity<String> response=restTemplate.postForEntity(strUrl,null,String.class);
            result=response.getBody();
        }catch (Exception exp)
        {
            logger.error("send msg error:", exp);
            throw new RuntimeException(exp.getMessage());
        }

        this.parseResult(result);
        /*HashMap<String, Object> result = null;
        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init(serverIp, serverPort);
        restAPI.setAccount(accountSid, accountToken);
        restAPI.setAppId(appId);
        result = restAPI.sendTemplateSMS(mobile, templateNo ,new String[]{authCode,"30"});
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                logger.debug(key +" = "+object);
            }
        }else{
            //异常返回输出错误码和错误信息
            String errorMsg="错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg");

            logger.error("send sms error:"+errorMsg);
            throw new RuntimeException(errorMsg);
        }*/
    }

    @Override
    public int authenticate(String mobile, String authCode) {
        /*if("12345678900".equals(mobile))
            return 0;*/
        return authCodeService.authenticate(mobile,authCode);
    }

    private void parseResult(String result)
    {
        if(StringUtils.isBlank(result))
        {
            logger.error("send msg error:no result");
            throw new RuntimeException("未发送成功");
        }
        else
        {
            String[] values=result.split(",");
            if(values==null||values.length<=0)
            {
                logger.error("send msg error2:no result");
                throw new RuntimeException("未发送成功");
            }
            else
            {
                int code=Integer.parseInt(values[0]);
                if(logger.isDebugEnabled())
                {
                    for(String item:values)
                    {
                        logger.debug("sms result value:"+item);
                    }
                }
                if(code!=0)
                {
                    throw new RuntimeException(values[values.length-1]);
                }
            }
        }
    }
}
