var app = app || {};
(function($) {
	app.sf = {};
	
	app.sf.listContacts = function(){
		var params = {method:"GET"};
		return app.getJsonData(contextPath+"/salesforce/listContacts",params);
	}
})(jQuery);
