var parmCompanyQuery={cId:'-1'};

$(function(){
    parmCompanyQuery={cId:'-1'};
    $('#table_company').datagrid({
        url:'/company/company/query',
        striped: true,
        border: true,
        collapsible:true,
        height : 380,
        scrollbarSize:-1,
        columns:[[
            {field:'cId',title:'公司编号',width:100,editor:'text'},
            {field:'cName',title:'公司名称',width:100,editor:'text'},
            {field:'cContact',title:'联系人',width:100,editor:'text'},
            {field:'cMobile',title:'手机',width:100,editor:'text'},
            {field:'cTel',title:'电话',width:100,editor:'text'},
            {field:'cEmail',title:'邮箱',width:100,editor:'text'},
            {field:'cFax',title:'传真',width:100,editor:'text'},
            {field:'cAddr',title:'地址',width:100,editor:'text'},
            {field:'cZipcode',title:'邮编',width:100,editor:'text'},
            {field:'crtdatetime',title:'创建时间',width:140,editor:'text'}
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: {cId:'-1'},
        onBeforeLoad:function(data)
        {
            if(data==null||(data.cId!=null)&&(data.cId=='-1'))
            {
                return false;
            }
            else
            {
                return true;
            }
        },
        onLoadSuccess: function(data){
            if(data.error!=null)
            {
                $.messager.alert('系统提示', data.err,'error');
            }
        },
        onLoadError: function(){
            $.messager.alert('系统提示', "加载失败!",'error');
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        },
        onDblClickRow:function(rowIndex, rowData){
            //$('#h_cId').val(rowData.cId);
            /*$('#dlgUserConfig').window({title:'设置用户',
                height : 500,
                width:800,
                top:20,
                iconCls:'',
                shadow: true,
                modal:true,
                closed:true,
                minimizable:false,
                maximizable:false,
                collapsible:false,
                draggable:false
            })
                .window('open');*/
            wkbShowWindow({
                title:'设置用户',
                href:'/company/manager/'+rowData.cId,
                height:520,
                width: 800,
                //top:20,
                shadow:true,
                modal:true,
                closed:true,
                minimizable:false,
                maximizable:false,
                collapsible:false,
                draggable:false
            },"companyManagerDialog");
        }
    });
    var p = $('#table_company').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $('#btn_companySearch').click(function(){
        if(!$("#fmCompanyQuery").form("validate"))
        {
            return;
        }

        parmCompanyQuery={
            token:"1",
            cId:getNotEmptyString($("#txt_cId").val()),
            cName:getNotEmptyString($("#txt_name").val()),
            cContact:getNotEmptyString($("#txt_contact").val()),
            uMobile:getNotEmptyString($("#txt_mobile").val()),
            uTel:getNotEmptyString($("#txt_tel").val()),
            begincrttime:getNotEmptyString($("#tbBeginCreatedt").datebox('getValue')),
            endcrttime:getNotEmptyString($("#tbEndCreatedt").datebox('getValue'))
        };

        $('#table_company').datagrid('load',parmCompanyQuery);


    });

    $('#btn_companyReset').click(function(){
        $('#fmCompanyQuery').form('reset');
    });




    /*$('#btn_config_userSearch').click(function(){
        if(!$("#fmUserConfig").form("validate"))
        {
            return;
        }

        parmCompanyUserQuery={
            cId: getNotEmptyString($("#h_cId").val()),
            uId:getNotEmptyString($("#txt_config_userId").val()),
            uName:getNotEmptyString($("#txt_config_userName").val()),
            uMobile:getNotEmptyString($("#txt_config_userMobile").val()),
            uTel:getNotEmptyString($("#txt_config_userTel").val()),
            applyBeginTime:getNotEmptyString($("#add_tbBeginJointdt").datebox('getValue')),
            applyEndTime:getNotEmptyString($("#add_tbEndJointdt").datebox('getValue')),
            uAdmin:getNotEmptyString($('#txt_config_manager').combobox('getValue'))
        };

        $('#table_config_user').datagrid('load',parmCompanyUserQuery);


    });

    $('#btn_config_userConfirm').click(function(){
        setUserRoles(true,'是否确定设置选中的用户为管理员？');
    });

    $('#btn_config_userCancel').click(function(){
        setUserRoles(false,'是否撤销选中的管理员？');
    }); */

    $('#btn_companySwitch').click(function(){
        var checkedRows = $('#table_company').datagrid('getChecked');
        if(checkedRows.length > 0)
        {
            var companyId=checkedRows[0].cId;
            if(window.parent!=null)
            {
                window.parent.switchCompany(companyId);
            }
        }
        else
        {
            $.messager.alert('系统提示','未选中公司','error');
        }
    });


});

$.fn.datebox.defaults.formatter = function(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
};

$.extend($.fn.validatebox.defaults.rules,{
    querydatevalidator:{
        validator: function(value)
        {
            var date = $.fn.datebox.defaults.parser(value);
            var s = $.fn.datebox.defaults.formatter(date);
            return s==value;
        },message: '日期无效'
    }
});

function getNotEmptyString(str){
    if(str==null||str=="")
        return null;
    else
        return str;
}

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

/*function setUserRoles(bAdmin,msg)
{
    var checkedRows = $('#table_config_user').datagrid('getChecked');
    if(checkedRows.length > 0)
    {
        var _list = new Array();
        for(var i=0;i<checkedRows.length ;i++)
        {
            _list.push(checkedRows[i].uId);
        }
        var json={
            uIdList:_list,
            cId:$('#h_cId').val()
        }

        $.messager.confirm('警告',msg,function(r)
        {
            if(r==true)
            {
                var parms=JSON.stringify(json);
                updateUserRoles(bAdmin,parms);
            }
        });
    }else
    {
        $.messager.alert('系统提示', '未选中用户');
    }
}

function updateUserRoles(bAdmin,parms)
{
    var url=null;
    if(bAdmin==true)
    {
        url='/company/user/adminAdd';
    }
    else
    {
        url='/company/user/adminDel';
    }

    $.ajax({
        type : 'POST',
        url : url,
        data : parms,
        dataType : "json",
        contentType: "application/json; charset=utf-8",
        success : function (data) {
            if(data.succ=='succ')
            {
                $('#table_config_user').datagrid('reload',parmCompanyUserQuery);
            }
            else
            {
                if(data.msg!=null)
                {
                    $.messager.alert('系统提示',data.msg,'error');
                }
            }
        }
    });
} */