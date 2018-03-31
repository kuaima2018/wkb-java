package com.heima.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.heima.model.WkbCompany;
import com.heima.model.WkbOrganization;
import com.heima.model.WkbUser;
import com.heima.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.heima.util.MD5Utils;

@Controller
public class LoginController {

    @Autowired
    private OrganizationExtService organizationExtService;

    @Autowired
    private WkbOrganizationService wkbOrganizationService;

    @Autowired
    private WkbSecurityService wkbSecurityService;

    @Autowired
    private WkbUserService wkbUserService;

    @Autowired
    private WkbCompanyService wkbCompanyService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        return "login";
    }

	@RequestMapping(value = "/bg/company/{companyId}", method = RequestMethod.GET)
	public String welcome(@PathVariable String companyId, HttpSession session,
			ModelMap model) {
        List<WkbOrganization> wkbOrganizationList= wkbOrganizationService.getOrganizationsByCompany(companyId);
		model.addAttribute("orgList", wkbOrganizationList);
        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
		this.refreshTreeList(model, wkbUser);
		
		if(wkbUser.getuAdmin()!=null&&wkbUser.getuAdmin().intValue()== 2) {
			return "/admin/welcome";
		}
		return "/bg/welcome";
	}

	@RequestMapping(value = "/bg/login", method = RequestMethod.POST)
	public String login(@Valid WkbUser user, BindingResult result,
			HttpSession session, HttpServletResponse res, ModelMap model)
			throws ServletException, IOException {
		if (result.hasErrors()) {
			System.out.println(user);
			System.out.println(result.hasFieldErrors("id"));
			if (!result.hasFieldErrors("id") && null == user.getuId()) {
				result.rejectValue("id", "error.id.empty");
			}
			return "/bg/loginError";
		}
		// admin 1
		WkbUser u = null;
				
			u=	wkbUserService.getUserByPrimary(user.getuId(), 2);
		if(null == u ) {
			u = wkbUserService.getUserByPrimary(user.getuId(), 1);
		}
		String authCode = (String) session.getAttribute("authCode");
		String pwd = user.getuPwd();
		
		// null != authCode && authCode.equals(user.getAuthCode())
		if (null != u && null != authCode /*&& authCode.equals(user.getAuthCode())*/
				&& user.getuId().equals(u.getuId())
				&& MD5Utils.getMd5String(pwd).equals(u.getuPwd())) {

			WkbCompany company = wkbCompanyService.findCompany(u.getcId());
			session.setAttribute("company", company);
			refreshTreeList(model, u);

			if(u.getuAdmin()!=null&&u.getuAdmin().intValue() == 1) {
				res.sendRedirect(session.getServletContext().getContextPath()
						+ "/bg/company/" + u.getcId());
			} else if(u.getuAdmin()!=null&&u.getuAdmin().intValue() == 2) {
				res.sendRedirect(session.getServletContext().getContextPath()
						+ "/admin/company/manager");
			}
			
			return null;
		} else {
			if (null== u || !user.getuId().equals(u.getuId())) {
				result.rejectValue("id", "error.id", "用户编码不存在！");
			}
			if (null != u && user.getuId().equals(u.getuId())
					&& !MD5Utils.getMd5String(pwd).equals(u.getuPwd())) {
				result.rejectValue("password", "error.password", "密码输入错误！");
			}
			if (null == authCode
					|| (null != authCode /*&& !authCode
							.equals(user.getAuthCode())*/)) {
				result.rejectValue("authCode", "error.authcode", "验证码错误");
			}
			return "/bg/loginError";
		}
	}

	private void refreshTreeList(ModelMap model, WkbUser wkbUser) {
		model.addAttribute("treeList", organizationExtService.treeOrg(wkbUser.getcId()));
	}

	@RequestMapping(value = "/bg/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, ModelMap model) {
		/*session.removeAttribute("user");
		User user = new User();
		model.addAttribute(user);*/
		return "login";
	}

}