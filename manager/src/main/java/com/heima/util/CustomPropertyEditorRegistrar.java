package com.heima.util;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-1
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

    /*
      * (non-Javadoc)
      *
      * @see
      * org.springframework.beans.PropertyEditorRegistrar#registerCustomEditors
      * (org.springframework.beans.PropertyEditorRegistry)
      */
    @InitBinder
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Date.class, new DateTypeEditor());

    }

}
