var currentNode=null;
var indexNode=null;
//var industryDataList=[{'id':1,'val':'行业1'},{'id':2,'val':'行业2'}];

$(function(){
    $('#wkb_org').tree({
        url:contextPath+'/tree/nodes/top/'+companyId,
        loadFilter: function(data){
            return data;
        },
        loader:function(param,success,error)
        {
            var opts = $(this).tree("options");
            if (!opts.url) {
                return false;
            }
            var parms=param;
            var url=opts.url;
            if(currentNode!=null)
            {
                var jsonData={
                    "id":currentNode.id,
                    "text":currentNode.text,
                    "attributes":currentNode.attributes,
                    "index":indexNode
                };
                parms=JSON.stringify(jsonData);
                url=contextPath+"/tree/nodes/fetch";
            }
                $.ajax({
                    type : opts.method,
                    url : url,
                    data : parms,
                    dataType : "json",
                    contentType: "application/json; charset=utf-8",
                    success : function (data) {
                        indexNode=data.index;
                        if(data.nodeList!=null)
                            success(data.nodeList);
                        else
                            success([]);
                    },
                    error : function () {
                        error.apply(this, arguments);
                    }
                });

        },
        onBeforeLoad:function(node,param){
            currentNode=node;
        },
        animate: true,
        lines:true,
        onContextMenu: function(e,node){
            //公司管理员才可以操作
            if(companyManager==true||systemManager==true)
            {
                e.preventDefault();
                if(node!=null&&node.attributes!=null&&node.attributes.type!=null)
                {
                    if(node.attributes.type==2)
                    {
                        $(this).tree('select',node.target);
                        $('#orgMenu').menu('show',{
                            left: e.pageX,
                            top: e.pageY
                        });
                    }
                    else if(node.attributes.type==1)
                    {
                        $(this).tree('select',node.target);
                        $('#companyMenu').menu('show',{
                            left: e.pageX,
                            top: e.pageY
                        });
                    }
                    else if(node.attributes.type==3)
                    {
                        $(this).tree('select',node.target);
                        $('#roleMenu').menu('show',{
                            left: e.pageX,
                            top: e.pageY
                        });
                    }
                }
            }
        },
        onDblClick:function(node)
        {
            if(node.attributes.type==3)
            {
                addTab("岗位："+node.text,contextPath+"/user/"+node.attributes.nid,node.attributes.nid);
            }
        },
        onClick:function(node)
        {
            if(node.attributes.type!=3)
            {
                //判断当前节点是否展开
                $('#wkb_org').tree('toggle',node.target);
            }
        },
        formatter:function(node)
        {
            if(node.attributes.type==3)
            {
                return '<i>'+node.text+'</i>';
            }
            else
                return node.text;
        }


    });

    $('#wkb_company').propertygrid({
        url:contextPath+'/company/get/'+companyId,
        method: 'POST',
        showGroup:false,
        showHeader:false,
        //fitColumns: true,
        columns:[[
            {field:'name',title:'Name',width:100,fixed:true},
            {field:'value',title:'Value',width:200,fixed:false}]],
        loader:function(param,success,error)
        {
            var opts = $(this).propertygrid("options");
            if (!opts.url) {
                return false;
            }
            var parms=param;
            var url=opts.url;
            $.ajax({
                type : opts.method,
                url : url,
                data : parms,
                dataType : "json",
                contentType: "application/json; charset=utf-8",
                success : function (data) {
                    var jsonProperty={};
                    jsonProperty.total=8;
                    //TODO:wkt-公司管理员或者非公司员工（也就是只可能不属于任何公司的人员），才可以编辑
                    jsonProperty.rows=new Array();
                    jsonProperty.rows[0]={"name":"公司代码","value":data.cId,"group":"1"};
                    jsonProperty.rows[1]={"name":"公司全称","value":data.cName,"group":"1"};//,"editor":"text"};
                    jsonProperty.rows[2]={"name":"公司电话","value":data.cTel,"group":"1"};//,"editor":{"type":"validatebox","options":{"validType":"telephoneNumber"}}};
                    jsonProperty.rows[3]={"name":"公司地址","value":data.cAddr,"group":"1"};//,"editor":"text"};
                    jsonProperty.rows[4]={"name":"公司邮编","value":data.cZipcode,"group":"1"};//,"editor":{"type":"numberbox","options":{"filter":wkb_numberFilter}}};
                    jsonProperty.rows[5]={"name":"电子邮箱","value":data.cEmail,"group":"1"};//,"editor":{"type":"validatebox","options":{"validType":"email","invalidMessage":"邮件格式不正确"}}};
                    jsonProperty.rows[6]={"name":"公司行业","value":data.cIndustry,"group":"1"}//,"editor":{"type":"combobox","options":{"valueField": "val","textField":"val",
                        //"data":industryDataList}}};
                    jsonProperty.rows[7]={"name":"公司简介","value":data.cRemark,"group":"1"}; //,"editor":"text"
                    if(jsonProperty!=null)
                        success(jsonProperty);
                    else
                        success([]);
                },
                error : function () {
                    error.apply(this, arguments);
                }
            });

        }
    });

    $('#wkb_company').propertygrid('doRowTip',{col:'value'});

    $('#company_reset').click(function(){
        $('#wkb_company').propertygrid("reload",null);
    });

    /*$('#company_refresh').click(function(){
        //保存
        var rows = $('#wkb_company').propertygrid('getChanges');
        if(rows==null||rows.length<=0)
            return;
        //var valid=$('wkb_company').propertygrid('isValid');
        rows=$('#wkb_company').propertygrid('getRows');
        var industry=rows[6].value;
        if(industry!=null&&industry!='')
        {
            //根据名称转换成整数
            industry=convertIndusty(industry);
        }
        var parms={
            'cId':rows[0].value,
            'cName':rows[1].value,
            'cTel':rows[2].value,
            'cAddr':rows[3].value,
            'cZipcode':rows[4].value,
            'cEmail':rows[5].value,
            'cIndustry':industry,
            'cRemark':rows[7].value
        };
        $.ajax({
            url:"/company/update",
            type : 'POST',
            async : false,
            data : JSON.stringify(parms),
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            success:function(data){
                if(data!=null)
                {
                    if(data.succ!='succ')
                    {
                        $.messager.alert('错误提示',data.msg);
                    }
                }
            }
        });
    });*/

    $('#company_add').click(function(){
        wkbShowWindow({
            title:'设置公司',
            href:contextPath+'/company/companyOp/0',
            height:420,
            width: 380,
            //top:20,
            shadow:true,
            modal:true,
            closed:true,
            minimizable:false,
            maximizable:false,
            collapsible:false,
            draggable:false
        },"companyOpDialog");
    });

    $('#company_refresh').click(function(){
        wkbShowWindow({
            title:'设置公司',
            href:contextPath+'/company/companyOp/'+companyId,
            height:440,
            width: 380,
            //top:20,
            shadow:true,
            modal:true,
            closed:true,
            minimizable:false,
            maximizable:false,
            collapsible:false,
            draggable:false
        },"companyOpDialog");
    });

    if(companyManager==true||systemManager==true)
    {
        $('#td_company_refresh').css({display:''});
    }
    else
    {
        $('#td_company_refresh').css({display:'none'});
    }
    /*$('#btn_company_switch').click(function(){
        companyId='2';
        *//*$('#wkb_org').tree('url','/tree/nodes/top/'+companyId);*//*
        var opts = $('#wkb_org').tree("options");
        opts.url='/tree/nodes/top/'+companyId;
        $('#wkb_org').tree('reload',null);
    });*/

    $('#orgAdd_cancel').click(function(){
        $('#dlgOrgAdd').dialog('close');
    });

    $('#roleOper_cancel').click(function(){
        $('#dlgRoleOper').dialog('close');
    });

    $('#orgAdd_confirm').click(function(){
        if(!$('#fmOrgAdd').form("validate"))
        {
           return;
        }
        var parentOrgId=$('#h_parentOrgId').val();
        var jsonData=null;
        if(parentOrgId==null||parentOrgId=='')
        {
            jsonData={
                /*'oId':$('#txt_xoId').val(),*/
                'oName':$('#txt_oName').val(),
                'cId':$('#h_cId').val()
            };
        }else
        {
            jsonData={
                /*'oId':$('#txt_xoId').val(),*/
                'oName':$('#txt_oName').val(),
                'oFatherId':$('#h_parentOrgId').val(),
                'cId':$('#h_cId').val()
            };
        }

        var parms=JSON.stringify(jsonData);
        $.ajax({
            url:contextPath+"/tree/org/add",
            type : 'POST',
            async : false,
            data : parms,
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            success:function(data){
                if(data!=null)
                {
                    //window.parent.alertWin('系统提示', data.msg);
                    if(data.succ=='succ')
                    {
                        var wkbtree=$('#wkb_org');
                        //1.父节点没有展开过，不处理
                        //2.添加到子组织节点后（a.没有子组织，那就是最前面 b.有子组织，那就是最后一个子组织后）
                        $('#dlgOrgAdd').dialog('close');
                        var node = wkbtree.tree('getSelected');
                        if(node!=null)
                        {
                            if (node.state == "open") {
                                wkbtree.tree('append', {
                                    parent: node.target,
                                    data: {
                                        id: indexNode,
                                        text: data.text,
                                        attributes: {
                                            type: data.attributes.type,
                                            nid: data.attributes.nid,
                                            right: data.attributes.right
                                        },
                                        state: 'open',
                                        iconCls: 'icon-organization'
                                    }
                                });
                                indexNode++;
                            }
                        }
                    }
                    else
                    {
                        $.messager.alert('错误提示',data.msg);
                    }
                }
                else
                {
                    $('#dlgOrgAdd').dialog('close');
                }
            }
        });
    });

    $('#orgDel_cancel').click(function(){
        $('#dlgOrgDel').dialog('close');
    });
    $('#orgDel_confirm').click(function(){
        var result=removeNode(contextPath+"/tree/org/del",false,$('#h_oId').val(),"存在下级部门或者岗位，是否全部删除？",
            "存在下级部门或者岗位，不能删除。");
        /*if(result==false)
        {
            removeNode("/tree/org/del",true,$('#h_oId').val(),"存在下级部门或者岗位，是否全部删除？",
                "存在下级部门或者岗位，不能删除。");
        }
        $('#dlgOrgDel').dialog('close');*/
    });


    //岗位操作

    $('#roleOper_confirm').click(function(){
        var roleId=$('#h_roleId').val();
        if(roleId==null||roleId=="")
        {
            if(!$('#fmRoleOper').form("validate"))
            {
                return;
            }
            //添加岗位
            var jsonData={
                    /*'pId':$('#txt_xroleId').val(),*/
                    'pName':$('#txt_roleName').val(),
                    'oId':$('#h_parentOrgId').val(),
                    'pRight':$('#txt_roleRight').combobox('getValue')
                };

            var parms=JSON.stringify(jsonData);
            $.ajax({
                url:contextPath+"/tree/role/add",
                type : 'POST',
                async : false,
                data : parms,
                dataType : "json",
                contentType: "application/json; charset=utf-8",
                success:function(data){
                    if(data!=null)
                    {
                        //window.parent.alertWin('系统提示', data.msg);
                        if(data.succ=='succ')
                        {
                            var wkbtree=$('#wkb_org');
                            $('#dlgRoleOper').dialog('close');
                            var node = wkbtree.tree('getSelected');
                            if(node!=null)
                            {
                                if (node.state == "open") {
                                    var childs = wkbtree.tree('getLeafChildren', node.target);
                                    if (childs != null && childs.length > 0) {
                                        var childOrgNode = null;
                                        var childRoleNode = null;
                                        for (var i = 0; i < childs.length; i++) {
                                            var childNode = childs[i];
                                            if (childNode.attributes.type == '2')
                                                childOrgNode = childNode;
                                            else if (childNode.attributes.type == '3')
                                                childRoleNode = childNode;
                                        }
                                        if (childRoleNode != null) {
                                            wkbtree.tree('insert', {
                                                after: childRoleNode.target,
                                                data: {
                                                    id: indexNode,
                                                    text: data.text,
                                                    attributes: {
                                                        type: data.attributes.type,
                                                        nid: data.attributes.nid
                                                    },
                                                    state: 'open',
                                                    iconCls: 'icon-role'
                                                }
                                            });
                                            indexNode++;
                                        }
                                        else if (childOrgNode != null) {
                                            wkbtree.tree('insert', {
                                                before: childOrgNode.target,
                                                data: {
                                                    id: indexNode,
                                                    text: data.text,
                                                    attributes: {
                                                        type: data.attributes.type,
                                                        nid: data.attributes.nid
                                                    },
                                                    state: 'open',
                                                    iconCls: 'icon-role'
                                                }
                                            });
                                            indexNode++;
                                        }
                                        else {

                                        }

                                    }
                                    else {
                                        wkbtree.tree('append', {
                                            parent: node.target,
                                            data: {
                                                id: indexNode,
                                                text: data.text,
                                                attributes: {
                                                    type: data.attributes.type,
                                                    nid: data.attributes.nid
                                                },
                                                state: 'open',
                                                iconCls: 'icon-role'
                                            }
                                        });
                                        indexNode++;
                                    }
                                }

                            }
                        }
                        else
                        {
                            $.messager.alert('错误提示',data.msg);
                        }
                    }
                    else
                    {
                        $('#dlgRoleOper').dialog('close');
                    }
                }
            });
        }
        else
        {
            //删除岗位
            var result=removeRoleNode(contextPath+"/tree/role/del",false,roleId,"存在岗位用户，是否全部删除？",
                "存在岗位用户，不能删除。");
        }
    });

    $('#wkbhome').layout('resize');

    if($('#btn_company_apply_query'))
    {
        $('#btn_company_apply_query').click(function(){
            queryCompanyapply();
        });

        $('#btn_company_query').click(function(){
            queryCompany();
        });
    }

    /*$('#btn_user_modify').click(function(){
        $('#txt_pass1').val('');
        $('#txt_pass2').val('')
        $('#dlgUserModify').window({title:'修改密码',
            width:400,
            height:300,
            top:200,
            iconCls:'',
            shadow: true,
            modal:true,
            closed:true,
            minimizable:false,
            maximizable:false,
            collapsible:false,
            draggable:false
        })
            .window('open');
    });

    $('#userModify_confirm').click(function(){
        if(!$("#fmUserModify").form("validate"))
        {
            return;
        }
        var parms={
            pwd:$('#txt_pass1').val()
        }

        $.ajax({
            url:"/user/pwd/modify",
            type : 'POST',
            async : false,
            data : parms,
            success:function(data){
                if(data!=null)
                {
                    if(data.succ=='succ')
                    {
                        $('#dlgUserModify').dialog('close');
                        $.messager.alert('提示','更新成功');
                    }
                    else
                    {
                        $.messager.alert('错误提示',data.msg,'error');
                    }
                }
            }
        });
    });

    $('#userModify_cancel').click(function(){
        $('#dlgUserModify').dialog('close');
    });*/


    $('#btn_manager_modify').click(function(){
        addTab("设置用户",contextPath+"/company/manager/ext/"+companyId);
    });

    $('#wkb_companyExt').resize(function(){
        $('#wkb_company').propertygrid("resize",null);
    });
});

