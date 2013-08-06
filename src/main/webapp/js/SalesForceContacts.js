;(function() {

	/**
	 * View: SalesForceContacts
	 *
	 */
    (function ($) {
        brite.registerView("SalesForceContacts",  {loadTmpl:true,emptyParent:true,parent:".SalesForceScreen-content"}, {
            create:function (data, config) {
                var $html = app.render("tmpl-SalesForceContacts",data);
                var $e = $($html);
                return $e;
            },
            postDisplay:function (data, config) {
            	var view = this;
            	var callback = function(pageIndex,pageSize){
            		refresh.call(view,pageIndex,pageSize);
            	}
            	data = data.contacts;
            	brite.display("Pagination",".paginationContainer",{pageIdx:data.pageIndex,pageSize:data.pageSize,totalCount:data.result_count,callback:callback});
            },
            events:{
            	"click;.addLabel":function(event){
            		var view = this;
            		var $addBtn = $(event.target);
            		var $tr = $addBtn.closest("tr");
            		var id = $tr.attr("data-id");
            		var name = $tr.attr("data-name");
            		var data = {type:"Add",id:id,name:name};
            		brite.display("LabelDialog","body",data);
            		view.currentTr = $tr;
            	},
            	"click;.labelText":function(event){
            		var view = this;
            		var $label = $(event.target);
            		var $tr = $label.closest("tr");
            		var id = $tr.attr("data-id");
            		var label =$label.html();
            		var name = $tr.attr("data-name");
            		var data = {type:"Edit",id:id,label:label,name:name};
            		view.currentTr = $tr;
            		brite.display("LabelDialog","body",data);
            	},
            	"click;.deleteLabel":function(event){
            		var $deleteBtn = $(event.target);
            		var $tr = $deleteBtn.closest("tr");
            		var id = $tr.attr("data-id");
            		app.sf.updateLabel({label:"",id:id}).done(function(contact){
            			$tr.find(".labelText").empty();
            			$tr.find(".addLabel").removeClass("hide");
            			$deleteBtn.addClass("hide");
    				});
            	}
            }
        });
        
        function refresh(pageIndex,pageSize){
        	app.sf.listContacts({pageSize:pageSize,pageIndex:pageIndex}).done(function(contacts){
        		brite.display("SalesForceContacts",".SalesForceScreen-content",{contacts:contacts});
            });
        }
    })(jQuery);


})();
