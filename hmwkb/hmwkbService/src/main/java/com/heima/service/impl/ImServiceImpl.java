package com.heima.service.impl;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.heima.common.WkbMessageEnum;
import com.heima.im.comm.Constants;
import com.heima.im.comm.HTTPMethod;
import com.heima.im.utils.HTTPClientUtils;
import com.heima.im.vo.ClientSecretCredential;
import com.heima.im.vo.Credential;
import com.heima.im.vo.EndPoints;
import com.heima.service.ImService;
import com.heima.service.biz.WkbBizException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Created by xuzhikai on 2015/9/5.
 */
@Service
public class ImServiceImpl implements ImService {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ImServiceImpl.class);

    private static final JsonNodeFactory factory = new JsonNodeFactory(false);

    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, "appAdmin");

    @Override
    public void registerUser(String userId, String userName, String userPassword) {
        ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username",userId);
        datanode.put("password", userPassword);
        datanode.put("nickname", userName);
        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
        if(createNewIMUserSingleNode!=null){
            //判断是否成功
            if(createNewIMUserSingleNode.get("statusCode").asInt()!= HttpStatus.SC_OK)
            {
                LOGGER.error("register user error:"+userId);
                LOGGER.error("register user error: " + createNewIMUserSingleNode.toString());
                throw new RuntimeException(createNewIMUserSingleNode.get("error").asText());
            }
        }
    }

    @Override
    public void resetUser(String userId, String userName, String userPassword) {
        if(StringUtils.isNotBlank(userName)){
            ObjectNode json2 = JsonNodeFactory.instance.objectNode();
            json2.put("nickname", userName);
            ObjectNode modifyIMUserPasswordWithAdminTokenNode = modifyIMUserNicknameWithAdminToken(userId, json2);
            if (null != modifyIMUserPasswordWithAdminTokenNode) {
                if(modifyIMUserPasswordWithAdminTokenNode.get("statusCode").asInt()!= HttpStatus.SC_OK) {
                    LOGGER.info("reset user nick name error: " + userId);
                    LOGGER.info("reset user nick name error: " + modifyIMUserPasswordWithAdminTokenNode.toString());
                    throw new RuntimeException(modifyIMUserPasswordWithAdminTokenNode.get("error").asText());
                }
            }
        }
        if(StringUtils.isNotBlank(userPassword)){
            ObjectNode json2 = JsonNodeFactory.instance.objectNode();
            json2.put("newpassword", userPassword);
            ObjectNode modifyIMUserPasswordWithAdminTokenNode = modifyIMUserPasswordWithAdminToken(userId, json2);
            if (null != modifyIMUserPasswordWithAdminTokenNode) {
                if(modifyIMUserPasswordWithAdminTokenNode.get("statusCode").asInt()!= HttpStatus.SC_OK) {
                    LOGGER.info("reset user password error: " + userId);
                    LOGGER.info("reset user password error: " + modifyIMUserPasswordWithAdminTokenNode.toString());
                    throw new RuntimeException(modifyIMUserPasswordWithAdminTokenNode.get("error").asText());
                }
            }
        }
    }

    @Override
    public void removeUser(String userId) {
        ObjectNode removeIMUserByuserNameNode = removeIMUserByuserName(userId);
        if(removeIMUserByuserNameNode!=null){
            if(removeIMUserByuserNameNode.get("statusCode").asInt()!= HttpStatus.SC_OK) {
                LOGGER.info("remove user error: " + userId);
                LOGGER.info("remove user error: " + removeIMUserByuserNameNode.toString());
                throw new RuntimeException(removeIMUserByuserNameNode.get("error").asText());
            }
        }
    }

    public static ObjectNode createNewIMUserSingle(ObjectNode dataNode) {

        ObjectNode objectNode = factory.objectNode();

        // check Constants.APPKEY format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Constants.APPKEY");

            return objectNode;
        }

        objectNode.removeAll();

        // check properties that must be provided
        if (null != dataNode && !dataNode.has("username")) {
            LOGGER.error("Property that named username must be provided .");

            objectNode.put("message", "Property that named username must be provided .");

            return objectNode;
        }
        if (null != dataNode && !dataNode.has("password")) {
            LOGGER.error("Property that named password must be provided .");

            objectNode.put("message", "Property that named password must be provided .");

            return objectNode;
        }

        try {

            objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataNode,
                    HTTPMethod.METHOD_POST);

        } catch (Exception e) {
            LOGGER.error("register user error:",e);
            throw new RuntimeException(e.getMessage());
        }

        return objectNode;
    }

    public static ObjectNode modifyIMUserPasswordWithAdminToken(String userName, ObjectNode dataObjectNode) {
        ObjectNode objectNode = factory.objectNode();

        // check Constants.APPKEY format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Constants.APPKEY");

            return objectNode;
        }

        if (StringUtils.isEmpty(userName)) {
            LOGGER.error("Property that named userName must be provided，the value is username of imuser.");

            objectNode.put("message",
                    "Property that named userName must be provided，the value is username or imuser.");

            return objectNode;
        }

        if (null != dataObjectNode && !dataObjectNode.has("newpassword")) {
            LOGGER.error("Property that named newpassword must be provided .");

            objectNode.put("message", "Property that named newpassword must be provided .");

            return objectNode;
        }

        try {
            URL modifyIMUserPasswordWithAdminTokenUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/")
                    + "/users/" + userName + "/password");
            objectNode = HTTPClientUtils.sendHTTPRequest(modifyIMUserPasswordWithAdminTokenUrl, credential,
                    dataObjectNode, HTTPMethod.METHOD_PUT);

        } catch (Exception e) {
            LOGGER.error("reset user password error:", e);
            throw new RuntimeException(e.getMessage());
        }

        return objectNode;
    }

    public static ObjectNode modifyIMUserNicknameWithAdminToken(String userName, ObjectNode dataObjectNode) {
        ObjectNode objectNode = factory.objectNode();

        // check Constants.APPKEY format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Constants.APPKEY");

            return objectNode;
        }

        if (StringUtils.isEmpty(userName)) {
            LOGGER.error("Property that named userName must be provided，the value is username of imuser.");

            objectNode.put("message",
                    "Property that named userName must be provided，the value is username or imuser.");

            return objectNode;
        }

        try {
            URL modifyIMUserNicknameWithAdminTokenUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/")
                    + "/users/" + userName);
            objectNode = HTTPClientUtils.sendHTTPRequest(modifyIMUserNicknameWithAdminTokenUrl, credential,
                    dataObjectNode, HTTPMethod.METHOD_PUT);

        } catch (Exception e) {
            LOGGER.error("reset user nick name error:", e);
            throw new RuntimeException(e.getMessage());
        }

        return objectNode;
    }

    public static ObjectNode removeIMUserByuserName(String userName) {
        ObjectNode objectNode = factory.objectNode();

        // check Constants.APPKEY format
        if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
            LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

            objectNode.put("message", "Bad format of Constants.APPKEY");

            return objectNode;
        }

        try {

            URL deleteUserPrimaryUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users/"
                    + userName);
            objectNode = HTTPClientUtils.sendHTTPRequest(deleteUserPrimaryUrl, credential, null,
                    HTTPMethod.METHOD_DELETE);

        } catch (Exception e) {
            LOGGER.error("remove user error:",e);
            throw new RuntimeException(e.getMessage());
        }

        return objectNode;
    }
}
