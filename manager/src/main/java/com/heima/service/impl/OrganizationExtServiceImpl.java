package com.heima.service.impl;

import com.heima.model.WkbOrganization;
import com.heima.model.WkbOrgpost;
import com.heima.model.WkbPostright;
import com.heima.model.WkbUser;
import com.heima.service.*;
import com.heima.tree.Node;
import com.heima.tree.utils.TreeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-3
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrganizationExtServiceImpl implements OrganizationExtService {

    @Autowired
    private WkbOrganizationService wkbOrganizationService;

    @Autowired
    private WkbPostrightService wkbPostrightService;

    @Autowired
    private WkbUserRoleService wkbUserRoleService;


    @Override
    public List<Node> treeOrg(String companyId) {
        HashMap<Integer, Node> nodeList = new HashMap<Integer, Node>();
        // 根节点
        Node root = null;
        List<Map<Object, Object>> dataList = this.getOrgsByCompany(companyId);
        for (Map<Object, Object> map : dataList) {
            Node node = new Node();
            node.id = (Integer) map.get(TreeConstants.ORG_ID);
            node.text = (String) map.get(TreeConstants.TEXT);
            node.parentId = (Integer) map.get(TreeConstants.PARENT_ID);
            node.type = (Integer) map.get(TreeConstants.TYPE);
            nodeList.put(node.id, node);
        }
        // 构造无序的多叉树
        Set<Map.Entry<Integer, Node>> entrySet = nodeList.entrySet();
        for (Map.Entry<Integer, Node> entry : entrySet) {
            Node node = entry.getValue();
            if (node.type == 0 && node.parentId == node.id) {
                root = node;
            } else {
                if (null != nodeList.get(node.parentId)) {
                    nodeList.get(node.parentId).addChild(node);
                }
            }
        }
        List<Node> treeList = new ArrayList<Node>();
        treeList.add(root);
        return treeList;
    }

    //@Override
    public List<Map<Object, Object>> getOrgsByCompany(String cId) {
        List<WkbOrganization> orgList = wkbOrganizationService.getOrganizationsByCompany(cId);
        List<Map<Object, Object>> mapList = new ArrayList<Map<Object, Object>>();
        for (WkbOrganization org : orgList) {
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            map.put(TreeConstants.ID, org.getId());
            map.put(TreeConstants.ORG_ID, org.getoId());
            map.put(TreeConstants.TEXT, org.getoId().toString());
            map.put(TreeConstants.PARENT_ID, org.getoFatherId()!=null?org.getoFatherId():0);
            map.put(TreeConstants.TYPE, 0);
            mapList.add(map);
        }

        List<WkbPostright> postList=wkbPostrightService.queryPostrightsByCompany(cId);

        for (WkbPostright post : postList) {
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            map.put(TreeConstants.ID, post.getId());
            map.put(TreeConstants.ORG_ID, post.getpId());
            map.put(TreeConstants.TEXT, post.getpName());
            map.put(TreeConstants.PARENT_ID, post.getoId());
            map.put(TreeConstants.TYPE, 1);
            mapList.add(map);
        }


        List<WkbUser> userList = wkbOrganizationService.getUsersByCompany(cId);
        List<WkbOrgpost> userRoleList=wkbUserRoleService.getUserRolesByCompany(cId);
        for (WkbUser user : userList) {
            HashMap<Object, Object> map = new HashMap<Object, Object>();
            map.put(TreeConstants.ID, user.getuId());
            map.put(TreeConstants.ORG_ID, user.getuId());
            map.put(TreeConstants.TEXT, user.getuName());
            for(WkbOrgpost userRole:userRoleList)
            {
                if(userRole.getuId().equals(user.getuId()))
                {
                    map.put(TreeConstants.PARENT_ID, userRole.getId());
                    map.put(TreeConstants.TYPE, 2);
                    mapList.add(map);
                    break;
                }
            }

        }
        return mapList;
    }

}