$.extend($.fn.tree.methods,{
     getLeafChildren:function(jq, params){
         var nodes = [];
         $(params).next().children().children("div.tree-node").each(function(){
         nodes.push($(jq[0]).tree('getNode',this));
         });
         return nodes;
     }
});

function showIndusty(id)
{
    for(var i=0;i<industyValList.length;i++)
    {
        if(industyValList[i].id==id)
            return industyValList[i].val;
    }
    return "";
}

function convertIndusty(name)
{
    for(var i=0;i<industryDataList.length;i++)
    {
        if(industryDataList[i].val==name)
            return industryDataList[i].id;
    }
    return null;
}

function wkb_numberFilter(e)
{
    if(e.which==46|| e.which==45)
        return false;
}

function wkb_phoneFilter(e)
{
    if(e.which==48|| e.which==49||e.which==50|| e.which==51|| e.which==52
        ||e.which==53|| e.which==54||e.which==55|| e.which==56|| e.which==57
        ||e.which==45)
        return true;
    else
        return false;
}


function addChildOrg()
{
    $('#txt_oId').val('');
    $('#txt_oName').val('');
    $('#h_parentOrgId').val('');

    var wkbtree = $('#wkb_org');
    var node = wkbtree.tree('getSelected');

    var rootNode= wkbtree.tree('getRoot');
    $('#txt_parentOId').val(node.text);
    if(node.attributes.type=='2')
        $('#h_parentOrgId').val(node.attributes.nid);
    $('#h_cId').val(rootNode.attributes.nid);
    //首先展开节点
    $('#wkb_org').tree("expand",node.target);
    $('#dlgOrgAdd').window({title:'添加下级部门',
        width:400,
        height:300,
        top:200,
        iconCls:'',
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        draggable:false
    })
        .window('open');
}

