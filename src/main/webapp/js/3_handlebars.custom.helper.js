(function($){
       
    Handlebars.registerHelper('listNum', function(start,end,currentPage, options) {
	  currentPage = parseInt(currentPage);
	  var fn = options.fn, inverse = options.inverse;
	  var ret = "", data;
	  if (options.data) {
	    data = Handlebars.createFrame(options.data);
	  }
	  var nums=new Array();
	  for(var i=start;i<=end;i++){
		 if(currentPage==i){
			nums.push({num:i,css:"currentPage"});
		 } else{
			nums.push({num:i});
		 }
	  }
	  if(nums && nums.length > 0) {
	    for(var i=0, j=nums.length; i<j; i++) {
	      if (data) { data.index = i; }
	      ret = ret + fn(nums[i], { data: data });
	    }
	  } else {
	    ret = inverse(this);
	  }
	  return ret;
    });

    Handlebars.registerHelper('fromTo', function(start,end,pageIdx, options) {
	  var fn = options.fn, inverse = options.inverse;
	  var ret = "", data;
	  if (options.data) {
	    data = Handlebars.createFrame(options.data);

	  }
	  var nums=new Array();
	  for(var i=start;i<=end;i++){
		 nums.push({num:i, pageIdx:pageIdx});
	  }
	  if(nums && nums.length > 0) {
	    for(var i=0, j=nums.length; i<j; i++) {
	      if (data) { data.index = i; }
	      ret = ret + fn(nums[i], { data: data });
	    }
	  } else {
	    ret = inverse(this);
	  }
	  return ret;
    });
	
    Handlebars.registerHelper('subString', function(src,start,num,ellipsis) {
    	ellipsis =ellipsis||false;
    	if(num=="end"){
    		return new Handlebars.SafeString(src.substring(start,src.length-start));
    	}else{
			if(num+start<src.length&&ellipsis){
				return new Handlebars.SafeString(src.substring(start,num)+"...");
			}else{
				return new Handlebars.SafeString(src.substring(start,num));
			}
    	}
    });

    Handlebars.registerHelper('hide', function(src,num) {
    	if(!src){
    		return new Handlebars.SafeString("");
    	}
    	if(src.length<=num){
    		return new Handlebars.SafeString("hide");
    	}else{
    		return new Handlebars.SafeString("");
    	}
    });

    Handlebars.registerHelper('colHeader', function(template,colWidth, options) {
        var columns = app.preference.columns();
        var displays = [];
        $.each(app.preference.displayColumns, function(idx, item){
            if(IsContain(columns, item.name)){
                displays.push(item);
            }
        });
        var html = Handlebars.templates[template]({displayColumns:displays, colWidth:100/displays.length, colsLen:displays.length});
        return html;
    });

    Handlebars.registerHelper('colBody', function(template, options) {
        var columns = app.preference.columns();
        var displays = [];
        $.each(app.preference.displayColumns, function(idx, item){
            if(IsContain(columns, item.name)){
                displays.push(item);
            }
        });
        var buffer = options.fn(this);
        var html = Handlebars.templates[template]({displayColumns:displays, block:buffer,
                colsLen:displays.length, colWidth:100/displays.length});
        return html;
    });

    function IsContain(arr,value)
    {
        for(var i=0;i<arr.length;i++)
        {
            if(arr[i]==value)
                return true;
        }
        return false;
    }
    
    /**
     * remove the continues comma,like:
     * ",,,,c1,,,,"==>"c1"
     * ",,,,,,,,,,"==>""
     * ",,,c1,,,,,c2"==>"c1,c2"
     */
    Handlebars.registerHelper('rmComma', function(src, options) {
    	if(src){
    		src = src+"";
    	}else{
    		 return new Handlebars.SafeString("");
    	}
    	src = src.replace(/\,{2,}/g,",");
    	if(src.length>0&&src.substring(0,1)==","){
    		src = src.substring(1);
    	}
    	if(src.length>0&&src.substring(src.length-1)==","){
    		src = src.substring(0,src.length-1);
    	}
        return new Handlebars.SafeString(src);
    });
})(jQuery);