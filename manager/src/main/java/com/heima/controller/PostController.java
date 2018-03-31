/**
 * 
 */
package com.heima.controller;

import java.util.List;


import com.heima.model.WkbPostright;
import com.heima.service.WkbPostrightService;
import com.heima.service.WkbSecurityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Administrator
 * 
 */
@Controller
public class PostController {

    @Autowired
    private WkbPostrightService wkbPostrightService;

    @Autowired
    private WkbSecurityService wkbSecurityService;

	@RequestMapping(value = "/bg/post/add", method = RequestMethod.POST)
	@ResponseBody
	public List<WkbPostright> save(@RequestBody List<WkbPostright> posts) {
        if(posts!=null)
        {
            for(WkbPostright wkbPostright:posts)
            {
                if(StringUtils.isBlank(wkbPostright.getCreator()))
                    wkbPostright.setCreator(String.valueOf(wkbSecurityService.getCurrentUser().getuId()));
            }
        }
		wkbPostrightService.savePostrights(posts);

        return posts;
	}

	@RequestMapping(value = "/bg/post/delete/{postId}", method = RequestMethod.GET)
	@ResponseBody
	public Boolean del(@PathVariable int postId) {
		
		int count=wkbPostrightService.removePostright(postId);
        if(count>0)
		    return true;
        else
            return false;
	}

	@RequestMapping(value = "/bg/post/update", method = RequestMethod.POST)
	@ResponseBody
	public boolean update(@RequestBody WkbPostright wkbPostright) {
		// userService.batchSave(users);
		int count= wkbPostrightService.updatePostright(wkbPostright);
        if(count>0)
            return true;
        else
            return false;
	}
	
}
