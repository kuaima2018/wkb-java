var parmCompanyapply={id:-1};
$(function(){
    parmCompanyapply={id:-1};
    $('#table_companyapply').datagrid({
        url:'/company/companyApply/query',
        striped: true,
        border: true,
        collapsible:true,
        height : 380,
        scrollbarSize:-1,
        columns:[[
            {field:'id',title:'ID',width:0,editor:'text',hidden: true},
            {field:'ck', width:20, checkbox: true},
            {field:'cName',title:'公司名称',width:100,editor:'text'},
            {field:'cContact',title:'联系人',width:100,editor:'text'},
            {field:'cMobile',title:'手机',width:100,editor:'text'},
            {field:'cTel',title:'电话',width:100,editor:'text'},
            {field:'cEmail',title:'邮箱',width:100,editor:'text'},
            {field:'cFax',title:'传真',width:100,editor:'text'},
            {field:'cAddr',title:'地址',width:100,editor:'text'},
            {field:'cZipcode',title:'邮编',width:100,editor:'text'},
            {field:'crtdatetime',title:'申请时间',width:140,editor:'text'},
            {field:'status',title:'状态',width:140,editor:'text',formatter:function (value){
                if(value==0)
                    return '已申请';
                else if(value==1)
                    return '已加入';
                else
                    return '';
            }
            }
        ]],
        remoteSort:false,
        singleSelect:true,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: {id:-1},
        onBeforeLoad:function(data)
        {
            if(data==null||(data.id!=null)&&(data.id==-1))
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
        }
    });
    var p = $('#table_companyapply').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $('#btn_applySearch').click(function(){
        if(!$("#fmCompanyapplyQuery").form("validate"))
        {
            return;
        }

        parmCompanyapply={
            cName:getNotEmptyString($("#txt_name").val()),
            cContact:getNotEmptyString($("#txt_contact").val()),
            uMobile:getNotEmptyString($("#txt_mobile").val()),
            uTel:getNotEmptyString($("#txt_tel").val()),
            status:getNotEmptyString($("#txt_status").combobox('getValue')),
            begincrttime:getNotEmptyString($("#tbBeginApplydt").datebox('getValue')),
            endcrttime:getNotEmptyString($("#tbEndApplydt").datebox('getValue'))
        };

        $('#table_companyapply').datagrid('load',parmCompanyapply);


    });

    $('#btn_applyReset').click(function(){
        $('#fmCompanyapplyQuery').form('reset');
    });

    $('#btn_applyAdd').click(function(){
        var checkedRows = $('#table_companyapply').datagrid('getChecked');
        if(checkedRows.length == 1)
        {
            var msg="确定添加公司："+checkedRows[0].cName+"?";
            $.messager.confirm('提示',msg,function(r)
            {
                if(r==true)
                {
                    $.ajax({
                        type : 'POST',
                        url : '/company/company/add/'+checkedRows[0].id,
                        dataType : "json",
                        async:false,
                        success : function (data) {
                            if(data.succ=='succ')
                            {
                                $('#table_companyapply').datagrid('reload',parmCompanyapply);
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
                }
            });
        }
        else
        {
            $.messager.alert('系统提示', '未选中记录');
        }
    });

    $('#btn_applyDelete').click(function(){
        var checkedRows = $('#table_companyapply').datagrid('getChecked');
        if(checkedRows.length == 1)
        {
            var msg="确定删除公司："+checkedRows[0].cName+"?";
            $.messager.confirm('提示',msg,function(r)
            {
                if(r==true)
                {
                    $.ajax({
                        type : 'POST',
                        url : '/company/company/delete/'+checkedRows[0].id,
                        dataType : "json",
                        async:false,
                        success : function (data) {
                            if(data.succ=='succ')
                            {
                                $('#table_companyapply').datagrid('reload',parmCompanyapply);
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
                }
            });
        }
        else
        {
            $.messager.alert('系统提示', '未选中记录');
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