function removeOrg()
{
    var wkbtree = $('#wkb_org');
    var node = wkbtree.tree('getSelected');

    var rootNode= wkbtree.tree('getRoot');
    var parentNode=wkbtree.tree('getParent',node.target);
    $('#txt_parentOIdDel').val(parentNode.text);
    $('#h_parentOrgId').val(parentNode.attributes.nid);
    $('#h_cId').val(rootNode.attributes.nid);

    /*var pos1=node.text.indexOf('(');
    var name=node.text.substring(0,pos1);
    var oid=node.text.substring(pos1+1).replace(')','');*/
    //$('#txt_oIdDel').val(oid);
    $('#txt_oNameDel').val(node.text);
    $('#h_oId').val(node.attributes.nid);
    $('#dlgOrgDel').window({title:'删除部门',
        width:400,
        height:300,
        top:200,
        iconCls:'',
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        draggable:false
    })
        .window('open');
}

function addRole()
{
    /*$('#txt_xroleId').val('');*/
    $('#txt_roleName').val('');

    var wkbtree = $('#wkb_org');
    var node = wkbtree.tree('getSelected');

    /*var pos1=node.text.indexOf('(');
    var name=node.text.substring(0,pos1);
    var oid=node.text.substring(pos1+1).replace(')','');*/

    $('#h_roleId').val("");

    //$('#txt_xroleOId').val(oid);
    $('#txt_roleOName').val(node.text);

    $('#h_parentOrgId').val(node.attributes.nid);

    $('#wkb_org').tree("expand",node.target);
    $('#dlgRoleOper').window({title:'增加岗位',
        width:600,
        height:400,
        top:200,
        iconCls:'',
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        draggable:false
    })
        .window('open');
}

