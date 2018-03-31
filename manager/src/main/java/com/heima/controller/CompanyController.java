/**
 *
 */
package com.heima.controller;

import com.heima.dto.WkbCompanyapplyQueryDto;
import com.heima.dto.WkbUserQueryDto;
import com.heima.model.WkbCompany;
import com.heima.model.WkbCompanyapply;
import com.heima.model.WkbUser;
import com.heima.service.*;
import com.heima.service.biz.WkbBizException;
import com.heima.util.DataGridModel;
import com.heima.util.RoleUsersDto;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
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
@RequestMapping(value = "company")
public class CompanyController extends BaseController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private WkbCompanyService wkbCompanyService;

    @Autowired
    private WkbCompanyapplyService wkbCompanyapplyService;

    @Autowired
    private WkbUserService wkbUserService;

    @Autowired
    private WkbSecurityService wkbSecurityService;

    @Autowired
    private CompanyIndustyService companyIndustyService;

    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/companyapply", method = RequestMethod.GET)
    public ModelAndView getApply() {
        ModelAndView mav = new ModelAndView("wkbcompanyapply");
        return mav;
    }

    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public ModelAndView get() {
        ModelAndView mav = new ModelAndView("wkbcompany");
        return mav;
    }

    @RequestMapping(value = "/companyOp/{companyId}", method = RequestMethod.GET)
    public ModelAndView getCompanyOp(@PathVariable String companyId) {
        //companyId为空，表示新增  非空表示修改公司
        ModelAndView mav = new ModelAndView("wkbCompanyOp");
        //查询公司、以及行业信息
        WkbUser wkbUser=wkbSecurityService.getCurrentUser();
        WkbCompany wkbCompany=null;
        if(wkbUser!=null)
        {
            mav.addObject("companyId", wkbUser.getcId());
        }
        else
        {
            mav.addObject("companyId","");
        }
        if(StringUtils.isNotBlank(companyId)&&!companyId.equals("0"))
        {
            //获取公司信息
            wkbCompany=wkbCompanyService.findCompany(companyId);
        }
        if(wkbCompany==null)
            wkbCompany=new WkbCompany();
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

        JsonConfig jsonConfig= new JsonConfig();
        jsonConfig.registerDefaultValueProcessor(Integer.class,
                new DefaultValueProcessor(){
                    public Object getDefaultValue(Class type){
                        return "";
                    }
                });
        mav.addObject("company",JSONSerializer.toJSON(wkbCompany,jsonConfig));
        return mav;
    }

    @RequestMapping(value = "/manager/{companyId}", method = RequestMethod.GET)
    public ModelAndView getManager(@PathVariable String companyId) {
        ModelAndView mav = new ModelAndView("wkbCompanyManager");
        mav.addObject("companyId",companyId);
        return mav;
    }

    @RequestMapping(value = "/manager/ext/{companyId}", method = RequestMethod.GET)
    public ModelAndView getManagerExt(@PathVariable String companyId) {
        ModelAndView mav = new ModelAndView("wkbCompanyManagerExt");
        mav.addObject("companyId",companyId);
        return mav;
    }


    @RequestMapping(value = "/get/{companyId}", method = RequestMethod.POST)
    @ResponseBody
    public WkbCompany getCompany(@PathVariable String companyId)
    {
        WkbCompany wkbCompany=null;
        if(StringUtils.isNotBlank(companyId))
        {
            wkbCompany=wkbCompanyService.findCompany(companyId);
        }
        if(wkbCompany==null)
            wkbCompany=new WkbCompany();
        return wkbCompany;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addCompany(@RequestBody WkbCompany wkbCompany)
    {
        //wkt-新增公司时，如果已经在另外的公司中，如果处理
        Map<String, Object> map=new HashMap<String, Object>();
        try
        {
            if(StringUtils.isBlank(wkbCompany.getcName()))
            {
                map.put("succ","error");
                map.put("msg","未提供公司全称");
                return map;
            }
            List<WkbCompany> wkbCompanyList=wkbCompanyService.findCompanyByName(wkbCompany.getcName());
            if(wkbCompanyList!=null&&wkbCompanyList.size()>0)
            {
                map.put("succ","error");
                map.put("msg","相同公司已存在");
                return map;
            }
            WkbUser wkbUser=wkbSecurityService.getCurrentUser();
            if(wkbUser!=null)
            {
                WkbCompany wkbCompanyNew=wkbCompanyService.addCompany(wkbCompany,wkbUser);
                map.put("company",wkbCompanyNew);
            }

            map.put("succ", "succ");
        }
        catch (Exception exp)
        {
            logger.error("update company unknown error:", exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateCompany(@RequestBody WkbCompany wkbCompany)
    {
        Map<String, Object> map=new HashMap<String, Object>();
        try
        {
            if(wkbCompany!=null)
            {
                if(StringUtils.isBlank(wkbCompany.getcName()))
                {
                    map.put("succ","error");
                    map.put("msg","未提供公司全称");
                    return map;
                }

                WkbCompany wkbCompanyOld=wkbCompanyService.findCompany(wkbCompany.getcId());
                if(wkbCompanyOld!=null) {
                    if(!wkbCompany.getcName().equals(wkbCompanyOld.getcName()))
                    {
                        List<WkbCompany> wkbCompanyList = wkbCompanyService.findCompanyByName(wkbCompany.getcName());
                        if (wkbCompanyList != null && wkbCompanyList.size() > 0) {
                            map.put("succ", "error");
                            map.put("msg", "相同公司已存在");
                            return map;
                        }
                    }
                }


                WkbUser wkbUser = wkbSecurityService.getCurrentUser();
                if (wkbUser != null)
                    wkbCompany.setUpdater(String.valueOf(wkbUser.getuId()));
                wkbCompanyService.updateCompany(wkbCompany);
            }
            map.put("succ", "succ");
        }
        catch (Exception exp)
        {
            logger.error("update company unknown error:", exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }
        return map;
    }

     @RequestMapping(value = "/companyApply/query", method = RequestMethod.POST)
     @ResponseBody
     public Map<String,Object> queryCompanyapply(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto,DataGridModel dataGridModel)
    {
        Map<String,Object> map=new HashMap<String, Object>();
        if(wkbCompanyapplyQueryDto.getId()!=null&&wkbCompanyapplyQueryDto.getId().intValue()==-1)
        {
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbCompanyapply>());
            return map;
        }
        try{
            this.formatQueryDto(wkbCompanyapplyQueryDto);
            wkbCompanyapplyQueryDto.setStartPos(dataGridModel.getStartRow());
            wkbCompanyapplyQueryDto.setEndPos(dataGridModel.getEndRow());

            List<WkbCompanyapply> wkbCompanyapplyList=wkbCompanyapplyService.queryCompanyapply(wkbCompanyapplyQueryDto);
            map.put("rows",wkbCompanyapplyList);
            map.put("total",wkbCompanyapplyService.queryCountCompanyapply(wkbCompanyapplyQueryDto));

        }catch (WkbBizException wkbExp)
        {
            logger.error("query company apply error:"+wkbExp.getCode(),wkbExp);
            map.put("error",this.getMsgFromWkbExp(wkbExp));
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbCompanyapply>());
        }
        catch (Exception exp)
        {
            logger.error("query company apply unknown error:",exp);
            map.put("error",exp.getMessage());
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbCompanyapply>());
        }

        return map;
    }

    @RequestMapping(value = "/company/add/{companyApplyId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addCompany(@PathVariable(value="companyApplyId") Integer companyApplyId)
    {
        Map<String, Object> map=new HashMap<String, Object>();
        try
        {
            //获取当前用户
            wkbCompanyService.addCompanyFromApply(companyApplyId,-1);
            map.put("succ","succ");
        }catch (WkbBizException wkbExp)
        {
            logger.error("add company error:"+wkbExp.getCode(), wkbExp);
            map.put("succ","error");
            map.put("msg",this.getMsgFromWkbExp(wkbExp));
        }
        catch (Exception exp)
        {
            logger.error("add company unknown error:", exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }
        return map;
    }

    @RequestMapping(value = "/company/delete/{companyApplyId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteCompany(@PathVariable(value="companyApplyId") Integer companyApplyId)
    {
        Map<String, Object> map=new HashMap<String, Object>();
        try
        {
            wkbCompanyapplyService.removeCompanyapply(companyApplyId);
            map.put("succ","succ");
        }catch (WkbBizException wkbExp)
        {
            logger.error("delete company error:"+wkbExp.getCode(), wkbExp);
            map.put("succ","error");
            map.put("msg",this.getMsgFromWkbExp(wkbExp));
        }
        catch (Exception exp)
        {
            logger.error("delete company unknown error:", exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }
        return map;
    }


    @RequestMapping(value = "/company/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryCompany(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto,DataGridModel dataGridModel)
    {
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getToken())){
                map.put("rows",new ArrayList<WkbCompany>());
                map.put("total",0);
                return map;
            }
            this.formatQueryDto(wkbCompanyapplyQueryDto);
            wkbCompanyapplyQueryDto.setStartPos(dataGridModel.getStartRow());
            wkbCompanyapplyQueryDto.setEndPos(dataGridModel.getEndRow());

            List<WkbCompany> wkbCompanyList=wkbCompanyService.queryPageCompany(wkbCompanyapplyQueryDto);
            map.put("rows",wkbCompanyList);
            map.put("total",wkbCompanyService.queryCountCompany(wkbCompanyapplyQueryDto));

        }catch (WkbBizException wkbExp)
        {
            logger.error("query company apply error:"+wkbExp.getCode(),wkbExp);
            map.put("error",this.getMsgFromWkbExp(wkbExp));
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbCompany>());
        }
        catch (Exception exp)
        {
            logger.error("query company apply unknown error:",exp);
            map.put("error",exp.getMessage());
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbCompany>());
        }

        return map;
    }

    @RequestMapping(value = "/user/query", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryCompanyUsers(WkbUserQueryDto wkbUserQueryDto,DataGridModel dataGridModel)
    {
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            this.formatQueryDto(wkbUserQueryDto);
            List<WkbUser> wkbUserList=wkbUserService.getPageCompanyUsers(wkbUserQueryDto,dataGridModel.getStartRow(),dataGridModel.getEndRow());
            map.put("rows",wkbUserList);
            map.put("total",wkbUserService.getCountCompanyUsers(wkbUserQueryDto));

        }catch (WkbBizException wkbExp)
        {
            logger.error("query company user error:"+wkbExp.getCode(),wkbExp);
            map.put("error",this.getMsgFromWkbExp(wkbExp));
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbUser>());
        }
        catch (Exception exp)
        {
            logger.error("query company user unknown error:",exp);
            map.put("error",exp.getMessage());
            map.put("total", 0);
            map.put("rows", new ArrayList<WkbUser>());
        }

        return map;
    }

    @RequestMapping(value = "/user/adminAdd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAdminUsers( @RequestBody RoleUsersDto roleUsersDto)
    {
        Map<String, Object> map=new HashMap<String, Object>();
        try
        {
            if(!wkbSecurityService.isAdmin())
                throw new RuntimeException("当前用户无权限");
            wkbUserService.updateUsersByAdminAdd(roleUsersDto.getuIdList(),roleUsersDto.getcId());
            map.put("succ","succ");
        }catch (WkbBizException wkbExp)
        {
            logger.error("add admin user error:"+wkbExp.getCode(), wkbExp);
            map.put("succ","error");
            map.put("msg",this.getMsgFromWkbExp(wkbExp));
        }
        catch (Exception exp)
        {
            logger.error("add admin user unknown error:", exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }

        return map;
    }


    @RequestMapping(value = "/user/adminDel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeAdminUsers( @RequestBody RoleUsersDto roleUsersDto)
    {
        Map<String, Object> map=new HashMap<String, Object>();
        try
        {
            if(!wkbSecurityService.isAdmin())
                throw new RuntimeException("当前用户无权限");
            wkbUserService.updateUsersByAdminDel(roleUsersDto.getuIdList(),roleUsersDto.getcId());
            map.put("succ","succ");
        }catch (WkbBizException wkbExp)
        {
            logger.error("remove admin user error:"+wkbExp.getCode(), wkbExp);
            map.put("succ","error");
            map.put("msg",this.getMsgFromWkbExp(wkbExp));
        }
        catch (Exception exp)
        {
            logger.error("remove admin user unknown error:", exp);
            map.put("succ","error");
            map.put("msg",exp.getMessage());
        }

        return map;
    }


    private void formatQueryDto(WkbCompanyapplyQueryDto wkbCompanyapplyQueryDto)
    {
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getcId()))
            wkbCompanyapplyQueryDto.setcId(null);
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getcName()))
            wkbCompanyapplyQueryDto.setcName(null);
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getcContact()))
            wkbCompanyapplyQueryDto.setcContact(null);
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getcMobile()))
            wkbCompanyapplyQueryDto.setcMobile(null);
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getcTel()))
            wkbCompanyapplyQueryDto.setcTel(null);
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getcFax()))
            wkbCompanyapplyQueryDto.setcFax(null);
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getcEmail()))
            wkbCompanyapplyQueryDto.setcEmail(null);
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getcAddr()))
            wkbCompanyapplyQueryDto.setcAddr(null);
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getcZipcode()))
            wkbCompanyapplyQueryDto.setcZipcode(null);
        if(StringUtils.isBlank(wkbCompanyapplyQueryDto.getStatus()))
            wkbCompanyapplyQueryDto.setStatus(null);

        try
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat simpleDateFormatFull=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(wkbCompanyapplyQueryDto.getBegincrttime()!=null)
            {
                String str=simpleDateFormat.format(wkbCompanyapplyQueryDto.getBegincrttime());
                wkbCompanyapplyQueryDto.setBegincrttime(simpleDateFormatFull.parse(str + " 00:00:00"));
            }
            if(wkbCompanyapplyQueryDto.getEndcrttime()!=null)
            {
                String str=simpleDateFormat.format(wkbCompanyapplyQueryDto.getEndcrttime());
                wkbCompanyapplyQueryDto.setEndcrttime(simpleDateFormatFull.parse(str + " 23:59:59"));
            }

        }catch (Exception exp)
        {
            logger.error("format date error:",exp);
        }
    }




























    @RequestMapping(value = "/admin/company/add", method = RequestMethod.POST)
    @ResponseBody
    public List<WkbCompany> save(@RequestBody List<WkbCompany> companys) {
        // userService.batchSave(users);
        //System.out.println(companys);
        return null;
    }

    @RequestMapping(value = "/admin/company/delete/{companyId}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean del(@PathVariable int companyId) {

        //companyService.deleteCompanyByPK(companyId);
        return true;
    }

    @RequestMapping(value = "/admin/company/update", method = RequestMethod.POST)
    @ResponseBody
    public boolean update(@RequestBody WkbCompany company) {
        // userService.batchSave(users);
        System.out.println(company);
        return false;//companyService.updateByPK(company);
    }

}
