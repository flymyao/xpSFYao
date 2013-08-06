var app = app || {};
(function($) {
	app.sf = {};
	
	app.sf.listContacts = function(params){
		params=params||{};
		params.method="GET";
		return app.getJsonData(contextPath+"/salesforce/listContacts",params);
	}
	app.sf.updateLabel = function(params){
		params=params||{};
		params.method="Post";
		return app.getJsonData(contextPath+"/salesforce/updateLabel",params);
	}
})(jQuery);