function removeNode(url,bloop,id,loopmsg,looperrormsg)
{
    var delUrl=url+"?id="+id;
    if(bloop==true)
    {
        delUrl+="&flag=1";
    }
    $.ajax({
        url:delUrl,
        type : 'POST',
        async : false,
        data : "",
        success:function(data){
            if(data!=null)
            {
                if(data.succ=='succ')
                {
                    var wkbtree=$('#wkb_org');
                    var node = wkbtree.tree('getSelected');
                    if(node!=null)
                    {
                        wkbtree.tree('remove',node.target);
                    }
                    $('#dlgOrgDel').dialog('close');
                }
                else if(data.succ=='warn')
                {
                    if(bloop==false)
                    {
                        $.messager.confirm('警告',loopmsg,function(r)
                        {
                            if(r==true)
                            {
                                removeNode(url,true,id,loopmsg,looperrormsg);
                            }
                        });
                    }
                    else
                    {
                        $.messager.alert('错误提示',looperrormsg);
                    }
                }
                else
                {
                    $.messager.alert('错误提示',data.msg);
                }
            }
        }
    });

    return true;
}

function removeRoleNode(url,bloop,id,loopmsg,looperrormsg)
{
    var delUrl=url+"?id="+id;
    if(bloop==true)
    {
        delUrl+="&flag=1";
    }
    $.ajax({
        url:delUrl,
        type : 'POST',
        async : false,
        data : "",
        success:function(data){
            if(data!=null)
            {
                if(data.succ=='succ')
                {
                    var wkbtree=$('#wkb_org');
                    var node = wkbtree.tree('getSelected');
                    if(node!=null)
                    {
                        wkbtree.tree('remove',node.target);
                    }
                    $('#dlgRoleOper').dialog('close');
                }
                else if(data.succ=='warn')
                {
                    if(bloop==false)
                    {
                        $.messager.confirm('警告',loopmsg,function(r)
                        {
                            if(r==true)
                            {
                                removeNode(url,true,id,loopmsg,looperrormsg);
                            }
                        });
                    }
                    else
                    {
                        $.messager.alert('错误提示',looperrormsg);
                    }
                }
                else
                {
                    $.messager.alert('错误提示',data.msg);
                }
            }
        }
    });

    return true;
}

