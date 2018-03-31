/**
 * 
 */
package com.heima.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.heima.model.WkbOrganization;
import com.heima.model.WkbUser;
import com.heima.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heima.tree.Node;

/**
 * @author Administrator
 * 
 */
@Controller
public class OrgController {

    @Autowired
    private WkbSecurityService wkbSecurityService;

	@Autowired
    private WkbOrganizationService wkbOrganizationService;

    @Autowired
    private OrganizationExtService organizationExtService;

	@RequestMapping(value = "/bg/org/add", method = RequestMethod.POST)
	@ResponseBody
	public List<WkbOrganization> save(@RequestBody List<WkbOrganization> orgs) {
        if(orgs!=null)
        {
            for(WkbOrganization wkbOrganization:orgs)
            {
                if(StringUtils.isBlank(wkbOrganization.getCreator()))
                {
                    wkbOrganization.setCreator(String.valueOf(wkbSecurityService.getCurrentUser().getuId()));
                }
            }
        }
		return wkbOrganizationService.saveOrgs(orgs);
	}

	@RequestMapping(value = "/bg/org/delete/{orgId}", method = RequestMethod.GET)
	@ResponseBody
	public Boolean del(@PathVariable int orgId) {

		WkbOrganization org = wkbOrganizationService.queryOrganization(orgId);
        List<WkbOrganization> wkbOrganizationList=wkbOrganizationService.getChilds(orgId);
		if(wkbOrganizationList!=null&&wkbOrganizationList.size()>0) {
			return false;
		}
		wkbOrganizationService.deleteOrganization(orgId);
		return true;
	}

	@RequestMapping(value = "/bg/org/update", method = RequestMethod.POST)
	@ResponseBody
	public boolean update(@RequestBody WkbOrganization org) {
		return wkbOrganizationService.upateOrganization(org);
	}

	@RequestMapping(value = "/bg/org/users", method = RequestMethod.GET)
	@ResponseBody
	public void users(@RequestBody WkbOrganization org) {
		// userService.batchSave(users);
	}
	
	@RequestMapping(value = "/bg/org/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<Node> trees(HttpSession session) {
        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
		List<Node> treeList = organizationExtService.treeOrg(wkbUser.getcId());
		return treeList;
	}
	
}
