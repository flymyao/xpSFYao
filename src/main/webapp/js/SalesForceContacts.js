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
            postDisplay:function (data, config) {}
        });
        
    })(jQuery);


})();