function addTab(title, url,id){

    var exists=0;
    if(id!=null&&id!=''){
        var tabs=$('#wkb_tabs').tabs('tabs');
        if(tabs!=null){
            for(var i=0;i<tabs.length;i++){
                var tabOps=tabs[i].panel('options');
                if(tabOps.id!=null&&tabOps.id!=''){
                    if(id==tabOps.id){
                        exists=1;
                        var index = $('#wkb_tabs').tabs('getTabIndex',tabs[i]);
                        if(index!=null&&index!='')
                        $('#wkb_tabs').tabs('select', index);
                        break;
                    }
                }
            }
        }
    }else{
        if ($('#wkb_tabs').tabs('exists', title)){
            exists=1;
            $('#wkb_tabs').tabs('select', title);
        }
    }
    if (exists==1){
        //$('#wkb_tabs').tabs('select', title);
    } else {
        <!--scrolling="auto"-->
        var content = '<iframe frameborder="0"  scrolling="no" src="'+url+'" style="width:100%;height:100%;"></iframe>';
        if(id!=null&&id!=''){
            $('#wkb_tabs').tabs('add', {
                title: title,
                content: content,
                closable: true,
                id:id
            });
        }else {
            $('#wkb_tabs').tabs('add', {
                title: title,
                content: content,
                closable: true
            });
        }
    }
}

