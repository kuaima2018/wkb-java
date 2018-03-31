/**
 * 
 */
package com.heima.controller;

import com.heima.dto.WkbUserQueryDto;
import com.heima.model.WkbUser;
import com.heima.service.UserRoleService;
import com.heima.service.WkbSecurityService;
import com.heima.service.WkbUserService;
import com.heima.service.biz.WkbBizException;
import com.heima.util.DataGridModel;
import com.heima.util.MD5Utils;
import com.heima.util.RoleUsersDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends BaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

	@Autowired
	private WkbUserService wkbUserService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private WkbSecurityService wkbSecurityService;


    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("roleId") Integer roleId) {
        ModelAndView mav = new ModelAndView("wkbuser");
        mav.addObject("roleId", roleId);
        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
        if(wkbUser!=null)
        {
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
        }
        return mav;
    }

    @RequestMapping(value = "/role/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryRoleUsers(WkbUserQueryDto wkbUserQueryDto, DataGridModel dataGridModel)
    {
        Map<String,Object> map=new HashMap<String, Object>();

        try
        {
            formatQueryDto(wkbUserQueryDto);
            List<WkbUser> wkbUserList=userRoleService.queryPageUsersByRoleId(wkbUserQueryDto,dataGridModel.getStartRow(),dataGridModel.getEndRow());
            map.put("rows",wkbUserList);
            map.put("total",userRoleService.queryCountUsersByRoleId(wkbUserQueryDto));
        }catch (WkbBizException wkbExp)
        {
            logger.error("query role user error:"+wkbExp.getCode(), wkbExp);
            map.put("error",this.getMsgFromWkbExp(wkbExp));
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbUser>());
        }
        catch (Exception exp)
        {
            logger.error("query role user unknown error:", exp);
            map.put("error",exp.getMessage());
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbUser>());
        }

        return map;
    }



    @RequestMapping(value = "/companyApply/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryApplyUsers(WkbUserQueryDto wkbUserQueryDto, DataGridModel dataGridModel)
    {
        Map<String,Object> map=new HashMap<String, Object>();

        try
        {
            formatQueryDto(wkbUserQueryDto);
            if(wkbUserQueryDto.getRoleId()!=null)
            {
                String companyId=userRoleService.getCompanyFromRoleId(wkbUserQueryDto.getRoleId());
                if(StringUtils.isBlank(companyId))
                {
                    map.put("rows",new ArrayList<WkbUser>());
                    map.put("total",0);
                    return map;
                }
                else
                {
                    wkbUserQueryDto.setcId(companyId);
                }
            }
            List<WkbUser> wkbUserList=userRoleService.queryPageCompanyApplyUsers(wkbUserQueryDto,dataGridModel.getStartRow(),dataGridModel.getEndRow());
            map.put("rows",wkbUserList);
            map.put("total",userRoleService.queryCountCompanyApplyUsers(wkbUserQueryDto));
        }catch (WkbBizException wkbExp)
        {
            logger.error("query role user error:"+wkbExp.getCode(), wkbExp);
            map.put("error",this.getMsgFromWkbExp(wkbExp));
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbUser>());
        }
        catch (Exception exp)
        {
            logger.error("query role user unknown error:", exp);
            map.put("error",exp.getMessage());
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbUser>());
        }

        return map;
    }

    @RequestMapping(value = "/role/removeUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeRoleUsers( @RequestBody RoleUsersDto roleUsersDto)
    {
        Map<String, Object> map=new HashMap<String, Object>();
        try
        {
            if(!wkbSecurityService.isAdmin())
                throw new RuntimeException("当前用户无权限");
            userRoleService.remvoeRoleUsers(roleUsersDto.getRoleId(),roleUsersDto.getuIdList());
            //System.out.println(roleUsersDto.getRoleId());
            map.put("succ","succ");
        }catch (WkbBizException wkbExp)
        {
            logger.error("remove role user error:"+wkbExp.getCode(), wkbExp);
            map.put("succ","error");
            map.put("msg",this.getMsgFromWkbExp(wkbExp));
        }
        catch (Exception exp)
        {
            logger.error("remove role user unknown error:", exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }

        return map;
    }

    @RequestMapping(value = "/role/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addRoleUsers( @RequestBody RoleUsersDto roleUsersDto)
    {
        Map<String, Object> map=new HashMap<String, Object>();
        try
        {
            if(!wkbSecurityService.isAdmin())
                throw new RuntimeException("当前用户无权限");
            userRoleService.addRoleUsers(roleUsersDto.getRoleId(),roleUsersDto.getuIdList(),null);
            map.put("succ","succ");
        }catch (WkbBizException wkbExp)
        {
            logger.error("remove role user error:"+wkbExp.getCode(), wkbExp);
            map.put("succ","error");
            map.put("msg",this.getMsgFromWkbExp(wkbExp));
        }
        catch (Exception exp)
        {
            logger.error("remove role user unknown error:", exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }

        return map;
    }

    @RequestMapping(value = "/pwd/modify", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyUserPwd(String pwd)
    {
        Map<String, Object> map=new HashMap<String, Object>();
        WkbUser wkbUser= wkbSecurityService.getCurrentUser();
        try
        {
            //wkbUserService.updateUser();
            if(StringUtils.isBlank(pwd))
            {
                map.put("succ","error");
                map.put("msg","密码不能为空");
                return map;
            }
            pwd=MD5Utils.getMd5String(pwd);
            wkbUser.setuPwd(pwd);
            WkbUser wkbUserNew=new WkbUser();
            wkbUserNew.setuPwd(pwd);
            wkbUserNew.setuId(wkbUser.getuId());
            wkbUserService.updateUser(wkbUserNew);
            map.put("succ","succ");
        }catch (WkbBizException wkbExp)
        {
            logger.error("modify user error:"+wkbExp.getCode(), wkbExp);
            map.put("succ","error");
            map.put("msg",this.getMsgFromWkbExp(wkbExp));
        }
        catch (Exception exp)
        {
            logger.error("modify user unknown error:", exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }

        return map;
    }







	@RequestMapping(value = "/admin/user/add", method = RequestMethod.POST)
	@ResponseBody
	public WkbUser saveUser(@RequestBody WkbUser wkbUser) {
        //ObjectMapper
        if(!wkbSecurityService.isAdmin())
            throw new RuntimeException("当前用户无权限");
		 return wkbUserService.addAdmin(wkbUser);
	}

	@RequestMapping(value = "/bg/user/del", method = RequestMethod.DELETE)
	@ResponseBody
	public void delUser(@RequestBody WkbUser user) {
		// userService.batchSave(users);
	}

	@RequestMapping(value = "/bg/user/update", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateUser(@RequestBody WkbUser wkbUser) {
		return wkbUserService.updateOrgByPK(wkbUser);
	}

	@RequestMapping(value = "/bg/user/users", method = RequestMethod.GET)
	@ResponseBody
	public void users(@RequestBody WkbUser wkbUser) {
	}

	@RequestMapping(value = "/bg/aggree/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean jion(@PathVariable int id) {
		return wkbUserService.jion(id);

	}

	@RequestMapping(value = "/bg/reject/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean reject(@PathVariable int id) {
		return wkbUserService.reject(id);

	}

	
}
