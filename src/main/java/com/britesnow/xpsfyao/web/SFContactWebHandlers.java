package com.britesnow.xpsfyao.web;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONException;

import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.param.annotation.WebUser;
import com.britesnow.snow.web.rest.annotation.WebGet;
import com.britesnow.snow.web.rest.annotation.WebPost;
import com.britesnow.xpsfyao.dao.SocialIdEntityDao;
import com.britesnow.xpsfyao.entity.Contact;
import com.britesnow.xpsfyao.entity.User;
import com.britesnow.xpsfyao.oauth.ServiceType;
import com.britesnow.xpsfyao.service.SalesForceService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SFContactWebHandlers {
    
    @Inject
    private SocialIdEntityDao socialIdEntityDao;
    @Inject
    private SalesForceService salesforceService;
    
    @WebGet("/salesforce/listContacts")
    public Map listSFContacts(@WebModel Map m,
                               @WebParam("pageSize") Integer pageSize, @WebParam("pageIndex") Integer pageIndex,RequestContext rc) throws HttpException, IOException, JSONException {
        User user = rc.getUser(User.class);
        String token = socialIdEntityDao.getSocialdentity(user.getId(), ServiceType.SalesForce).getToken();
        if(pageIndex == null){
            pageIndex = 0;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        
        Map result = salesforceService.listContacts(token, pageIndex-1, pageSize);
        result.put("pageSize", pageSize);
        result.put("pageIndex", pageIndex);
        m.putAll(result);
        return m ;
    }
	
    @WebPost("/salesforce/updateLabel")
    public Contact updateLabel(@WebParam("label")String label,@WebParam("id")Long id) throws HttpException, IOException, JSONException {
    	return salesforceService.updateLabel(label, id);
    }
    
    @WebPost("/salesforce/sync")
    public Map sync(@WebUser User user) throws HttpException, IOException, JSONException {
        String token = socialIdEntityDao.getSocialdentity(user.getId(), ServiceType.SalesForce).getToken();
    	return salesforceService.syncContacts(token);
    }
}