function removeRole()
{
    var wkbtree = $('#wkb_org');
    var node = wkbtree.tree('getSelected');

    /*var pos1=node.text.indexOf('(');
    var name=node.text.substring(0,pos1);
    var oid=node.text.substring(pos1+1).replace(')','');*/

    $('#h_roleId').val(node.attributes.nid);

    /*$('#txt_xroleId').val(oid);*/
    $('#txt_roleName').val(node.text);
    $('#txt_roleRight').combobox('setValue',node.attributes.right);


    var parentNode=wkbtree.tree('getParent',node.target);
    $('#h_parentOrgId').val(parentNode.attributes.nid);
    /*pos1=parentNode.text.indexOf('(');
    name=parentNode.text.substring(0,pos1);
    oid=parentNode.text.substring(pos1+1).replace(')','');*/

    //$('#txt_xroleOId').val(oid);
    $('#txt_roleOName').val(parentNode.text);

    $('#dlgRoleOper').window({title:'删除岗位',
        width:600,
        height:400,
        top:200,
        iconCls:'',
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        draggable:false
    })
        .window('open');
}

function queryCompanyapply()
{
    addTab("公司申请",contextPath+"/company/companyapply");
}

function queryCompany()
{
    addTab("公司",contextPath+"/company/company");
}

function clearWkbTabs()
{
    var tabs=$('#wkb_tabs').tabs("tabs");
    for(var i=tabs.length-1;i>=1;i--)
    {
        $('#wkb_tabs').tabs('close',i);
    }
}

$.extend($.fn.validatebox.defaults.rules, {
    equals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '两次密码输入不一致'
    },
    telephoneNumber:{
        validator: function(value,param){
            if(value==null||value=='')
                return true;
            //
            var data2=value.replace(/-/gm,'');
            return wkb_IsNum(data2);
        },
        message: '电话号码不正确'
    }
});

function wkb_IsNum(num){
    var reNum =/^[0-9]+$/;
    return (reNum.test(num));
}

