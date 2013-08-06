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
            }
        });
        
        function refresh(pageIndex,pageSize){
        	app.sf.listContacts({pageSize:pageSize,pageIndex:pageIndex}).done(function(contacts){
        		brite.display("SalesForceContacts",".SalesForceScreen-content",{contacts:contacts});
            });
        }
    })(jQuery);


})();
