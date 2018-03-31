package com.heima.service;

import com.heima.tree.Node;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-3
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrganizationExtService {
    List<Node> treeOrg(String companyId);
}
