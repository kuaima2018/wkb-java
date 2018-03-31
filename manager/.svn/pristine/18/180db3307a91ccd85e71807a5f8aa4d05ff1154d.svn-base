var currentNode=null;
var indexNode=null;
$(function(){
    $('#wkb_org').tree({
        url:'/tree/nodes/top',
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
                url="/tree/nodes/fetch";
            }
                $.ajax({
                    type : opts.method,
                    url : url,
                    data : parms,
                    dataType : "json",
                    contentType: "application/json; charset=utf-8",
                    success : function (data) {
                        indexNode=data.index;
                        success(data.nodeList);
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
        onContextMenu: function(e,node){
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
            }
        }


    });

    $('#orgAdd_cancel').click(function(){
        $('#dlgOrgAdd').dialog('close');
    });
    $('#orgAdd_confirm').click(function(){
        var jsonData={
            'oId':$('#txt_oId').val(),
            'oName':$('#txt_oName').val(),
            'oFatherId':$('#h_parentOrgId').val()
        };
        var parms=JSON.stringify(jsonData);
        $.ajax({
            url:"/tree/org/add",
            type : 'POST',
            async : false,
            data : parms,
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            success:function(data){
                if(data!=null)
                {
                    //window.parent.alertWin('系统提示', data.msg);
                    if(data.st=='succ')
                    {
                        //1.父节点没有展开过，不处理
                        //2.添加到子组织节点后（a.没有子组织，那就是最前面 b.有子组织，那就是最后一个子组织后）
                        $('#dlgOrgAdd').dialog('close');
                        var node = wkbtree.tree('getSelected');
                        if(node!=null)
                        {

                        }
                    }
                }
                else
                {
                    $('#dlgOrgAdd').dialog('close');
                }
            }
        });
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


function addChildOrg()
{
    var wkbtree = $('#wkb_org');
    var node = wkbtree.tree('getSelected');
    if(node.state=="open")
    {
        var childs=wkbtree.tree('getLeafChildren',node.target);
        if(childs!=null&&childs.length>0)
        {
            var childOrgNode=null;
            var childRoleNode=null;
            for(var i=0; i<childs.length; i++)
            {
                var childNode=childs[i];
                if(childNode.attributes.type=='2')
                    childOrgNode=childNode;
                else if(childNode.attributes.type=='3')
                    childRoleNode=childNode;
            }
            if(childOrgNode!=null)
            {
                wkbtree.tree('insert', {
                    after: childOrgNode.target,
                    data: {
                        id: indexNode,
                        text: 'node text' +indexNode,
                        attributes:{
                            type:"2",
                            nid:indexNode+100
                        }
                    }
                });
                indexNode++;
            }

        }
        else
        {

        }
    }
    $('#txt_parentOId').val(node.text);
    $('#h_parentOrgId').val(node.attributes.nid);
    $('#dlgOrgAdd').window({title:'添加下级组织',
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