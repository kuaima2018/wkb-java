/**
 * 
 */
package com.heima.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.heima.model.WkbCompany;
import com.heima.model.WkbPostright;
import com.heima.model.WkbUser;
import com.heima.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heima.tree.Node;
import com.heima.util.MD5Utils;
import sun.security.util.Password;

/**
 * @author Administrator
 * 
 */
@Controller
public class MainController {

	@Autowired
	private WkbUserService wkbUserService;

    @Autowired
    private WkbSecurityService wkbSecurityService;

    @Autowired
    private OrganizationExtService organizationExtService;

    @Autowired
    private WkbCompanyService wkbCompanyService;

    @Autowired
    private WkbPostrightService wkbPostrightService;
	
	
	@RequestMapping(value = "/admin/company/manager", method = RequestMethod.GET)
	public String company(HttpSession session, ModelMap model) {
		WkbUser wkbUser=wkbSecurityService.getCurrentUser();
		refreshTreeList(model, wkbUser);
		List<WkbCompany> companyList = wkbCompanyService.getAllCompanys();
		model.addAttribute("companyList", companyList);
		WkbCompany company = wkbCompanyService.findCompany(wkbUser.getcId());
		session.setAttribute("company", company);
		return "/admin/company";
	}
	
	@RequestMapping(value = "/bg/company/{companyId}/pwd", method = RequestMethod.GET)
	public String pwd(@PathVariable String companyId, HttpSession session,
			ModelMap model) {
        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
		model.addAttribute("pwd", new Password());
		refreshTreeList(model, wkbUser);
		if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue() == 2) {
			return "/admin/pwd";
		}
		return "/bg/pwd";
	}

	@RequestMapping(value = "/bg/company/{companyId}/jion", method = RequestMethod.GET)
	public String jionList(@PathVariable String companyId, HttpSession session,
			ModelMap model) {
        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
		List<WkbUser> emList =wkbUserService.findNotJionEm(companyId);
		model.addAttribute("emList", emList);
		refreshTreeList(model, wkbUser);
		if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue() == 2) {
			return "/admin/jionList";
		}
		return "/bg/jionList";
	}

	@RequestMapping(value = "/bg/company/{companyId}/org", method = RequestMethod.GET)
	public String employerOrg(@PathVariable String companyId,
			HttpSession session, ModelMap model) {
		List<WkbUser> emList = wkbUserService.findJionEm(companyId);
		model.addAttribute("emList", emList);

        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
		refreshTreeList(model, wkbUser);
		List<Node> orgTree = organizationExtService.treeOrg(wkbUser.getcId());
		model.addAttribute("orgTree",orgTree);
		//System.out.println(orgTree);
		if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue() == 2) {
			return "/admin/employerOrg";
		}
		return "/bg/employerOrg";
	}
	
	@RequestMapping(value = "/bg/company/{companyId}/post", method = RequestMethod.GET)
	public String post(@PathVariable String companyId,
			HttpSession session, ModelMap model) {

        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
		refreshTreeList(model, wkbUser);
        List<WkbPostright> wkbPostrightList=wkbPostrightService.queryPostrightsByCompany(wkbUser.getcId());
		model.addAttribute("postList", wkbPostrightList);
		if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue() == 2) {
			return "/admin/post";
		}
		return "/bg/post";
	}
	
	private void refreshTreeList(ModelMap model, WkbUser wkbUser) {
		List<Node> treeList = organizationExtService.treeOrg(wkbUser.getcId());
		//System.out.println(treeList);
		model.addAttribute("treeList", treeList);
	}
	
	@RequestMapping(value = "/bg/user/pwd", method = RequestMethod.POST)
	public String updateUser(@Valid Password pwd, BindingResult result,
			HttpSession session, HttpServletResponse res, ModelMap model) {
        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
		this.refreshTreeList(model, wkbUser);
		if (result.hasErrors()) {
			if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue() == 2) {
				return "/admin/pwd";
			}
			return "/bg/pwd";
		}
		WkbUser wkbUserOrg = wkbUserService.getUserByPrimary(wkbUser.getuId(), 1);
		/*String md5OldPassword = MD5Utils.getMd5String(pwd.getOldPassword());
		String md5NewPassword = MD5Utils.getMd5String(pwd.getPassword());*/
        String md5OldPassword = MD5Utils.getMd5String(pwd.toString());
        String md5NewPassword = MD5Utils.getMd5String(pwd.toString());
		if (wkbUserOrg.getuPwd().equals(md5OldPassword)
				&& pwd.toString().equals(pwd.toString())) {
            wkbUserOrg.setuPwd(md5NewPassword);
			WkbUser wkbUser1 = wkbUserService.pwd(wkbUserOrg);
			if(null == wkbUser1) {
				result.rejectValue("oldPassword", "error.user.notfound",
						"user 不存在！");
				if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue() == 2) {
					return "/admin/pwd";
				}
				return "/bg/pwd";
			} else {
				wkbUserOrg = wkbUser1;
			}
			session.setAttribute("user",wkbUserOrg);
			model.addAttribute("message", "修改成功!");
			if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue() == 2) {
				return "/admin/success";
			}
			return "/bg/success";
		}
		
		if (null == wkbUser || null == pwd) {
			result.rejectValue("oldPassword", "error.user.notfound",
					"user 不存在！");
			if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue() == 2) {
				return "/admin/pwd";
			}
			return "/bg/pwd";
		}
		
		if (!wkbUser.getuPwd().equals(pwd.toString())) {
			result.rejectValue("oldPassword", "error.oldPassword.", "密码输入错误！");
		}
		
		if (!pwd.toString().equals(pwd.getClass())) {
			result.rejectValue("rePassword", "error.rePassword.", "两次密码输入不一致！");
		}
		if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue() == 2) {
			return "/admin/pwd";
		}
		return "/bg/pwd";
	}
}
