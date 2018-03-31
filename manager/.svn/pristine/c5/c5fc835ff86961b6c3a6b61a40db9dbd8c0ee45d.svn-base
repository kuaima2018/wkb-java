package com.heima.controller;

import com.heima.model.WkbCompany;
import com.heima.model.WkbOrganization;
import com.heima.model.WkbPostright;
import com.heima.model.WkbUser;
import com.heima.service.*;
import com.heima.service.biz.TaskRight;
import com.heima.service.biz.WkbBizException;
import com.heima.tree.*;
import com.heima.util.ProdInfo;
import com.heima.util.WkbNodeStatus;
import net.sf.json.JSONSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping(value = "tree")
public class TreeController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TreeController.class);

    @Autowired
    private WkbOrganizationService wkbOrganizationService;

    @Autowired
    private WkbCompanyService wkbCompanyService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private WkbPostrightService wkbPostrightService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WkbSecurityService wkbSecurityService;

    @Autowired
    private CompanyIndustyService companyIndustyService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView show() {
        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
        String companyId=wkbUser.getcId();
        /*Map map = request.getParameterMap();*/
        ModelAndView mav=new ModelAndView("wkbdemo");
        mav.addObject("companyId",companyId);
        mav.addObject("name",wkbUser.getuName());
        if(wkbUser.getuAdmin()!=null) {
            if(wkbUser.getuAdmin().intValue()==2) {
                mav.addObject("systemManager", true);
                mav.addObject("companyManager", false);
            }
            else if(wkbUser.getuAdmin().intValue()==1) {
                mav.addObject("systemManager", false);
                mav.addObject("companyManager", true);
            }
            else
            {
                mav.addObject("systemManager", false);
                mav.addObject("companyManager", false);
            }
        }
        //获取行业信息
        List<Map<String, String>> industyValList=new ArrayList<Map<String, String>>();
        Map<String,String> mapData;
        Map<Integer,String> itemMap=companyIndustyService.getIndustyData();
        for(Map.Entry<Integer,String> entry:itemMap.entrySet())
        {
            mapData=new HashMap<String, String>();
            mapData.put("id",String.valueOf(entry.getKey()));
            mapData.put("val",entry.getValue());
            industyValList.add(mapData);
        }

        mav.addObject("industyValList", JSONSerializer.toJSON(industyValList));
        return mav;
    }

    @RequestMapping(value = "/nodes/top/{companyId}", method = RequestMethod.POST)
    @ResponseBody
    public WkbOrgNodesData getTopNodes(@PathVariable String companyId)
    {
        Integer index=1;
        //String companyId="1";
        WkbOrgNodesData wkbOrgNodesData=new WkbOrgNodesData();
        WkbCompany wkbCompany=wkbCompanyService.findCompany(companyId);
        if(wkbCompany==null)
            return wkbOrgNodesData;

        WkbUser currentUser=wkbSecurityService.getCurrentUser();
        if(!wkbCompany.getcId().equals(currentUser.getcId()))
        {
            if(currentUser.getuAdmin()!=null&&currentUser.getuAdmin().intValue()!=2)
            {
                logger.error("fetch company error:"+wkbCompany.getcId()+"-"+currentUser.getuId());
                return wkbOrgNodesData;
            }
        }
        WkbOrgNode companyNode=new WkbOrgNode(index++, wkbCompany.getcName());
        Map<String, String> attributes=new HashMap<String, String>();
        attributes.put("type","1");
        attributes.put("nid",wkbCompany.getcId().toString());
        companyNode.setAttributes(attributes);
        companyNode.setState(WkbNodeStatus.Node_OPEN);
        companyNode.setIconCls("icon-company");

        List<WkbOrganization> wkbOrganizationList=wkbOrganizationService.getTopOrganizations(companyId);
        if(wkbOrganizationList==null||wkbOrganizationList.size()==0)
        {
        }
        else
        {
            companyNode.setChildren(new ArrayList<WkbOrgNode>());
            for(WkbOrganization wkbOrganization:wkbOrganizationList)
            {
                WkbOrgNode orgNode=new WkbOrgNode(index++, wkbOrganization.getoName());
                attributes=new HashMap<String, String>();
                attributes.put("type","2");
                attributes.put("nid",wkbOrganization.getId().toString());
                orgNode.setAttributes(attributes);
                orgNode.setState(WkbNodeStatus.Node_CLOSE);
                orgNode.setIconCls("icon-organization");

                companyNode.getChildren().add(orgNode);
            }
        }

        List<WkbOrgNode> list=new ArrayList<WkbOrgNode>();
        list.add(companyNode);


        wkbOrgNodesData.setIndex(index);
        wkbOrgNodesData.setNodeList(list);
        return wkbOrgNodesData;
    }

    @RequestMapping(value = "/nodes/fetch", method = RequestMethod.POST)
    @ResponseBody
    public WkbOrgNodesData getNodes(HttpServletRequest request, @RequestBody WkbOrgNodeExt wkbOrgNodePost)
    {
        WkbOrgNodesData wkbOrgNodesData=new WkbOrgNodesData();

        Integer index=wkbOrgNodePost.getIndex();
        if(wkbOrgNodePost.getAttributes().containsKey("type"))
        {
            String type=wkbOrgNodePost.getAttributes().get("type");
            if(type.equals("2"))  //组织
            {
                //获取下级组织和岗位
                wkbOrgNodesData.setNodeList(new ArrayList<WkbOrgNode>());

                Integer orgId=Integer.parseInt(wkbOrgNodePost.getAttributes().get("nid"));

                //先显示岗位，后显示子部门
                List<WkbPostright> roleList=wkbPostrightService.queryRolesByOrgId(orgId);
                if(roleList!=null)
                {
                    for(WkbPostright role :roleList)
                    {
                        WkbOrgNode roleNode=new WkbOrgNode(index++,role.getpName());
                        Map<String, String> attributes=new HashMap<String, String>();
                        attributes.put("type","3");
                        attributes.put("nid", role.getId().toString());
                        attributes.put("right", role.getpRight() != null ? role.getpRight().toString() : TaskRight.LOWLEVEL.getRight().toString());
                        roleNode.setAttributes(attributes);
                        roleNode.setState(WkbNodeStatus.Node_OPEN);
                        roleNode.setIconCls("icon-role");

                        wkbOrgNodesData.getNodeList().add(roleNode);
                    }
                }
                List<WkbOrganization> wkbOrganizationList=wkbOrganizationService.getChilds(orgId);
                if(wkbOrganizationList!=null)
                {
                    for(WkbOrganization wkbOrganization:wkbOrganizationList)
                    {
                        WkbOrgNode orgNode=new WkbOrgNode(index++, wkbOrganization.getoName());
                        Map<String, String> attributes=new HashMap<String, String>();
                        attributes.put("type","2");
                        attributes.put("nid", wkbOrganization.getId().toString());
                        orgNode.setAttributes(attributes);
                        orgNode.setState(WkbNodeStatus.Node_CLOSE);
                        orgNode.setIconCls("icon-organization");

                        wkbOrgNodesData.getNodeList().add(orgNode);

                    }
                }

                wkbOrgNodesData.setIndex(index);
            }
            else if (type.equals("3"))//岗位
            {
                //获取岗位用户
                /*wkbOrgNodesData.setNodeList(new ArrayList<WkbOrgNode>());
                wkbOrgNodesData.setIndex(wkbOrgNodePost.getIndex());
                Integer roleId=Integer.parseInt(wkbOrgNodePost.getAttributes().get("nid"));
                initRoleUsers(roleId, wkbOrgNodesData);*/
            }
        }

        return wkbOrgNodesData;
    }

    private void initRoleUsers(Integer roleId, WkbOrgNodesData wkbOrgNodesData)
    {
        List<WkbUser> wkbUserList=userRoleService.queryUsersByRoleId(roleId);
        Integer index=wkbOrgNodesData.getIndex();
        if(wkbUserList!=null)
        {
            for(WkbUser wkbUser:wkbUserList)
            {
                WkbOrgNode userNode=new WkbOrgNode(index++,wkbUser.getuName());
                userNode.setState(WkbNodeStatus.Node_OPEN);
                Map<String, String> attributes=new HashMap<String, String>();
                attributes.put("type","4");
                attributes.put("nid", wkbUser.getuId().toString());
                userNode.setAttributes(attributes);

                wkbOrgNodesData.getNodeList().add(userNode);
            }
        }
        wkbOrgNodesData.setIndex(index);
    }

    @RequestMapping(value = "/org/add", method = RequestMethod.POST)
    @ResponseBody
    public WkbAddNodeResult addOrg(@RequestBody WkbOrganization wkbOrganization)
    {
        WkbAddNodeResult wkbAddNodeResult=new WkbAddNodeResult();
        try
        {
            wkbOrganization.setCreator(this.getCurrentUserId());
            WkbOrganization wkbOrganizationNew=wkbOrganizationService.addOrganization(wkbOrganization);
            Map<String, String> attributes=new HashMap<String, String>();
            attributes.put("type","2");
            attributes.put("nid", wkbOrganizationNew.getId().toString());
            wkbAddNodeResult.setAttributes(attributes);
            wkbAddNodeResult.setText(wkbOrganizationNew.getoName());

        }catch (WkbBizException wkbExp)
        {
            wkbAddNodeResult.setSucc("error");
            wkbAddNodeResult.setMsg(messageSource.getMessage(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            wkbAddNodeResult.setSucc("error");
            wkbAddNodeResult.setMsg("系统错误！");
        }
        return wkbAddNodeResult;
    }

    @RequestMapping(value = "/org/del", method = RequestMethod.POST)
    @ResponseBody
    public WkbDelNodeResult delOrg(@RequestParam Integer id,
                                   @RequestParam(required=false) Integer flag)
    {
        WkbDelNodeResult wkbDelNodeResult=new WkbDelNodeResult();
        boolean bLoop=false;
        if(flag!=null&&flag.intValue()==1)
            bLoop=true;
        try
        {
            boolean bSucc=wkbOrganizationService.deleteOrganization(id,bLoop);
            if(bSucc==false)
            {
                wkbDelNodeResult.setSucc("warn");
            }

        }catch (WkbBizException wkbExp)
        {
            wkbDelNodeResult.setSucc("error");
            wkbDelNodeResult.setMsg(messageSource.getMessage(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            wkbDelNodeResult.setSucc("error");
            wkbDelNodeResult.setMsg("系统错误！");
        }
        return wkbDelNodeResult;
    }

    @RequestMapping(value = "/role/add", method = RequestMethod.POST)
    @ResponseBody
    public WkbAddNodeResult addRole(@RequestBody WkbPostright role)
    {
        WkbAddNodeResult wkbAddNodeResult=new WkbAddNodeResult();
        try
        {
            role.setCreator(this.getCurrentUserId());
            WkbPostright roleNew=wkbPostrightService.addRole(role);
            Map<String, String> attributes=new HashMap<String, String>();
            attributes.put("type","3");
            attributes.put("nid", roleNew.getId().toString());
            attributes.put("right", role.getpRight() != null ? role.getpRight().toString() : TaskRight.LOWLEVEL.getRight().toString());
            wkbAddNodeResult.setAttributes(attributes);
            wkbAddNodeResult.setText(roleNew.getpName());

        }catch (WkbBizException wkbExp)
        {
            wkbAddNodeResult.setSucc("error");
            wkbAddNodeResult.setMsg(messageSource.getMessage(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            wkbAddNodeResult.setSucc("error");
            wkbAddNodeResult.setMsg("系统错误！");
        }
        return wkbAddNodeResult;
    }

    private String getCurrentUserId()
    {
        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
        if(wkbUser!=null)
            return String.valueOf(wkbUser.getuId());
        else
            return null;
    }

    @RequestMapping(value = "/role/del", method = RequestMethod.POST)
    @ResponseBody
    public WkbDelNodeResult delRole(@RequestParam Integer id,
                                   @RequestParam(required=false) Integer flag)
    {
        WkbDelNodeResult wkbDelNodeResult=new WkbDelNodeResult();
        boolean bLoop=false;
        if(flag!=null&&flag.intValue()==1)
            bLoop=true;
        try
        {
            boolean bSucc=wkbPostrightService.deleteRole(id,bLoop);
            if(bSucc==false)
            {
                wkbDelNodeResult.setSucc("warn");
            }

        }catch (WkbBizException wkbExp)
        {
            wkbDelNodeResult.setSucc("error");
            wkbDelNodeResult.setMsg(messageSource.getMessage(wkbExp.getCode()));
        }
        catch (Exception exp)
        {
            wkbDelNodeResult.setSucc("error");
            wkbDelNodeResult.setMsg("系统错误！");
        }
        return wkbDelNodeResult;
    }

    @RequestMapping(value = "/grid/data")
    @ResponseBody
    public Map<String, Object> gridData(HttpServletResponse response)
    {

        Map<String, Object> map=new HashMap<String, Object>();
        List<ProdInfo> list=new ArrayList<ProdInfo>();
        for(int i=0;i<40;i++)
        {
            list.add(new ProdInfo());
        }
        map.put("rows",list);
        map.put("total",40);

        return map;

    }
}
