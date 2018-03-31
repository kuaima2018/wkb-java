package com.heima.web.service;

import com.heima.model.SessionRegister;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-9
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WkbSessionRegisterService {
    SessionRegister addSession(SessionRegister sessionRegister);
    void invalidSession(SessionRegister sessionRegister);
    void refreshSession(SessionRegister sessionRegister);
    SessionRegister getSession(String sessionId);
}
