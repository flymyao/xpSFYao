(function(){
	brite.registerView("LabelDialog",{emptyParent:false},{
		create:function(data,config){
			return app.render("LabelDialog",{data:data});
		},
		postDisplay:function(){},
		events:{
			"click;.cancel,.dialogCloseBtn":function(event){
				this.$el.remove();
			},
			"click;.add,.update":function(event){
				var contactView = $(".SalesForceContacts").bComponent();
				var $contactTr = contactView.currentTr;
				var view = this;
				var $label = $(":text[name='label']",view.$el);
				var label = $label.val();
				var id = $label.attr("data-id");
				id=id||-1;
				app.sf.updateLabel({label:label,id:id}).done(function(contact){
					view.$el.remove();
					if(contact.label){
						$contactTr.find(".labelText").html(contact.label);
						$contactTr.find(".deleteLabel").removeClass("hide");
						$contactTr.find(".addLabel").addClass("hide");
					}else{
						$contactTr.find(".labelText").empty();
						$contactTr.find(".deleteLabel").addClass("hide");
						$contactTr.find(".addLabel").removeClass("hide");
					}
				});
			}
		}
	});
})();