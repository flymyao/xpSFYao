;(function() {

  /**
   * View: SalesForceScreen
   *
   */
    (function ($) {
        brite.registerView("SalesForceScreen",  {parent:".MainScreen-main",emptyParent:true}, {
            create:function (data, config) {
                var $html = app.render("tmpl-SalesForceScreen");
                var $e = $($html);
                return $e;
            },
            postDisplay:function (data, config) {
                var view = this;
                var $e = view.$el;
                app.sf.listContacts().done(function(contacts){
                	console.log(contacts);
                	brite.display("SalesForceContacts",".SalesForceScreen-content",{contacts:contacts});
                });
                
            },
            events:{
              "btap;.nav li":function(e){
                var view = this;
                var $e = view.$el;
                var $li = $(e.currentTarget);
                $e.find("li").removeClass("active");
                $li.addClass("active");
                
                var menu = $li.attr("data-nav");
                if(menu == "contacts"){
                  brite.display("SalesForceContacts");
                }
              }
            },

            docEvents:{
            },

            daoEvents:{
            }
            
        });
    })(jQuery);


})();