function switchCompany(scompanyId)
{
    companyId=scompanyId;
    var opts = $('#wkb_org').tree("options");
    opts.url=contextPath+'/tree/nodes/top/'+scompanyId;
    $('#wkb_org').tree('reload',null);
    //定位到公司页面
    //$('#wkbCompany').accordion('select','组织架构');
    //修改公司信息
    opts = $('#wkb_company').propertygrid("options");
    opts.url=contextPath+'/company/get/'+companyId;
    $('#wkb_company').propertygrid('reload',null);
}

function refreshCompany()
{
    $('#wkb_company').propertygrid('reload',null);
}

$.extend($.fn.datagrid.methods, {
    doRowTip: function (jq, params) {
        function showTip(data, td, e,colName,func) {
            var selFilter=">td[field='"+colName+"']";

            data.tooltip.text($(td).text()).css({
                top: (e.pageY + 10) + 'px',
                left: (e.pageX + 20) + 'px',
                'max-width': '600px',
                'z-index': $.fn.window.defaults.zIndex,
                display: 'block'
            });
        };
        return jq.each(function () {
            var grid = $(this);
            var options = $(this).data('datagrid');
            if (!options.tooltip) {
                var panel = grid.datagrid('getPanel').panel('panel');
                var defaultCls = {
                    //'border': 'none',
                    'padding': '2px',
                    'color': '#333',
                    'background': '#f7f5d1',
                    'position': 'absolute',
                    'border-radius': '4px',
                    '-moz-border-radius': '4px',
                    '-webkit-border-radius': '4px',
                    'display': 'none'
                }
                var tooltip = $('<div id="celltip" class="goods_info"></div>').appendTo('body');
                tooltip.css($.extend({}, defaultCls, params.cls));
                options.tooltip = tooltip;
                panel.find('.datagrid-body').each(function () {
                    var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
                    $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td', {
                        'mouseover': function (e) {
                            options.factContent = $(this).find('>div').clone().css({'margin-left':'-5000px', 'width':'auto', 'display':'inline', 'position':'absolute'}).appendTo('body');
                            var factContentWidth = options.factContent.width();
                            //不完全显示时，才提示
                            if (factContentWidth > $(this).width())
                            {
                                if (params.delay) {
                                    if (options.tipDelayTime)
                                        clearTimeout(options.tipDelayTime);
                                    var that = this;
                                    options.tipDelayTime = setTimeout(function () {
                                        showTip(options, that, e, params.col,params.func);
                                    }, params.delay);
                                }
                                else {
                                    showTip(options, this, e, params.col,params.func);
                                }
                            }
                        },
                        'mouseout': function (e) {
                            if (options.tipDelayTime)
                                clearTimeout(options.tipDelayTime);
                            options.tooltip.css({
                                'display': 'none'
                            });
                        }/*,
                        'mousemove': function (e) {
                            var that = this;
                            if (options.tipDelayTime)
                                clearTimeout(options.tipDelayTime);
                            options.tipDelayTime = setTimeout(function () {
                                showTip(options, that, e, params.col,params.func);
                            }, params.delay);
                        } */
                    });
                });
            }
        });
    },
    cancelRowTip: function (jq) {
        return jq.each(function () {
            var data = $(this).data('datagrid');
            if (data.tooltip) {
                data.tooltip.remove();
                data.tooltip = null;
                var panel = $(this).datagrid('getPanel').panel('panel');
                panel.find('.datagrid-body').undelegate('tr', 'mouseover').undelegate('tr', 'mouseout').undelegate('tr', 'mousemove')
            }
            if (data.tipDelayTime) {
                clearTimeout(data.tipDelayTime);
                data.tipDelayTime = null;
            }
        });
    }
});

function wkbShowWindow(options,winPop){
    if (options && options.width && options.height){
        options.top=($(window).height() - options.height) * 0.5;
        options.left=($(window).width() - options.width) * 0.5;
    }
    if(winPop){
        winPop="#"+winPop;
        var showWin=jQuery(winPop).window(options);
        showWin.window('open');
    }else{
        jQuery("#MyPopWindow").window(options);
        $('#MyPopWindow').window('open');
    }
}

$.extend($.messager.defaults,{
    ok:"确定",
    cancel:"取消"
});
