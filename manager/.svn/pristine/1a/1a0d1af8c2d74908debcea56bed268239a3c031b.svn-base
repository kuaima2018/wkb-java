package com.heima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping(value = "error")
public class ErrorController {
    @RequestMapping(value = "/accessError", method = RequestMethod.GET)
    public ModelAndView accessError() {
        return new ModelAndView("error/404");
    }
}
